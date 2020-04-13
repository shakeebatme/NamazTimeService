package com.hsa.labs.time.namaz.domain;

import org.springframework.stereotype.Component;

@Component
public class TimeFormatter {

    // Time Formats
    private boolean time24; // 24-hour format
    private boolean Time12; // 12-hour format
    private boolean Time12NS; // 12-hour format with no suffix
    private boolean Floating; // floating point number

    public TimeFormatter(boolean time24, boolean Time12, boolean Time12NS, boolean Floating) {
        this.time24 = time24;
        this.Time12 = Time12;
        this.Time12NS = Time12NS;
        this.Floating = Floating;
    }

    public TimeFormatter() {
    }

    public boolean isTime24() {
        return this.time24;
    }

    public boolean isTime12() {
        return this.Time12;
    }

    public boolean isTime12NS() {
        return this.Time12NS;
    }

    public boolean isFloating() {
        return this.Floating;
    }

    public void setTime24(boolean time24) {
        this.time24 = time24;
    }

    public void setTime12(boolean Time12) {
        this.Time12 = Time12;
    }

    public void setTime12NS(boolean Time12NS) {
        this.Time12NS = Time12NS;
    }

    public void setFloating(boolean Floating) {
        this.Floating = Floating;
    }
}
