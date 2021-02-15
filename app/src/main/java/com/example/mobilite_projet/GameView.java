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
        deckWidth = (width - (Game.BOARD_COUNT + 1) * deckMargin) / Game.BOARD_COUNT;
        deckHeight = deckWidth * 1.4f;
        gridSeparatorSize = (width / 9f) / 20f;

        gridWidth = width;                                  // Size of the grid (it's a square)
        cellWidth = gridWidth / 9f;

        try {
            int imageSize = (int) (deckWidth * 0.66);


            imgCard = BitmapFactory.decodeResource(getResources(), R.drawable.empty_card);
            imgCard = Bitmap.createScaledBitmap(imgCard, imageSize, imageSize, true);

        } catch (Exception exception) {
            Log.e("ERROR", "Cannot load card images");
        }

    }
    /**
     * Calcul de la "bounding box" de la stack spécifiée en paramètre.
     */
    private RectF computePlateau( int index ) {
        float x = deckMargin + (deckWidth + deckMargin) * index;
        float y = getHeight() * 0.17f;
        return new RectF( x, y, x+deckWidth, y+deckHeight );
    }
    /**
     * Calcul de la "bounding box" du deck spécifié en paramètre.
     */
    private RectF computeDeckRect( int cardIndex ) {
        float x = deckMargin + (deckWidth + deckMargin) * cardIndex;
        float y = getHeight() * 0.30f + cardIndex * computeStepY();
        return new RectF( x, y, x+deckWidth, y+deckHeight );
    }
    /**
     * Calcul du décalage en y pour toutes les cartes d'un deck.
     */
    public float computeStepY() {
        return ( getHeight()*0.9f - getHeight()*0.3f ) / 17f;
    }

    public void drawCard(Canvas canvas, Card card, float x, float y ) {
        float cornerWidth = deckWidth / 10f;

        RectF rectF = new RectF( x, y, x + deckWidth, y + deckHeight );

        // Si card == null alors on ne trace que le contour de la carte
        if ( card == null ) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(0xff_40_40_40);
            canvas.drawRoundRect(rectF, cornerWidth, cornerWidth, paint);
            paint.setStyle(Paint.Style.FILL);
            return;
        }

        paint.setStyle(Paint.Style.FILL);
        paint.setColor( 0xff_a0_c0_a0 );
        canvas.drawRoundRect(rectF, cornerWidth, cornerWidth, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor( 0xff_00_00_00 );
        canvas.drawRoundRect(rectF, cornerWidth, cornerWidth, paint);

        Bitmap image = imgCard;
        int color = 0xff_00_00_00;
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize( deckWidth / 2.4f );
        paint.setFakeBoldText( true );
        paint.setTextAlign( Paint.Align.LEFT );
        paint.setColor( color );
        canvas.drawText( "1", x + deckWidth * 0.1f, y + deckHeight * 0.32f, paint);
        canvas.drawText( "0", x + deckWidth * 0.3f, y + deckHeight * 0.32f, paint);


        canvas.drawBitmap( image, x + (deckWidth - image.getWidth())/ 2f,
                y + (deckHeight*0.9f - image.getHeight()) , paint );
        paint.setFakeBoldText( false );

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

        paint.setColor(redColor);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize((int) (getWidth() / 12));
        canvas.drawText(getResources().getString(R.string.app_name),widthDiv10 * 5, (int) (heightDiv10 * 0.4), paint);

        paint.setColor(headerForegroundColor);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(getWidth() / 20f);
        paint.setStrokeWidth(1);
        canvas.drawText("Tour: ", (int) (widthDiv10 * 0.5), (int) (heightDiv10 * 0.7), paint);

        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("PlayerName", (int) (widthDiv10 * 9.5), (int) (heightDiv10 * 0.7), paint);

        // --- Draw the Plateau ---

        //// --- GRID ----
        paint.setColor( Color.BLACK );
        paint.setStrokeWidth( gridSeparatorSize );
        for( int i=1; i<=4; i++ ) {
            canvas.drawLine( i*(cellWidth*3),  360, i*(cellWidth*3), cellWidth*9+360, paint );
            canvas.drawLine( i,i*(cellWidth*3), cellWidth*9, i*(cellWidth*3), paint );
        }
        //// ---- CONTENU ----

        Plateau p = Game.getPlateau();

        for ( int i = 0; i < Plateau.PlateauSize;i++ ) {
            Case c = p.getCase(i);
            if ( c.available()==false)
            {
                rectF = computePlateau(i);
                drawCard(canvas, c.getContains(), rectF.left, rectF.top);
            }

            //drawCard(canvas, card, rectF.left, rectF.top);
        }
/*
        // --- Draw decks ---
        Deck deck = Game.getP1().getDeck();

        for ( int cardIndex = 0; cardIndex < Deck.maxCard; cardIndex++ ) {
                Card card = deck.getCardByIndex(cardIndex);
                rectF = computeDeckRect(cardIndex);
                //drawCard(canvas, card, rectF.left, rectF.top);
        }
*/

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

        RectF rect;

        // --- Un tap sur les cartes non retournées de la pioche ---
        rect = computePlateau(4);
        if ( rect.contains( e.getX(), e.getY() ) ) {
            //poserla carte sur le plateau
            postInvalidate();
            return true;
        }

        return true;
    }
}
