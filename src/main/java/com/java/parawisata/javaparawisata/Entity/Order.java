package com.java.parawisata.javaparawisata.Entity;

import java.sql.Date;
import java.time.temporal.Temporal;

public class Order {
    private String orderID;
    private Date orderDate;
    private String customerID;
    private String busID;
    private String driverID;
    private String pickUpPoint;
    private String destination;
    private Integer duration;
    private Boolean status;
    private Integer review;
    private String reviewDesc;
    private String fileName;
    private Date createdDate;
    private Date updateDate;
    private String administratorID;

    public Order() {
    }

    public Order(String orderID, Date orderDate, String customerID, String busID, String driverID, String pickUpPoint, String destination, Integer duration, Boolean status, Integer review, String reviewDesc, String fileName, Date createdDate, Date updateDate, String administratorID) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.customerID = customerID;
        this.busID = busID;
        this.driverID = driverID;
        this.pickUpPoint = pickUpPoint;
        this.destination = destination;
        this.duration = duration;
        this.status = status;
        this.review = review;
        this.reviewDesc = reviewDesc;
        this.fileName = fileName;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
        this.administratorID = administratorID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getBusID() {
        return busID;
    }

    public void setBusID(String busID) {
        this.busID = busID;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getPickUpPoint() {
        return pickUpPoint;
    }

    public void setPickUpPoint(String pickUpPoint) {
        this.pickUpPoint = pickUpPoint;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getReview() {
        return review;
    }

    public void setReview(Integer review) {
        this.review = review;
    }

    public String getReviewDesc() {
        return reviewDesc;
    }

    public void setReviewDesc(String reviewDesc) {
        this.reviewDesc = reviewDesc;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public String getAdministratorID() {
        return administratorID;
    }

    public void setAdministratorID(String administratorID) {
        this.administratorID = administratorID;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID='" + orderID + '\'' +
                ", orderDate=" + orderDate +
                ", customerID='" + customerID + '\'' +
                ", busID='" + busID + '\'' +
                ", driverID='" + driverID + '\'' +
                ", pickUpPoint='" + pickUpPoint + '\'' +
                ", destination='" + destination + '\'' +
                ", duration=" + duration +
                ", status=" + status +
                ", review=" + review +
                ", reviewDesc='" + reviewDesc + '\'' +
                ", fileName='" + fileName + '\'' +
                ", createdDate=" + createdDate +
                ", updateDate=" + updateDate +
                ", administratorID='" + administratorID + '\'' +
                '}';
    }
}
