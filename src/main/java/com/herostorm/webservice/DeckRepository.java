package com.herostorm.webservice;

import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface DeckRepository extends DeckRepo {
    public Deck createNewDeck(String name);
    public Deck updateDeck();
}
