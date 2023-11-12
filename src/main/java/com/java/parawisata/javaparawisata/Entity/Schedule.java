package com.java.parawisata.javaparawisata.Entity;

import java.time.LocalDateTime;
import java.util.Date;

public class Schedule {
    private String idSchedule;
    private Date scheduleDate;
    private String pickUpPoint;
    private String destinationTour;

    public Schedule() {
    }

    public Schedule(String idSchedule, Date scheduleDate, String pickUpPoint, String destinationTour) {
        this.idSchedule = idSchedule;
        this.scheduleDate = scheduleDate;
        this.pickUpPoint = pickUpPoint;
        this.destinationTour = destinationTour;
    }

    public String getIdSchedule() {
        return idSchedule;
    }

    public void setIdSchedule(String idSchedule) {
        this.idSchedule = idSchedule;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
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
}
