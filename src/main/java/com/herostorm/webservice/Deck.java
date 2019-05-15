package com.herostorm.webservice;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

@Document(collection="decks")
public class Deck{
    @Id
    private String ID;

    private String userID;
    private String name;
    private List<String> cards;

    public Deck(String deckName, String user){
        this.name = deckName;
        this.userID = user;
    }

    public void saveDeck(){

    }

    public String getName() {
        return name;
    }

    public Deck setName(String name) {
        return new Deck(name, this.userID);
    }

    public List<String> getCards() {
        return cards;
    }

    public Deck setCards(List<String> cards) {
        Deck newDeck = new Deck(this.name, this.userID);
        newDeck.cards = cards;
        return newDeck;
    }
}
