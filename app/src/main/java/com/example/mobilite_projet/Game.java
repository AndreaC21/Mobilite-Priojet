package com.example.mobilite_projet;

public class Game {

    private Plateau plateau;
    private Player p1,p2;
    private int nbTurn;

    public Game(){}

    public Game(Player p1, Player p2)
    {
        this.plateau = new Plateau();
        this.p1 = p1;
        this.p2 = p2;
        this.nbTurn = 0;
    }

    // FUNCTION

}
