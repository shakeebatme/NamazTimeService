package com.hsa.labs.time.namaz.rest;

import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class PrayerTimeRequest {

    Calendar date;
    double latitude;
    double longitude;
    double timeZone;
    private int dhuhrMinutes; // minutes after mid-day for Dhuhr

    public PrayerTimeRequest(Calendar date, double latitude, double longitude, double timeZone, int dhuhrMinutes) {
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeZone = timeZone;
        this.dhuhrMinutes = dhuhrMinutes;
    }

    public PrayerTimeRequest() {
    }

    public static PrayerTimeRequestBuilder builder() {
        return new PrayerTimeRequestBuilder();
    }

    public Calendar getDate() {
        return this.date;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public double getTimeZone() {
        return this.timeZone;
    }

    public int getDhuhrMinutes() {
        return this.dhuhrMinutes;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setTimeZone(double tZone) {
        this.timeZone = tZone;
    }

    public void setDhuhrMinutes(int dhuhrMinutes) {
        this.dhuhrMinutes = dhuhrMinutes;
    }

    public static class PrayerTimeRequestBuilder {
        private Calendar date;
        private double latitude;
        private double longitude;
        private double tZone;
        private int dhuhrMinutes;

        PrayerTimeRequestBuilder() {
        }

        public PrayerTimeRequest.PrayerTimeRequestBuilder date(Calendar date) {
            this.date = date;
            return this;
        }

        public PrayerTimeRequest.PrayerTimeRequestBuilder latitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public PrayerTimeRequest.PrayerTimeRequestBuilder longitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public PrayerTimeRequest.PrayerTimeRequestBuilder tZone(double tZone) {
            this.tZone = tZone;
            return this;
        }

        public PrayerTimeRequest.PrayerTimeRequestBuilder dhuhrMinutes(int dhuhrMinutes) {
            this.dhuhrMinutes = dhuhrMinutes;
            return this;
        }

        public PrayerTimeRequest build() {
            return new PrayerTimeRequest(date, latitude, longitude, tZone, dhuhrMinutes);
        }

        public String toString() {
            return "PrayerTimeRequest.PrayerTimeRequestBuilder(date=" + this.date + ", latitude=" + this.latitude + ", longitude=" + this.longitude + ", tZone=" + this.tZone + ", dhuhrMinutes=" + this.dhuhrMinutes + ")";
        }
    }
}
