package com.example.mobilite_projet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Array;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class DeckActivity extends AppCompatActivity {

    private int id_selectedCard,id_cardToReplace;
    private NodeList nList_deck;

    private Document doc;
    private LinearLayout defaultLl;
    private String filename = "deck_file";

    private Document getDocument() {
        Document d = null;
        try {
            FileInputStream f = openFileInput(filename);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            d = dBuilder.parse(f);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return  d;
    }
    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }
    private static int[] getValue(Element element) {
        int[] result = new int[4];
        result[0] = Integer.parseInt(getValue("up",element));
        result[1] = Integer.parseInt(getValue("down",element));
        result[2] = Integer.parseInt(getValue("left",element));
        result[3] = Integer.parseInt(getValue("right",element));

        return result;
    }
    private void SaveChange()
    {
        try {
            //String filepath = "android.resource:/raw/deck_raw";
            // Uri uri = Uri.parse("android.resource://com.example.mobilite_projet.package/raw/deck_raw");
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(openFileOutput(filename, Context.MODE_PRIVATE));
            transformer.transform(source, result);
            Log.d("tag","save");
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();

        } catch (TransformerException e) {

            e.printStackTrace();
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
    }
    private void UpdateCard()
    {
        int id = getResources().getIdentifier("card_"+id_selectedCard, "array",getPackageName());
        int[] card = getResources().getIntArray(id);

        Node node = nList_deck.item(id_cardToReplace);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element2 = (Element) node;

            element2.getElementsByTagName("up").item(0).setTextContent(String.valueOf(card[0]));
            element2.getElementsByTagName("down").item(0).setTextContent(String.valueOf(card[1]));
            element2.getElementsByTagName("left").item(0).setTextContent(String.valueOf(card[2]));
            element2.getElementsByTagName("right").item(0).setTextContent(String.valueOf(card[3]));

           // Log.d("tag","done");

        }

        SaveChange();
        DrawDeck();
    }

    private void DrawDeck()
    {
        defaultLl.removeAllViewsInLayout();
        try{

            doc = getDocument();

            Element element=doc.getDocumentElement();
            element.normalize();

            nList_deck = doc.getElementsByTagName("card");

            for (int i=0; i<nList_deck.getLength(); i++) {

                Node node = nList_deck.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element2 = (Element) node;

                    //element2.getElementsByTagName("up").item(0).setTextContent("99");

                    //Log.d("tag","up"+String.valueOf(getValue("up", element2)));
                    int[] card = getValue(element2);
                    CreateCard(card,i, (LinearLayout) findViewById(R.id.deck_array),175,true);
                }
            }

        } catch (Exception e) {e.printStackTrace();}
    }
    private void SelectCard(int id, boolean deck)
    {
        // Clique on card to replace in the deck
        if ( deck) { id_cardToReplace = id; }
        else { id_selectedCard = id; }

        if ( id_cardToReplace != -1 && id_selectedCard != -1)
        {
            //Log.d("tag", "replace card");
            UpdateCard();
            id_selectedCard = -1;
            id_cardToReplace = -1;
        }
    }
    private void CreateCard(int[] card,int id,LinearLayout ll, int size,boolean isCardOfDeck)
    {
       // LinearLayout ll = (LinearLayout) findViewById(R.id.card_array);
        FrameLayout fl = new FrameLayout(this);
        ImageButton ib = new ImageButton(this);
        TextView up = new TextView(this);
        TextView down = new TextView(this);
        TextView left = new TextView(this);
        TextView right = new TextView(this);

        ib.setImageResource(R.drawable.empty_card);
        ib.setId(id);
        up.setText(String.valueOf(card[0]));
        down.setText(String.valueOf(card[1]));
        left.setText(String.valueOf(card[2]));
        right.setText(String.valueOf(card[3]));

        up.setTextColor(Color.BLACK);
        down.setTextColor(Color.BLACK);
        left.setTextColor(Color.BLACK);
        right.setTextColor(Color.BLACK);
        ib.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.d("tag", "click card_"+String.valueOf(id) + " isDeck"+isCardOfDeck);
                SelectCard(id,isCardOfDeck);
            }
        });
        fl.addView(ib);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.TOP | Gravity.CENTER);
        fl.addView(up,params);
        params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM | Gravity.CENTER);
        fl.addView(down,params);
        params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.LEFT | Gravity.CENTER);
        fl.addView(left,params);
        params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.RIGHT | Gravity.CENTER);
        fl.addView(right,params);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        params = new FrameLayout.LayoutParams(size, size);
        params.setMargins(0, 0, 50, 0);
        ll.addView(fl, params);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deck_creation_layout);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean valueBool=prefs.getBoolean("convertRAWtoFILE",false);

        // If file in internal storage not exist, we coy the one in raw to internal
        // else we load the file in internal storage
        if ( valueBool==false)
        {
            InputStream is = getResources().openRawResource (R.raw.deck_raw);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                doc = dBuilder.parse(is);
            } catch (ParserConfigurationException |SAXException | IOException e) {
                e.printStackTrace();
            }
            SaveChange();
            SharedPreferences.Editor editor=prefs.edit();
            editor.putBoolean("convertRAWtoFILE", true);
            editor.commit();
        }
        else
            doc = getDocument();



       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        id_cardToReplace = -1;
        id_selectedCard = -1;
        Resources res = getResources();
        int[] card1 = res.getIntArray(R.array.card_0);
        int[] card2 = res.getIntArray(R.array.card_1);

        //Load Card to Select
        for(int i = 0; i<2; i++) {

            int id = getResources().getIdentifier("card_"+i, "array",getPackageName());
            int[] card = getResources().getIntArray(id);
            CreateCard(card,i, (LinearLayout) findViewById(R.id.card_array),200,false);
        }

        defaultLl = (LinearLayout) findViewById(R.id.deck_array);

        //Load Deck
        DrawDeck();

        // Exit Button
        Button exit = findViewById(R.id.button_exit);
        exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent gameActivity = new Intent(DeckActivity.this, MainActivity.class);
                startActivity(gameActivity);
            }
        });


    }
}