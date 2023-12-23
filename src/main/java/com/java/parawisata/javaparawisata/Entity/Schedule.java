package com.java.parawisata.javaparawisata.Entity;

import java.time.LocalDateTime;
import java.util.Date;

public class Schedule {
    private String idSchedule;
    private Date dateFrom;
    private Date dateTo;
    private String pickUpPoint;
    private String destinationTour;

    public Schedule() {
    }

    public Schedule(String idSchedule, Date dateFrom, Date dateTo, String pickUpPoint, String destinationTour) {
        this.idSchedule = idSchedule;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.pickUpPoint = pickUpPoint;
        this.destinationTour = destinationTour;
    }

    public String getIdSchedule() {
        return idSchedule;
    }

    public void setIdSchedule(String idSchedule) {
        this.idSchedule = idSchedule;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public String getPickUpPoint() {
        return pickUpPoint;
    }

    public void setPickUpPoint(String pickUpPoint) {
        this.pickUpPoint = pickUpPoint;
    }

    public String getDestinationTour() {
        return destinationTour;
    }

    public void setDestinationTour(String destinationTour) {
        this.destinationTour = destinationTour;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "idSchedule='" + idSchedule + '\'' +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", pickUpPoint='" + pickUpPoint + '\'' +
                ", destinationTour='" + destinationTour + '\'' +
                '}';
    }
}
