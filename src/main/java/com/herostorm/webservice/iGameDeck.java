package com.herostorm.webservice;

public interface iGameDeck {
    public Card draw();
    public iGameDeck shuffle();
}
