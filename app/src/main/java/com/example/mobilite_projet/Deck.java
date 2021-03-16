package com.example.mobilite_projet;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class Deck {

    ArrayList<Card> deck;

    public final static int maxCard = 5;
    public static Deck myDeck = new Deck();

    public Deck()
    {
        this.deck = new ArrayList<Card>(5);
        for (int i=0; i < this.deck.size();++i)
        {
            this.deck.set(i,new Card());
        }
    }
    public Deck(Player p){

        this.deck = new ArrayList<Card>(5);
        for (int i=0; i < this.deck.size();++i)
        {
            this.deck.set(i,new Card(p));
        }
    }

    public Deck(ArrayList<Card> d){this.deck =d;}

    public void AddCard( Card c) { this.deck.add(c);}

    public void RemoveCard(Card c) {

        this.deck.remove(c);
    }

    public Card getCard(int index)
    {
        return this.deck.get(index);
    }

    public int Size()
    {
        return this.deck.size();
    }

    public Card getCardByIndex(int index) { return this.deck.get(index);}

    public Card getRandomCard()
    {
        Random rand = new Random();
        int randomIndex = rand.nextInt(this.deck.size() );
        return this.deck.get(randomIndex);
    }

    public void setPlayerBelong(Player p)
    {
        for (int i=0; i < this.deck.size();++i)
        {
            this.deck.get(i).BelongTo(p);
        }
    }

}
