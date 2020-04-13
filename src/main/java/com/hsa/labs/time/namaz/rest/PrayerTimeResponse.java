package com.hsa.labs.time.namaz.rest;

public class PrayerTimeResponse {
    private String fajar;
    private String sunrise;
    private String dhuhr;
    private String asr;
    private String sunset;
    private String maghrib;
    private String isha;

    public PrayerTimeResponse(String fajar, String sunrise, String dhuhr, String asr, String sunset, String maghrib, String isha) {
        this.fajar = fajar;
        this.sunrise = sunrise;
        this.dhuhr = dhuhr;
        this.asr = asr;
        this.sunset = sunset;
        this.maghrib = maghrib;
        this.isha = isha;
    }

    public PrayerTimeResponse() {
    }

    public String getFajar() {
        return this.fajar;
    }

    public String getSunrise() {
        return this.sunrise;
    }

    public String getDhuhr() {
        return this.dhuhr;
    }

    public String getAsr() {
        return this.asr;
    }

    public String getSunset() {
        return this.sunset;
    }

    public String getMaghrib() {
        return this.maghrib;
    }

    public String getIsha() {
        return this.isha;
    }

    public void setFajar(String fajar) {
        this.fajar = fajar;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public void setDhuhr(String dhuhr) {
        this.dhuhr = dhuhr;
    }

    public void setAsr(String asr) {
        this.asr = asr;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public void setMaghrib(String maghrib) {
        this.maghrib = maghrib;
    }

    public void setIsha(String isha) {
        this.isha = isha;
    }
}
