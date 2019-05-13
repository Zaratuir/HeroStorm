package com.herostorm.webservice;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

@Document(collection="cards")
public class Deck{
    @Id
    private String ID;

    private String name;
    private List<String> cards;

    public Deck(){
    }

    public void saveDeck(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
