package com.shingetsu.datntruong.Models;

import java.io.Serializable;

public class Trip implements Serializable {
    private String tripId, uid;
    private String carIdTrip;
    private Long priceCartrip;
    private String startDateTrip;
    private String endDateTrip;
    private Boolean hasDriverTrip;
    private String statusTrip;

    public Trip() {
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Trip(String nameCartrip, Long priceCartrip, String startDateTrip, String endDateTrip, Boolean driverTrip, String statusTrip) {
        this.carIdTrip = nameCartrip;
        this.priceCartrip = priceCartrip;
        this.startDateTrip = startDateTrip;
        this.endDateTrip = endDateTrip;
        this.hasDriverTrip = driverTrip;
        this.statusTrip = statusTrip;
    }

    public String getCarIdTrip() {
        return carIdTrip;
    }

    public void setCarIdTrip(String carIdTrip) {
        this.carIdTrip = carIdTrip;
    }

    public Long getPriceCartrip() {
        return priceCartrip;
    }

    public void setPriceCartrip(long priceCartrip) {
        this.priceCartrip = priceCartrip;
    }

    public String getStartDateTrip() {
        return startDateTrip;
    }

    public void setStartDateTrip(String startDateTrip) {
        this.startDateTrip = startDateTrip;
    }

    public String getEndDateTrip() {
        return endDateTrip;
    }

    public void setEndDateTrip(String endDateTrip) {
        this.endDateTrip = endDateTrip;
    }

    public Boolean getHasDriverTrip() {
        return hasDriverTrip;
    }

    public void setHasDriverTrip(Boolean hasDriverTrip) {
        this.hasDriverTrip = hasDriverTrip;
    }

    public String getStatusTrip() {
        return statusTrip;
    }

    public void setStatusTrip(String statusTrip) {
        this.statusTrip = statusTrip;
    }
}
