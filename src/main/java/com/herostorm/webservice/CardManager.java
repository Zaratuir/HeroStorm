package com.herostorm.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
public class CardManager {

    @Autowired
    CardRepo repo;

    public CardManager(){
    }

    @RequestMapping(value = "/cards/{id:^[0-9]+$}", method= RequestMethod.GET)
    private Card findCardByCardNumber(@PathVariable @Min(0) int id) throws ResponseStatusException {
        Card foundCard = repo.findByCardNumber(id);
        if (foundCard != null) {
            return foundCard;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card with ID " + id + " Not Found");
    }

    @RequestMapping(value="/cards/{id:!^[0-9]+$}", method=RequestMethod.GET)
    private Card stringID(@PathVariable("id") String id) throws ResponseStatusException{
        Card foundCard = repo.findById(id).get();
        return foundCard;
    }

    @RequestMapping(value = "/cards", params="name", method= RequestMethod.GET)
    private List<Card> findCardsByName(@RequestParam String name){
        return repo.findByName(name);
    }

    @RequestMapping(value = "/cards", method= RequestMethod.GET)
    private List<Card> getAllCards(){
        return repo.findAll();
    }
}
