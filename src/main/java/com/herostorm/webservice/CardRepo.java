package com.herostorm.webservice;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CardRepo extends MongoRepository<Card, String> {
    public Card findByCardNumber(int cardNumber);
    public List<Card> findByName(String name);
}
