package com.herostorm.webservice.cards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Min;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/cards")
public class CardManager {

    @Autowired
    CardRepo repo;

    public CardManager(){
    }

    @GetMapping(value="/{id}")
    private Card stringID(@PathVariable("id") String id) throws ResponseStatusException{
        Optional<Card> optCard = repo.findById(id);
        if(optCard.isPresent()){
            return optCard.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card with ID " + id + " Not Found");
        }
    }

    @GetMapping(params="name")
    private List<Card> findCardsByName(@RequestParam String name){
        return repo.findByName(name);
    }

    @GetMapping
    private List<Card> getAllCards(){
        return repo.findAll();
    }

    public List<Card> getCardsByID(String cardList) {
        List<Card> myCardList = (List<Card>) repo.findAllById(Arrays.asList(cardList.split(",")));
        return myCardList;
    }
}
