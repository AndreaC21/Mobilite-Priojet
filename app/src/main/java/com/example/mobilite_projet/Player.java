package com.example.mobilite_projet;

import android.graphics.Color;

public class Player {

    private Color borderColor;
    private String name;
    private Deck deck;

    public Player(){}

    public Player(String name,Color c,Deck d)
    {
        this.borderColor =c;
        this.name = name;
        this.deck = d;
    }
}
