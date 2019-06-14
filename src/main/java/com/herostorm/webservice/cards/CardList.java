package com.herostorm.webservice.cards;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

abstract public class CardList {
    private Collection<Card> cardList;

    public Collection<Card> getCardList() {
        return cardList;
    }

    public void setCardList(Collection<Card> cardList) {
        this.cardList = cardList;
    }

    public CardList filterByName(String name) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<? extends CardList> currentClass = this.getClass();
        CardList newList = currentClass.getConstructor().newInstance();
        Collection<Card> newCardList = new ArrayList<Card>();
        for(Card card : cardList){
            if(card.getName().contains(name)){
                newCardList.add(card);
            }
        }
        newList.setCardList(newCardList);
        return newList;
    }
}
