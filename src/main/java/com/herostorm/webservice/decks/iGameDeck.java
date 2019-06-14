package com.herostorm.webservice.decks;

import com.herostorm.webservice.cards.Card;

public interface iGameDeck {
    public Card draw();
    public iGameDeck shuffle();
}
