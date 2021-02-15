package com.example.mobilite_projet;

public class Plateau {

    private Case[] plateau;

    public Plateau()
    {
        defaultPlateau();
    }
    public final static int PlateauSize = 9;

    // GETTER

    public void defaultPlateau() { this.plateau =new Case[9] ; }

    public Case getCase(int index) { return this.plateau[index];}

}
