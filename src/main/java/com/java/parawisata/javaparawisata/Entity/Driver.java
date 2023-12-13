package com.java.parawisata.javaparawisata.Entity;

import java.util.Date;

public class Driver {
    private String driverID;
    private String driverName;
    private Date createdDate;
    private Date updateDate;
    private String userID;
    public Driver() {
    }

    public Driver(String driverID, String driverName, Date createdDate, Date updateDate, String userID) {
        this.driverID = driverID;
        this.driverName = driverName;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
        this.userID = userID;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
