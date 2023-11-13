package com.java.parawisata.javaparawisata.Entity;

import java.util.Date;

public class HistoryOrder {
    private Long idHist;
    private String orderID;
    private Date orderDate;
    private String customerID;
    private String busID;
    private String driverID;
    private String pickUpPoint;
    private String destination;
    private Boolean status;
    private Integer review;
    private String reviewDesc;
    private Date createdDate;
    private Date updateDate;

    public HistoryOrder() {
    }

    public HistoryOrder(Long idHist, String orderID, Date orderDate, String customerID, String busID, String driverID, String pickUpPoint, String destination, Boolean status, Integer review, String reviewDesc, Date createdDate, Date updateDate) {
        this.idHist = idHist;
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.customerID = customerID;
        this.busID = busID;
        this.driverID = driverID;
        this.pickUpPoint = pickUpPoint;
        this.destination = destination;
        this.status = status;
        this.review = review;
        this.reviewDesc = reviewDesc;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
    }

    public Long getIdHist() {
        return idHist;
    }

    public void setIdHist(Long idHist) {
        this.idHist = idHist;
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
