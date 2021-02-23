package com.example.mobilite_projet;

public class Plateau {

    public final static int PlateauSize = 9;
    private Case[] plateau;

    public Plateau()
    {
        this.plateau =new Case[9] ;
        for ( int i=0; i < PlateauSize ; ++i)
        {
            this.plateau[i] = new Case();
        }
        this.plateau[4].SetContains(new Card());
    }



    // GETTER

    public Case getCase(int index) { return this.plateau[index];}

}
