package com.java.parawisata.javaparawisata.Entity;

import java.text.DecimalFormat;
import java.util.Date;

public class BusPrice {
    private Long id;
    private String busPriceID;
    private String busName;
    private Double price;
    private Integer duration;
    private String destination;
    private String userID;
    private String supervisorID;
    private Date createdDate;
    private Date updateDate;

    public BusPrice() {
    }

    public BusPrice(Long id, String busPriceID, String busName, Double price, Integer duration, String destination, String userID, String supervisorID, Date createdDate, Date updateDate) {
        this.id = id;
        this.busPriceID = busPriceID;
        this.busName = busName;
        this.price = price;
        this.duration = duration;
        this.destination = destination;
        this.userID = userID;
        this.supervisorID = supervisorID;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getSupervisorID() {
        return supervisorID;
    }

    public void setSupervisorID(String supervisorID) {
        this.supervisorID = supervisorID;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date cretedDate) {
        this.createdDate = cretedDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
