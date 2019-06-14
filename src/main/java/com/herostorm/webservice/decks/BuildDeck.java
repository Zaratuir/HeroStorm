package com.herostorm.webservice.decks;

import com.herostorm.webservice.cards.Card;
import com.herostorm.webservice.decks.Deck;
import com.herostorm.webservice.decks.iBuildDeck;

import java.util.List;

public class BuildDeck implements iBuildDeck {

    private Deck deck;
    public BuildDeck(Deck deck){
        this.deck = deck;
    }

    @Override
    public Deck addCard(Card card) {
        return this.deck;
    }

    @Override
    public Deck removeCard(int i) {
        List<Card> cardList = this.deck.getCards();
        cardList.remove(i);
        return this.deck.setCards(cardList);
    }

    @Override
    public Deck moveCard(int startPosition, int endPosition) {
        return this.deck;
    }
}
