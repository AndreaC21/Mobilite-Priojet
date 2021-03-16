package com.example.mobilite_projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private GameView gameView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menulayout);

        final Button button_deck = findViewById(R.id.button_deck);
        button_deck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent gameActivity = new Intent(MainActivity.this, DeckActivity.class);
                startActivity(gameActivity);
            }
        });

        final Button button_play = findViewById(R.id.button_play);
        button_play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                setContentView(R.layout.activity_main);
            }
        });

        if (Deck.myDeck.deck.size()==0)
        {
            Deck.myDeck = new Deck();
        }

       // gameView = findViewById( R.id.gameView );


    }
}