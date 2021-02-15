package com.example.mobilite_projet;

import android.graphics.Color;

public class Player {

    private int borderColor;
    private String name;
    private Deck deck;

    public Player(){}

    public Player(String name,int c,Deck d)
    {
        this.borderColor =c;
        this.name = name;
        CreateDeck();
    }

    public Deck getDeck(){ return this.deck;}

    public void CreateDeck()
    {
        this.deck = new Deck();
        this.deck.AddCard(new Card(1,1,1,1));
        this.deck.AddCard(new Card(2,2,2,2));
        this.deck.AddCard(new Card(3,3,3,3));
        this.deck.AddCard(new Card(4,4,4,4));
        this.deck.AddCard(new Card(5,5,5,5));
    }
}
