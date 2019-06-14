package com.herostorm.webservice.decks;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeckRepo extends MongoRepository<Deck,String> {

}
