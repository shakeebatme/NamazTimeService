package com.hsa.labs.time.namaz.rest;

import org.springframework.stereotype.Component;

@Component
public class DayRequest {

    private int timeFormat; // time format
    private double lat; // latitude
    private double lng; // longitude
    private double timeZone; // time-zone

    public int getTimeFormat() {
        return this.timeFormat;
    }

    public double getLat() {
        return this.lat;
    }

    public double getLng() {
        return this.lng;
    }

    public double getTimeZone() {
        return this.timeZone;
    }

    public void setTimeFormat(int timeFormat) {
        this.timeFormat = timeFormat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setTimeZone(double timeZone) {
        this.timeZone = timeZone;
    }
}
