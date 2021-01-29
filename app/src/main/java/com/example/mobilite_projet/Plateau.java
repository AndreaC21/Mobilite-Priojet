package com.example.mobilite_projet;

public class Plateau {

    private Case[] plateau;

    public Plateau()
    {
        defaultPlateau();
    }

    // GETTER

    public void defaultPlateau() { this.plateau =new Case[9] ; }

}
