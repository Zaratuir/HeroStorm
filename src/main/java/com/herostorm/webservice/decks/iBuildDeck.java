package com.herostorm.webservice.decks;

import com.herostorm.webservice.cards.Card;

public interface iBuildDeck {
    public Deck addCard(Card card);
    public Deck removeCard(int i);
    public Deck moveCard(int startPosition, int endPosition);
}
