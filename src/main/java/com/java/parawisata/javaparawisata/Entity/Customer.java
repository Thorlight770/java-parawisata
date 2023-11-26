package com.java.parawisata.javaparawisata.Entity;

import java.sql.Date;

public class Customer {
    private String customerID;
    private String customerName;
    private String identityID;
    private String identityType;
    private String gender;
    private String address;
    private String religion;
    private String email;
    private Integer fds;
    private Date createdDate;
    private Date updateDate;

    public Customer() {
    }

    public Customer(String customerID, String customerName, String identityID, String identityType, String gender, String address, String religion, String email, Integer fds, Date createdDate, Date updateDate) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.identityID = identityID;
        this.identityType = identityType;
        this.gender = gender;
        this.address = address;
        this.religion = religion;
        this.email = email;
        this.fds = fds;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getIdentityID() {
        return identityID;
    }

    public void setIdentityID(String identityID) {
        this.identityID = identityID;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getFds() {
        return fds;
    }

    public void setFds(Integer fds) {
        this.fds = fds;
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
}
