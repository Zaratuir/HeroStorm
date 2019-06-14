package com.herostorm.webservice.cards;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Blob;

@Document(collection="cards")
public class Card {

    @Id
    private String id;

    private int cardNumber;
    private int redCost = 0;
    private int blueCost = 0;
    private int greenCost = 0;
    private int whiteCost = 0;
    private int blackCost = 0;
    private int yellowCost = 0;
    private int colorlessCost = 0;
    private String imagePath;
    private String name;

    public Card(String name, int cardNumber){
        this.name = name;
        this.cardNumber = cardNumber;
        this.redCost = 0;
        this.blueCost = 0;
        this.greenCost = 0;
        this.whiteCost = 0;
        this.blackCost = 0;
        this.yellowCost = 0;
        this.colorlessCost = 0;
        this.imagePath = "/images/DefaultImage.png";
    }

    public int getRedCost() {
        return redCost;
    }

    public void setRedCost(int redCost) {
        this.redCost = redCost;
    }

    public int getBlueCost() {
        return blueCost;
    }

    public void setBlueCost(int blueCost) {
        this.blueCost = blueCost;
    }

    public int getGreenCost() {
        return greenCost;
    }

    public void setGreenCost(int greenCost) {
        this.greenCost = greenCost;
    }

    public int getWhiteCost() {
        return whiteCost;
    }

    public void setWhiteCost(int whiteCost) {
        this.whiteCost = whiteCost;
    }

    public int getBlackCost() {
        return blackCost;
    }

    public void setBlackCost(int blackCost) {
        this.blackCost = blackCost;
    }

    public int getYellowCost() {
        return yellowCost;
    }

    public void setYellowCost(int yellowCost) {
        this.yellowCost = yellowCost;
    }

    public int getColorlessCost() {
        return colorlessCost;
    }

    public void setColorlessCost(int colorlessCost) {
        this.colorlessCost = colorlessCost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
