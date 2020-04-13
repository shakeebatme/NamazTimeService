package com.hsa.labs.time.namaz.domain;

import org.springframework.stereotype.Component;

@Component
public class AdjustingMethodsForHigherLatitudes {

    // Adjusting Methods for Higher Latitudes
    private boolean None; // No adjustment
    private boolean MidNight; // middle of night
    private boolean OneSeventh; // 1/7th of night
    private boolean AngleBased; // angle/60th of night

    public AdjustingMethodsForHigherLatitudes(boolean None, boolean MidNight, boolean OneSeventh, boolean AngleBased) {
        this.None = None;
        this.MidNight = MidNight;
        this.OneSeventh = OneSeventh;
        this.AngleBased = AngleBased;
    }

    public AdjustingMethodsForHigherLatitudes() {
    }

    public boolean isNone() {
        return this.None;
    }

    public boolean isMidNight() {
        return this.MidNight;
    }

    public boolean isOneSeventh() {
        return this.OneSeventh;
    }

    public boolean isAngleBased() {
        return this.AngleBased;
    }

    public void setNone(boolean None) {
        this.None = None;
    }

    public void setMidNight(boolean MidNight) {
        this.MidNight = MidNight;
    }

    public void setOneSeventh(boolean OneSeventh) {
        this.OneSeventh = OneSeventh;
    }

    public void setAngleBased(boolean AngleBased) {
        this.AngleBased = AngleBased;
    }
}
