package com.herostorm.webservice;

public interface iBuildDeck {
    public Deck addCard(Card card);
    public Deck removeCard(int i);
    public Deck moveCard(int startPosition, int endPosition);
}
