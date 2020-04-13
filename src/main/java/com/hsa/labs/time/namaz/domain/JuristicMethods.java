package com.hsa.labs.time.namaz.domain;

import org.springframework.stereotype.Component;

@Component
public class JuristicMethods {

    private boolean shafii; // Shafii (standard)
    private boolean hanafi; // Hanafi

    public JuristicMethods(boolean shafii, boolean hanafi) {
        this.shafii = shafii;
        this.hanafi = hanafi;
    }

    public JuristicMethods() {
    }

    public boolean isShafii() {
        return this.shafii;
    }

    public boolean isHanafi() {
        return this.hanafi;
    }

    public void setShafii(boolean shafii) {
        this.shafii = shafii;
    }

    public void setHanafi(boolean hanafi) {
        this.hanafi = hanafi;
    }
}
