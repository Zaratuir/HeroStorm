package com.herostorm.webservice.cards;

import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CardRepository extends CardRepo {

    @Query("{'cardNumber' : ?0}")
    public Card findByCardNumber(int cardNumber);
    @Query("{'name': {$regex: '^.*?0.*$',  $options: 'i'}}")
    public List<Card> findByName(String name);
}
