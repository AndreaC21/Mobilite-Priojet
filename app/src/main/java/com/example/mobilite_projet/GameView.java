package com.example.mobilite_projet;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View implements GestureDetector.OnGestureListener {

    private GestureDetector gestureDetector;
    private int headerBackgroundColor;
    private int headerForegroundColor;
    private int backgroundColor;
    private int redColor;

    private Bitmap imgCard;

    private float deckWidth,deckHeight,deckMargin;
    private float cardSize;
    private float gridSeparatorSize,cellWidth,gridWidth;

    private Paint paint = new Paint( Paint.ANTI_ALIAS_FLAG );

    public Game Game = new Game();

    public GameView(Context context) {
        super(context);
        postConstruct();
    }

    /**
     * Class constructor
     */
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        postConstruct();
    }

    /**
     * Chargement des codes couleurs.
     */
    private void postConstruct() {
        gestureDetector = new GestureDetector(getContext(), this);

        Resources res = getResources();
        headerBackgroundColor = res.getColor( R.color.colorPrimaryDark );
        headerForegroundColor = res.getColor( R.color.headerForegroundColor );
        backgroundColor = res.getColor( R.color.backgroundColor );
        redColor = res.getColor( R.color.redColor );
    }

    /**
     * Retaillage des bitmaps en fonction de la taille de la fenêtre.
     */
    @Override
    protected void onSizeChanged( int width, int height, int oldw, int oldh ) {
        super.onSizeChanged( width, height, oldw, oldh );

        deckMargin = width * 0.025f;
        cardSize =  width * 0.35f;
        deckWidth = (width - (Game.BOARD_COUNT + 1) * deckMargin) / Game.BOARD_COUNT;
        deckHeight = deckWidth;
        gridSeparatorSize = (width / 9f) / 20f;

        gridWidth = width;                                  // Size of the grid (it's a square)
        cellWidth = gridWidth / 9f;

        try {
            int imageSize = (int) (deckWidth * 0.66);

            imgCard = BitmapFactory.decodeResource(getResources(), R.drawable.empty_card);
            imgCard = Bitmap.createScaledBitmap(imgCard, (int)cardSize, (int)cardSize, true);

        } catch (Exception exception) {
            Log.e("ERROR", "Cannot load card images");
        }

    }

    /**
     * Calcul de la "bounding box" du deck spécifié en paramètre.
     */
    private RectF computeDeckRect( int cardIndex ) {
        float x = deckMargin + cardSize *0.5f * cardIndex;
        float y = getHeight() * 0.77f ;
        return new RectF( x, y, x+deckWidth, y+deckHeight );
    }

    public void drawCard(Canvas canvas, Card card, float x, float y ) {
        float cornerWidth = deckWidth / 10f;

        RectF rectF = new RectF( x, y, x + cardSize, y + cardSize );

        Bitmap image = imgCard;
        canvas.drawBitmap(image,x,y,paint);
        int color = 0xff_00_00_00;
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize( cardSize / 6f );
        paint.setFakeBoldText( true );
        paint.setTextAlign( Paint.Align.LEFT );
        paint.setColor( color );

        // UP
        canvas.drawText( String.valueOf(card.getUp()), x + cardSize *0.92f /2, y + cardSize *0.15f, paint);
        //DOWN
        canvas.drawText( String.valueOf(card.getDown()), x + cardSize *0.92f /2, y + cardSize * 0.94f, paint);
        // LEFT
        canvas.drawText( String.valueOf(card.getLeft()), x + cardSize *0.07f, y + cardSize *0.55f, paint);
        //RIGHT
        canvas.drawText( String.valueOf(card.getRight()), x + cardSize *0.87f, y + cardSize * 0.55f, paint);

        paint.setFakeBoldText( false );

        // BORDER
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(0xff_40_40_40);
        canvas.drawRect(rectF, paint);

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // --- Background ---
        paint.setColor(backgroundColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

        // --- Draw the Header ---

        float widthDiv10 = getWidth() / 10f;
        float heightDiv10 = getHeight() / 10f;

        paint.setColor(headerBackgroundColor);
        RectF rectF = new RectF(0, 0, getWidth(), getHeight() * 0.08f);
        canvas.drawRect(rectF, paint);

       /*
        paint.setColor(redColor);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize((int) (getWidth() / 12));
        canvas.drawText(getResources().getString(R.string.app_name),widthDiv10 * 5, (int) (heightDiv10 * 0.4), paint);
        */

        paint.setColor(headerForegroundColor);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(getWidth() / 20f);
        paint.setStrokeWidth(1);
        canvas.drawText("Tour: "+Game.getNbTurn(), (int) (widthDiv10 * 0.5), (int) (heightDiv10 * 0.7), paint);

        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("AdversaireName", (int) (widthDiv10 * 9.5), (int) (heightDiv10 * 0.7), paint);

        // --- Draw the Plateau ---

        //// --- GRID ----
        paint.setColor( Color.BLACK );
        paint.setStrokeWidth( gridSeparatorSize );
        float yStart = getHeight()*0.19f;
        float yEnd = yStart*3f;
        for( int i=1; i<=4; i++ ) {
            //Verticale
            canvas.drawLine( i*(cellWidth*3),  yStart, i*(cellWidth*3), yEnd+yStart, paint );

            //Horizontale
            canvas.drawLine( i,i*yStart, getWidth(), i*yStart, paint );
        }
        
        //// ---- CONTENU ----
        Plateau p = Game.getPlateau();

        for ( int i = 0; i < Plateau.PlateauSize; ++i ) {
            Case c = p.getCase(i);
            if ( c !=null && c.isEmpty()==false)
            {
                float x =  getHeight()* 0.19f * (i %3);
                float y = getHeight() * 0.19f;
                if (i>2 && i<=5)  y *= 2;
                else if (i>5 ) y*=3;

                drawCard(canvas, c.getContains(),x, y);
            }

        }

        // --- Draw decks ---
        Deck deck = Game.getP1().getDeck();

        for ( int cardIndex = 0; cardIndex < Deck.maxCard; cardIndex++ ) {
                Card card = deck.getCardByIndex(cardIndex);
                rectF = computeDeckRect(cardIndex);
                drawCard(canvas, card, rectF.left, rectF.top);
        }

    }


    // --- OnGestureDetector interface ----

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);     // Le widget repasse la main au GestureDetector.
    }
    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    // On réagit à un appui simple sur le widget.
    @Override
    public boolean onSingleTapUp(MotionEvent e) {


        return true;
    }
}
