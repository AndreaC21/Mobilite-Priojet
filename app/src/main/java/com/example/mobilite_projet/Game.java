package com.example.mobilite_projet;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

public class Game {

    private Plateau plateau;
    private Player p1,pTurn;
    private Ai_Player p2;
    private int nbTurn;
    public static final int BOARD_COUNT = 9;

    private  GameView gameView;

    public Game(Player p1, Ai_Player p2)
    {
        this.plateau = new Plateau();
        this.p1 = p1;
        this.p2 = p2;
        this.nbTurn = 0;
        this.pTurn = this.p1;
    }
    public Game(Context c,GameView gv)
    {
        this.p1 = new Player("Andrea", Color.BLUE);
        this.p2 = new Ai_Player("IA", Color.RED);
        this.plateau = new Plateau();
        this.nbTurn = 0;
        this.pTurn = this.p1;
      this.gameView = gv;

        this.p1.LoadDeck(c);
       // this.p1.CreateDeck();
        //this.p2.CreateDeck();
        this.p2.CreateRandomCard(c);
    }

    public Player getP1(){return this.p1;}
    public Player getP2(){return this.p2;}

    public Plateau getPlateau() { return this.plateau;}
    public String getNbTurn() { return String.valueOf(this.nbTurn);}
    public Player getWinner()
    {
        int cardControlByP1 = 0;
        int cardControlByP2 = 0;

        for (int i=0; i< Plateau.PlateauSize ; ++i)
        {
            if (this.plateau.getCase(i).getContains().getBelongPlayer()==this.p1) cardControlByP1++;
            else cardControlByP2++;
        }

        if ( cardControlByP1 > cardControlByP2) return this.p1;
        return this.p2;

    }

    public String TurnOf()
    {
        return String.valueOf(this.pTurn.name);
    }

    public void nextPlayer()
    {


        //Log.d("tag","pturn"+this.pTurn.name);
        if ( this.pTurn == this.p1) this.pTurn = this.p2;
        else this.pTurn = this.p1;

        this.nbTurn++;

        if ( this.pTurn==this.p2)
        {
            this.p2.SelectCard(this);
        }
    }

    public void PlayCard(int indexPlateau,Card c)
    {

        // Play card
        c.BelongTo(this.pTurn);

        this.plateau.SetCase(indexPlateau, c,this.pTurn);

        gameView.invalidate();

        // Attack and Attaque en sucession
        Mechanic(indexPlateau);

        // Remove card from dek
        if ( this.pTurn == this.p1) this.p1.getDeck().RemoveCard(c);

        if ( !this.isFinished())
        {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Write whatever to want to do after delay specified (1 sec)
                    // Log.d("Handler", "Running Handler");
                    nextPlayer();
                    gameView.invalidate();
                }
            }, 1500);
        }



    }

    private final void Mechanic(int currentIndexPlateau)
    {
        if (currentIndexPlateau <0 || currentIndexPlateau >=9) return ;
        //Log.d("tag","Turn: "+String.valueOf(this.nbTurn)+" Mechanic on Case: "+String.valueOf(currentIndexPlateau));
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run() {
                //Write whatever to want to do after delay specified (1 sec)
               // Log.d("Handler", "Running Handler");


        Card currentCard = plateau.getCase(currentIndexPlateau).getContains();

        Card upCard = getContactCard(-3,currentIndexPlateau);
        Card downCard = getContactCard(3,currentIndexPlateau);
        Card leftCard = getContactCard(-1,currentIndexPlateau);
        Card rightCard = getContactCard(1,currentIndexPlateau);

        if ( upCard!=null && currentCard.isWiningAgainstUp(upCard) && upCard.getBelongPlayer()!=currentCard.getBelongPlayer())
        {
            upCard.BelongTo(pTurn);
            Mechanic(currentIndexPlateau-3);
        }
        if ( downCard!=null && currentCard.isWiningAgainstDown(downCard) && downCard.getBelongPlayer()!=currentCard.getBelongPlayer())
        {
            downCard.BelongTo(pTurn);
            Mechanic(currentIndexPlateau+3);
        }
        if ( leftCard!=null && currentCard.isWiningAgainstLeft(leftCard) && leftCard.getBelongPlayer()!=currentCard.getBelongPlayer())
        {
            leftCard.BelongTo(pTurn);
            Mechanic(currentIndexPlateau-1);
        }
        if ( rightCard!=null && currentCard.isWiningAgainstRight(rightCard) && rightCard.getBelongPlayer()!=currentCard.getBelongPlayer())
        {
            rightCard.BelongTo(pTurn);
            Mechanic(currentIndexPlateau+1);
        }
                gameView.invalidate();

            }
        }, 200);
    }

    /*a = -3 pour la carte du haut,
    +3 pour la carte du bas,
    -1 pour la carte de gauche,
    +1 pour la carte de droite
    */
    private Card getContactCard(int a, int currentIndexPlateau)
    {
        //Haut
        if ( a==3 && currentIndexPlateau+a >=9 ) return null;

        //bas
        if ( a==-3 && currentIndexPlateau+a < 0) return null;

        //On veut la carte a gauche mais si on est sur la bordure a gauche, retourne null
        if ( a==-1 && currentIndexPlateau%3==0) return null;

        if ( a==1 &&currentIndexPlateau%3==2) return null;

        return this.plateau.getCase(currentIndexPlateau+a).getContains();
    }

    public boolean isFinished()
    {
        return this.plateau.isPlateauComplete();
    }


    // FUNCTION

}
