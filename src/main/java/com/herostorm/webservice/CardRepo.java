package com.herostorm.webservice;

import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CardRepo {
    public Card findByCardNumber(int cardNumber);
    public List<Card> findByName(String name);
}
