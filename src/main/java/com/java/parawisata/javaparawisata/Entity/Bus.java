package com.java.parawisata.javaparawisata.Entity;

import java.sql.Date;
import java.util.List;

public class Bus {
    private String busID;
    private String busName;
    private String fasilitas;
    private List<BusPrice> priceList;
    private Date createdDate;
    private String userID;

    public Bus() {
    }

    public Bus(String busID, String busName, String fasilitas, List<BusPrice> priceList, Date createdDate, String userID) {
        this.busID = busID;
        this.busName = busName;
        this.fasilitas = fasilitas;
        this.priceList = priceList;
        this.createdDate = createdDate;
        this.userID = userID;
    }

    public String getBusID() {
        return busID;
    }

    public void setBusID(String busID) {
        this.busID = busID;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getFasilitas() {
        return fasilitas;
    }

    public void setFasilitas(String fasilitas) {
        this.fasilitas = fasilitas;
    }

    public List<BusPrice> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<BusPrice> priceList) {
        this.priceList = priceList;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}

