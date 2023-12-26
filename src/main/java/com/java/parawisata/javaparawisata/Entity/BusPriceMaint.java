package com.java.parawisata.javaparawisata.Entity;

import java.sql.Date;

public class BusPriceMaint {
    private String busPriceID;
    private String busName;
    private Double price;
    private Integer duration;
    private String destination;
    private String userID;
    private String action;

    public BusPriceMaint() {
    }

    public BusPriceMaint(String busPriceID, String busName, Double price, Integer duration, String destination, String userID, String action) {
        this.busPriceID = busPriceID;
        this.busName = busName;
        this.price = price;
        this.duration = duration;
        this.destination = destination;
        this.userID = userID;
        this.action = action;
    }

    public String getBusPriceID() {
        return busPriceID;
    }

    public void setBusPriceID(String busPriceID) {
        this.busPriceID = busPriceID;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "BusPriceMaint{" +
                "busPriceID='" + busPriceID + '\'' +
                ", busName='" + busName + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", destination='" + destination + '\'' +
                ", userID='" + userID + '\'' +
                ", action='" + action + '\'' +
                '}';
    }
}
