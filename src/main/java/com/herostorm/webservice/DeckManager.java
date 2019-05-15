package com.herostorm.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class DeckManager {

    @Autowired
    DeckRepo deckRepository;
    @Autowired
    UserManager userManager;

    public DeckManager(){
    }

    @RequestMapping(path="/Users/{ID}/Decks", method = RequestMethod.GET)
    public List<Deck> getDecks(@PathVariable("ID") String id){
        User user = userManager.getUser("id");
        if(user != null){
            return deckRepository.findAll("id");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID " + id + " Not Found");
        }
    }

    @RequestMapping(path="/Users/{ID}/Decks", params="name", method = RequestMethod.POST)
    public Deck createDeck(@Param("name") String name, @PathVariable("ID") String userID) {
        Deck newDeck = new Deck(name, userID);
        deckRepository.save(newDeck);
        return newDeck;
    }

    @RequestMapping(path="/Users/{ID}/Decks/{deck}", params="cardList,name", method = RequestMethod.POST)
    public Deck updateDeck(@Param("ID") String userID, @Param("deck") String deckID, @Param("cardList") String cardList){
        Deck myDeck = deckRepository.findById(userID,deckID);
        return myDeck;
    }

}
