package com.herostorm.webservice.decks;

import com.herostorm.webservice.users.User;
import com.herostorm.webservice.users.UserManager;
import com.herostorm.webservice.cards.Card;
import com.herostorm.webservice.cards.CardManager;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

@RestController
@RequestMapping(path="/Users/{id}/Decks")
public class DeckManager {

    DeckRepo deckRepository;
    UserManager userManager;
    CardManager cardManager;

    @Inject
    public DeckManager(DeckRepo deckRepo, UserManager userManager, CardManager cardManager){
        this.deckRepository = deckRepo;
        this.userManager = userManager;
        this.cardManager = cardManager;
    }

    @GetMapping
    public List<Deck> getDecks(@PathVariable("id") String id){
        User user = userManager.getUser(id);
        if(user != null){
            return user.getDecks();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID " + id + " Not Found");
        }
    }

    @PostMapping
    public Deck createDeck(@RequestBody Deck newDeck, @PathVariable("id") String userID) {
        if(newDeck.getName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deck Name Must Be Included");
        }
        deckRepository.save(newDeck);
        return newDeck;
    }

    @PostMapping(path="/{deck}")
    public Deck updateDeck(@PathVariable("id") String userID, @PathVariable("deck") String deckID, @RequestBody PostDeck newDeck){
        User user = userManager.getUser(userID);
        if (user != null){
            Optional<Deck> optDeck = deckRepository.findById(deckID);
            if(optDeck.isPresent()){
                Deck myDeck = optDeck.get();
                if(newDeck.getCardList() != null){
                    List<Card> updateCardList = cardManager.getCardsByID(newDeck.getCardList());
                    myDeck.setCards(updateCardList);
                }
                if(newDeck.getName() != null){
                    myDeck.setName(newDeck.getName());
                }
                myDeck = deckRepository.save(myDeck);
                return myDeck;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Deck with ID " + deckID + " Not Found");
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID " + userID + " Not Found");
    }

    @GetMapping(path="/{deck}")
    public Deck getDeck(@PathVariable("id") String userID, @PathVariable("deck") String deckID){
        User user = userManager.getUser(userID);
        if (user != null){
            Optional<Deck> optDeck = deckRepository.findById(deckID);
            if(optDeck.isPresent()){
                return optDeck.get();
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Deck with ID " + deckID + " Not Found");
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID " + userID + " Not Found");
    }
}
