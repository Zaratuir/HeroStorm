package com.herostorm.webservice.decks;

import com.herostorm.webservice.cards.Card;
import com.herostorm.webservice.cards.CardList;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection="Decks")
public class Deck extends CardList {
    @Id
    private String id;

    private String name;
    @DBRef
    private List<Card> cardList;

    public Deck(){
    }

    public String getName() {
        return name;
    }

    public Deck setName(String name) {
        this.name = name;
        return this;
    }

    public List<Card> getCards() {
        return cardList;
    }

    public Deck setCards(List<Card> cards) {
        this.cardList = cards;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
