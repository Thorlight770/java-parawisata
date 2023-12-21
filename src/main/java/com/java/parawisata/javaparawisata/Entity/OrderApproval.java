package com.java.parawisata.javaparawisata.Entity;

import java.sql.Date;
import java.time.LocalDate;

public class OrderApproval {
    private Date orderDate;
    private Long idHist;
    private String customerID;
    private String customerName;
    private String busID;
    private String busName;
    private String driverID;
    private String driverName;
    private String pickUpPoint;
    private String destination;
    private Long duration;
    private String statusPayment;
    private String fileName;
    private String administratorID;
    private String reason;

    public OrderApproval() {
    }

    public OrderApproval(Date orderDate, Long idHist, String customerID, String customerName, String busID, String busName, String driverID, String driverName, String pickUpPoint, String destination, Long duration, String statusPayment, String fileName, String administratorID, String reason) {
        this.orderDate = orderDate;
        this.idHist = idHist;
        this.customerID = customerID;
        this.customerName = customerName;
        this.busID = busID;
        this.busName = busName;
        this.driverID = driverID;
        this.driverName = driverName;
        this.pickUpPoint = pickUpPoint;
        this.destination = destination;
        this.duration = duration;
        this.statusPayment = statusPayment;
        this.fileName = fileName;
        this.administratorID = administratorID;
        this.reason = reason;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Long getIdHist() {
        return idHist;
    }

    public void setIdHist(Long idHist) {
        this.idHist = idHist;
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

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getStatusPayment() {
        return statusPayment;
    }

    public void setStatusPayment(String statusPayment) {
        this.statusPayment = statusPayment;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAdministratorID() {
        return administratorID;
    }

    public void setAdministratorID(String administratorID) {
        this.administratorID = administratorID;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "OrderApproval{" +
                "orderDate=" + orderDate +
                ", idHist=" + idHist +
                ", customerID='" + customerID + '\'' +
                ", customerName='" + customerName + '\'' +
                ", busID='" + busID + '\'' +
                ", busName='" + busName + '\'' +
                ", driverID='" + driverID + '\'' +
                ", driverName='" + driverName + '\'' +
                ", pickUpPoint='" + pickUpPoint + '\'' +
                ", destination='" + destination + '\'' +
                ", duration=" + duration +
                ", statusPayment='" + statusPayment + '\'' +
                ", fileName='" + fileName + '\'' +
                ", administratorID='" + administratorID + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
