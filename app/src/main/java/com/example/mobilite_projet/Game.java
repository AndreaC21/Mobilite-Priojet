package com.example.mobilite_projet;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;

public class Game {

    private Plateau plateau;
    private Player p1,pTurn;
    private Ai_Player p2;
    private int nbTurn;
    private Handler handler;
    public static final int BOARD_COUNT = 9;


    public Game(Player p1, Ai_Player p2)
    {
        this.plateau = new Plateau();
        this.p1 = p1;
        this.p2 = p2;
        this.nbTurn = 0;
        this.pTurn = this.p1;
    }
    public Game(Context c)
    {
        this.p1 = new Player("Andrea", Color.BLUE);
        this.p2 = new Ai_Player("IA", Color.RED);
        this.plateau = new Plateau();
        this.nbTurn = 0;
        this.pTurn = this.p1;
        handler = new Handler();

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

    public void nextPlayer(View view)
    {
        if ( this.pTurn == this.p1) this.pTurn = this.p2;
        else this.pTurn = this.p1;

        this.nbTurn++;

        if ( this.pTurn==this.p2)
        {
            this.p2.PlayCard(this);
           nextPlayer(view);

        }
    }

    public void PlayCard(int indexPlateau,Card c)
    {
        c.BelongTo(this.pTurn);
        this.plateau.SetCase(indexPlateau, c,this.pTurn);
        this.Mechanic(indexPlateau);
    }

    private void Mechanic(int currentIndexPlateau)
    {
        if (currentIndexPlateau <0 || currentIndexPlateau >=9) return ;

        Card currentCard = this.plateau.getCase(currentIndexPlateau).getContains();

        Card upCard = getContactCard(-3,currentIndexPlateau);
        Card downCard = getContactCard(3,currentIndexPlateau);
        Card leftCard = getContactCard(-1,currentIndexPlateau);
        Card rightCard = getContactCard(1,currentIndexPlateau);

        if ( upCard!=null && currentCard.isWiningAgainstUp(upCard))
        {
            upCard.BelongTo(this.pTurn);
            Mechanic(currentIndexPlateau-3);
        }
        if ( downCard!=null && currentCard.isWiningAgainstDown(downCard))
        {
            downCard.BelongTo(this.pTurn);
            Mechanic(currentIndexPlateau+3);
        }
        if ( leftCard!=null && currentCard.isWiningAgainstLeft(leftCard))
        {
            leftCard.BelongTo(this.pTurn);
            Mechanic(currentIndexPlateau-1);
        }
        if ( rightCard!=null && currentCard.isWiningAgainstRight(rightCard))
        {
            rightCard.BelongTo(this.pTurn);
            Mechanic(currentIndexPlateau+1);
        }

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
