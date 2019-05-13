package com.herostorm.webservice;

public class BuildDeck implements iBuildDeck {

    Deck deck;
    public BuildDeck(Deck deck){

    }

    @Override
    public Deck addCard(Card card) {
        return this;
    }

    @Override
    public Deck removeCard(int i) {
        return this;
    }

    @Override
    public Deck moveCard(int startPosition, int endPosition) {
        return this;
    }
}
