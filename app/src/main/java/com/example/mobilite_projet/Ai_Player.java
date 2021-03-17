package com.example.mobilite_projet;

import android.content.Context;
import android.content.res.Resources;

import java.util.Random;

public class Ai_Player extends Player{

    // 0->agressif,1->moyen,2->defensif
    private int type_action;

    public Ai_Player(String name,int c)
    {
        super(name,c);
    }

    public void SelectCard(Game g)
    {
        // Choose position
        Plateau p = g.getPlateau();
        int indexLastPosition = p.lastPostionPlayed();
        Card lastCard = p.LastCardPlayed();

        int indexLowestValue = lastCard.getIndexLowerValue(-1);
        int indexAiWantToPlay = indexPositionAiWantToPlay(indexLowestValue,indexLastPosition);

        if ( isIndexCaseWantedValid(indexAiWantToPlay))
        {
            if ( p.getCase(indexAiWantToPlay).isEmpty())
            {
                Card bestCard = getBestCard(indexLowestValue);
                super.deck.RemoveCard(bestCard);

                g.PlayCard(indexAiWantToPlay,bestCard);
                return;
            }
        }
        Card r = deck.getRandomCard();
        super.deck.RemoveCard(r);

        g.PlayCard(getFirstEmptyPosition(p),r);

    }

    //REcursive version
    private int getFirstEmptyPosition(Plateau p,int index){
       if (p.getCase(index).isEmpty())
           return index;
       return getFirstEmptyPosition(p,index++);
    }

    //
    private int getFirstEmptyPosition(Plateau p){
        for ( int i =0; i < Plateau.PlateauSize ; ++i)
        {
            if (p.getCase(i).isEmpty()) return i;
        }
        return -1;

    }

    // Retourne l'index au pose a indexCard
    /*
    Si index card corresponds au bas (1) de la carte, alors pour comparer ensuite, je retourne le haut (0)
     */
    private int getIndex(int indexCard)
    {
        if ( indexCard==0) return 1;
        if ( indexCard==1) return 0;
        if ( indexCard==2) return 3;
        if  (indexCard==3) return 2;
        return -1;

    }

    // Si je veux jouer au dessus, ma meilleure carte est celle qui a la plus grande valeur en bas
    private Card getBestCard(int cardValue)
    {
        int max = 0;
        int maxIndex = -1;
        int myIndex = getIndex(cardValue);
        for (int i = 0 ; i < super.deck.Size() ; ++i)
        {
            if ( super.deck.getCard(i).getValue(myIndex) > max)
            {
                max = super.deck.getCard(i).getValue(myIndex);
                maxIndex = i;
            }
        }

        return super.deck.getCard(maxIndex);
    }

    private int indexPositionAiWantToPlay(int indexValueCardPlayer,int indexPositionPlayer)
    {
        switch (indexValueCardPlayer)
        {
            // AI veut jouer au dessus de la carte du joueur
            case 0: return indexPositionPlayer -3;
            //AI veut joueur en dessous de la carte du joueur
            case 1: return indexPositionPlayer +3;
            // AI veut jouer a gauche de la carte du joueur
            case 2 : return indexPositionPlayer -1;
            // AI veut jouer a gauche de la carte du joueur
            case 3: return indexPositionPlayer +1;
            default: return -1;
        }
    }
    private boolean isIndexCaseWantedValid(int indexAiWantToPlay)
    {
       // if ( indexPlayer < 0 || indexPlayer > 9) return false;
        if ( indexAiWantToPlay < 0 || indexAiWantToPlay >= 9) return false;

        return true;
    }

    public int getRandomInt(int max)
    {
        Random rand = new Random();
        return rand.nextInt(max);

    }
    public void CreateRandomCard(Context c)
    {
        Resources res = c.getResources();
        super.deck = new Deck(this);

        for(int i = 0; i<Deck.maxCard; ++i)
        {
            int id = res.getIdentifier("card_"+getRandomInt(Deck.maxCardinCollection), "array",c.getPackageName());
            int[] card = res.getIntArray(id);

            this.deck.AddCard(new Card(card,this));
        }

    }
}
