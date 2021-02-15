package com.example.mobilite_projet;

import android.graphics.Color;

public class Game {

    private Plateau plateau;
    private Player p1,p2;
    private int nbTurn;
    public static final int BOARD_COUNT = 9;

    public Game(Player p1, Player p2)
    {
        this.plateau = new Plateau();
        this.p1 = p1;
        this.p2 = p2;
        this.nbTurn = 0;
    }
    public Game()
    {
        this.p1 = new Player("Andrea", Color.BLUE,Deck.defaultDeck);
        this.p2 = new Player("Quentin", Color.RED,Deck.defaultDeck);
        this.plateau = new Plateau();
        this.nbTurn = 0;
    }

    public Player getP1(){return this.p1;}

    public Plateau getPlateau(){return  this.plateau;}

    // FUNCTION

}
