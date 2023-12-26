package com.java.parawisata.javaparawisata.Entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class BusMaint {
    private String busID;
    private String busName;
    private String fasilitas;
    private List<BusPriceMaint> busPrices;
    private Date createdDate;
    private String userID;

    public BusMaint() {
        busPrices = new ArrayList<>();
    }

    public BusMaint(String busID, String busName, String fasilitas, List<BusPriceMaint> busPrices, Date createdDate, String userID) {
        this.busID = busID;
        this.busName = busName;
        this.fasilitas = fasilitas;
        this.busPrices = busPrices;
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

    public List<BusPriceMaint> getBusPrices() {
        return busPrices;
    }

    public void setBusPrices(List<BusPriceMaint> busPrices) {
        this.busPrices = busPrices;
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

    @Override
    public String toString() {
        return "BusMaint{" +
                "busID='" + busID + '\'' +
                ", busName='" + busName + '\'' +
                ", fasilitas='" + fasilitas + '\'' +
                ", busPrices=" + busPrices +
                ", createdDate=" + createdDate +
                ", userID='" + userID + '\'' +
                '}';
    }
}
