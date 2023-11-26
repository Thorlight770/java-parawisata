package com.java.parawisata.javaparawisata.Entity;

import java.text.DecimalFormat;
import java.util.Date;

public class BusPrice {
    private String busPriceID;
    private String busName;
    private DecimalFormat price;
    private Integer duration;
    private String destination;
    private String userID;
    private String supervisorID;
    private Date cretedDate;
    private Date updateDate;

    public BusPrice() {
    }

    public BusPrice(String busPriceID, String busName, DecimalFormat price, Integer duration, String destination, String userID, String supervisorID, Date cretedDate, Date updateDate) {
        this.busPriceID = busPriceID;
        this.busName = busName;
        this.price = price;
        this.duration = duration;
        this.destination = destination;
        this.userID = userID;
        this.supervisorID = supervisorID;
        this.cretedDate = cretedDate;
        this.updateDate = updateDate;
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

    public DecimalFormat getPrice() {
        return price;
    }

    public void setPrice(DecimalFormat price) {
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

    public String getSupervisorID() {
        return supervisorID;
    }

    public void setSupervisorID(String supervisorID) {
        this.supervisorID = supervisorID;
    }

    public Date getCretedDate() {
        return cretedDate;
    }

    public void setCretedDate(Date cretedDate) {
        this.cretedDate = cretedDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
