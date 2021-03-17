package com.example.mobilite_projet;

import android.content.Context;
import android.graphics.Color;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Player {

    protected int borderColor;
    protected String name;
    protected Deck deck;

    public Player(){}

    private String filename="deck_file";

    public Player(String name,int c)
    {
        this.borderColor = c;
        this.name = name;

    }

    public Deck getDeck(){ return this.deck;}
    public int getColor() { return this.borderColor;}

    public void CreateDeck()
    {
        this.deck = new Deck(this);
        this.deck.AddCard(new Card(1,1,1,1));
        this.deck.AddCard(new Card(2,2,2,2));
        this.deck.AddCard(new Card(3,3,3,3));
        this.deck.AddCard(new Card(4,4,4,4));
        this.deck.AddCard(new Card(5,5,5,5));
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
    public void LoadDeck(Context c)
    {
        Document doc = null;
        this.deck = new Deck();
        try {
            FileInputStream f =  c.openFileInput(filename);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(f);
            Element element = doc.getDocumentElement();
            element.normalize();

            NodeList nList_deck = doc.getElementsByTagName("card");

            for (int i = 0; i < nList_deck.getLength(); i++) {

                Node node = nList_deck.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element2 = (Element) node;
                    //element2.getElementsByTagName("up").item(0).setTextContent("99");

                    //Log.d("tag","up"+String.valueOf(getValue("up", element2)));
                    int[] card = getValue(element2);

                    this.deck.AddCard(new Card(card,this));
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
