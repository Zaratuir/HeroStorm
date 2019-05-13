package com.herostorm.webservice;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface DeckRepo extends MongoRepository<Card,String> {
    public Deck createNewDeck();
    public Deck updateDeck();
    @Override
    @Query("{User:?0}")
    public List<Deck>findAll(String userID);
}
