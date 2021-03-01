package com.example.mobilite_projet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import java.util.ArrayList;

public class Card {

    private int[] values; //up,down,left,right;
    private Bitmap image;
    private int borderColor;
    private Player belong;


    public Card(Player p){
        this.values = new int[4];
        this.values[0] = 0;
        this.values[1] = 0;
        this.values[2] = 0;
        this.values[3] = 0;
        this.belong =p;
        this.borderColor = 0xff_00_00_00;
    }

    public Card(){
        this.values = new int[4];
        this.values[0] = 0;
        this.values[1] = 0;
        this.values[2] = 0;
        this.values[3] = 0;
        this.borderColor = 0xff_00_00_00;
    }


    public Card(int up,int down,int left,int right)
    {
        this.values = new int[4];
        this.values[0] = up;
        this.values[1] = down;
        this.values[2] = left;
        this.values[3] = right;
        this.borderColor = 0xff_00_00_00;
    }

    // GETTER

    public int getUp() { return this.values[0]; }
    public int getDown() { return this.values[1]; }
    public int getLeft() { return this.values[2]; }
    public int getRight() { return this.values[3]; }

    public int getValue(int index)
    {
        if(index < 0 || index > 4) return -1;
        return this.values[index];

    }
    public int getBorderColor() { return this.borderColor; } //return 0xff_00_00_00;}
    public Player getBelongPlayer() { return this.belong;}

    public int getIndexLowerValue(int exception)
    {
        int min = 999;
        int index = -1;
        for ( int i= 0; i < this.values.length ;++i)
        {
            if (this.values[i] < min && i!=exception)
            {
                min = this.values[i];
                index = i;
            }
        }
        return index;
    }

    // SETTER

    public void setBorderColor(int c) { this.borderColor = c;}
    public void BelongTo(Player p)
    {
        this.belong = p;
        this.borderColor = this.belong.getColor();
    }

    //// FUNCTION CONTACT

    // Ma carte est au dessus de contactCard
    public boolean isWiningAgainstDown(Card contactCard)
    {
      return this.getDown() > contactCard.getUp();
    }
    // Ma carte est en dessous de contactCard
    public boolean isWiningAgainstUp(Card contactCard)
    {
        return this.getUp() > contactCard.getDown();
    }

    // Ma carte est a gauche de contactCard
    public boolean isWiningAgainstRight(Card contactCard)
    {
        return this.getRight() > contactCard.getLeft();
    }
    // Ma carte est a droite de contactCard
    public boolean isWiningAgainstLeft(Card contactCard)
    {
        return this.getLeft() > contactCard.getRight();
    }

}
