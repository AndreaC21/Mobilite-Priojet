package com.example.mobilite_projet;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;

public class Plateau {

    public final static int PlateauSize = 9;
    private Case[] plateau;
    private ArrayList<Pair<Integer,Card>> coups;
    public Plateau()
    {
        this.plateau =new Case[9] ;
        for ( int i=0; i < PlateauSize ; ++i)
        {
            this.plateau[i] = new Case();
        }
        coups = new ArrayList<>();

    }
    // GETTER

    public Case getCase(int index) { return this.plateau[index];}

    public void SetCase(int index,Card c,Player pturn)
    {
        if ( index < 0 || index > 9) return;

        if ( isPlateauComplete()==false)
        {
            c.setBorderColor(pturn.getColor());
            this.plateau[index].SetContains(c);
            Pair<Integer,Card> p = new Pair<Integer,Card>(index,c);
            coups.add(p);

            Log.d("tag","index plateau"+index);
            Log.d("tag","index card"+c.getUp());
        }
    }

    public int LastPostionPlayed()
    {
        return this.coups.get(this.coups.size()-1).first;
    }
    public Card LastCardPlayed()
    {
        return this.coups.get(this.coups.size()-1).second;
    }

    public boolean isPlateauComplete()
    {
        for ( int i=0; i < PlateauSize; ++i)
        {
            if ( this.plateau[i].isEmpty()) return false;
        }
        return true;
    }

}
