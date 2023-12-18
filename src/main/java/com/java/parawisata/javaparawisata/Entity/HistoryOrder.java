package com.java.parawisata.javaparawisata.Entity;

import java.util.Date;

public class HistoryOrder {
    private Long idHist;
    private String orderID;
    private Date orderDate;
    private String busName;
    private String driverName;
    private String pickUpPoint;
    private String destination;
    private String status;
    private String reason;

    public HistoryOrder() {
    }

    public HistoryOrder(Long idHist, String orderID, Date orderDate, String busName, String driverName, String pickUpPoint, String destination, String status, String reason) {
        this.idHist = idHist;
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.busName = busName;
        this.driverName = driverName;
        this.pickUpPoint = pickUpPoint;
        this.destination = destination;
        this.status = status;
        this.reason = reason;
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

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "HistoryOrder{" +
                "idHist=" + idHist +
                ", orderID='" + orderID + '\'' +
                ", orderDate=" + orderDate +
                ", busName='" + busName + '\'' +
                ", driverName='" + driverName + '\'' +
                ", pickUpPoint='" + pickUpPoint + '\'' +
                ", destination='" + destination + '\'' +
                ", status='" + status + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
