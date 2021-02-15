package com.example.mobilite_projet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

public class Card {

    private int[] values; //up,down,left,right;
    private Bitmap image;
    private Color borderColor;



    public Card(){

    }


    public Card(int up,int down,int left,int right)
    {
        this.values = new int[4];
        this.values[0] = up;
        this.values[1] = down;
        this.values[2] = left;
        this.values[3] = right;
    }

    // GETTER

    public int getUp() { return this.values[0]; }
    public int getDown() { return this.values[1]; }
    public int getLeft() { return this.values[2]; }
    public int getRight() { return this.values[3]; }
    public Color getBorderColor() { return borderColor; }

    // SETTER

    public void setBorderColor(Color c) { this.borderColor = c;}

    //// FUNCTION CONTACT

    // Ma carte est au dessus de contactCard
    public boolean isWiningContactUP(Card contactCard)
    {
      return this.getDown() > contactCard.getUp();
    }
    // Ma carte est en dessous de contactCard
    public boolean isWiningContactDOWN(Card contactCard)
    {
        return this.getUp() > contactCard.getDown();
    }

    // Ma carte est a gauche de contactCard
    public boolean isWiningContactLEFT(Card contactCard)
    {
        return this.getRight() > contactCard.getLeft();
    }
    // Ma carte est a droite de contactCard
    public boolean isWiningContactRIGHT(Card contactCard)
    {
        return this.getLeft() > contactCard.getRight();
    }

}
