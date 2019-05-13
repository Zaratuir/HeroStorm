package com.herostorm.webservice;

import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeckManager {

    DeckRepo deckRepository;

    public DeckManager(){
    }

    @RequestMapping(path="/Users/{ID}/Decks" method = "GET");
    private void getDecks(@PathVariable("ID") String id){
        deckRepository.findAll(id);
    }

    @RequestMapping(path="/Users/{ID}/Decks", params="name", method = "POST")
    public void createDeck(@Param("name") String name) {
        Deck newDeck = new Deck();
        newDeck.setName(name);
        deckRepository.insert(newDeck, Deck.class);
    }

}
