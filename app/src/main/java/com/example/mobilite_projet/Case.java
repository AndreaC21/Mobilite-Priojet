package com.example.mobilite_projet;

public class Case {

    private enum TypeCase { Void,Occuped}
    private TypeCase type; // empty or occuped
    private Card contains;
    private boolean[] murs; //up,down,left,right;

    public Case()
    {
        this.type = TypeCase.Void;
        this.contains = null;
        this.murs = new boolean[]{false,false,false,false};
    }

    public Case(int positionMur)
    {
        this.type = TypeCase.Void;
        this.contains = null;
        this.murs = new boolean[]{false,false,false,false};
        this.murs[positionMur] = true;

    }
    public Case(boolean[] m)
    {
        this.type = TypeCase.Void;
        this.contains = null;
        if ( m.length == 4) this.murs = m;

    }

    // GETTER
    public Card getContains() {
        /*if ( this.type == TypeCase.Occuped) */return contains;
       // return null;
    }

    // SETTER
    public void SetContains(Card c)
    {
        if (this.available()) this.contains = c;
    }

    // index = 0,1,2,3 == up,down,left,right
    public boolean hasWallByIndex( int index)
    {
        return this.murs[index];
    }

    // FUNCTION
    public boolean available() { return this.type == TypeCase.Void;}

    //
}

