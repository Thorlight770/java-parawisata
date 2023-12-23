package com.java.parawisata.javaparawisata.Entity;

import java.util.ArrayList;
import java.util.List;

public class Dashboard {
    private List<Schedule> schedules;
    private double percentTrip;
    private int totalTrip;
    private double percentPendingTrip;
    private int totalPendingTrip;

    public Dashboard() {
        schedules = new ArrayList<>();
    }

    public Dashboard(List<Schedule> schedules, double percentTrip, int totalTrip, double percentPendingTrip, int totalPendingTrip) {
        this.schedules = schedules;
        this.percentTrip = percentTrip;
        this.totalTrip = totalTrip;
        this.percentPendingTrip = percentPendingTrip;
        this.totalPendingTrip = totalPendingTrip;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public double getPercentTrip() {
        return percentTrip;
    }

    public void setPercentTrip(double percentTrip) {
        this.percentTrip = percentTrip;
    }

    public int getTotalTrip() {
        return totalTrip;
    }

    public void setTotalTrip(int totalTrip) {
        this.totalTrip = totalTrip;
    }

    public double getPercentPendingTrip() {
        return percentPendingTrip;
    }

    public void setPercentPendingTrip(double percentPendingTrip) {
        this.percentPendingTrip = percentPendingTrip;
    }

    public int getTotalPendingTrip() {
        return totalPendingTrip;
    }

    public void setTotalPendingTrip(int totalPendingTrip) {
        this.totalPendingTrip = totalPendingTrip;
    }

    @Override
    public String toString() {
        return "Dashboard{" +
                "schedules=" + schedules +
                ", percentTrip=" + percentTrip +
                ", totalTrip=" + totalTrip +
                ", percentPendingTrip=" + percentPendingTrip +
                ", totalPendingTrip=" + totalPendingTrip +
                '}';
    }
}
