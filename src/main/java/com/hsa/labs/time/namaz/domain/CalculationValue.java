package com.hsa.labs.time.namaz.domain;

import org.springframework.stereotype.Component;

@Component
public class CalculationValue {

    private double fajrAngle;
    private boolean maghribSelector;
    private double maghribParameterValue;
    private boolean ishaSelector;
    private double ishaParameterValue;

    public CalculationValue(double fajrAngle, boolean maghribSelector, double maghribParameterValue, boolean ishaSelector, double ishaParameterValue) {
        this.fajrAngle = fajrAngle;
        this.maghribSelector = maghribSelector;
        this.maghribParameterValue = maghribParameterValue;
        this.ishaSelector = ishaSelector;
        this.ishaParameterValue = ishaParameterValue;
    }

    public CalculationValue() {
    }

    public static CalculationValueBuilder builder() {
        return new CalculationValueBuilder();
    }

    public double getFajrAngle() {
        return this.fajrAngle;
    }

    public boolean isMaghribSelector() {
        return this.maghribSelector;
    }

    public double getMaghribParameterValue() {
        return this.maghribParameterValue;
    }

    public boolean isIshaSelector() {
        return this.ishaSelector;
    }

    public double getIshaParameterValue() {
        return this.ishaParameterValue;
    }

    public void setFajrAngle(double fajrAngle) {
        this.fajrAngle = fajrAngle;
    }

    public void setMaghribSelector(boolean maghribSelector) {
        this.maghribSelector = maghribSelector;
    }

    public void setMaghribParameterValue(double maghribParameterValue) {
        this.maghribParameterValue = maghribParameterValue;
    }

    public void setIshaSelector(boolean ishaSelector) {
        this.ishaSelector = ishaSelector;
    }

    public void setIshaParameterValue(double ishaParameterValue) {
        this.ishaParameterValue = ishaParameterValue;
    }

    public static class CalculationValueBuilder {
        private double fajrAngle;
        private boolean maghribSelector;
        private double maghribParameterValue;
        private boolean ishaSelector;
        private double ishaParameterValue;

        CalculationValueBuilder() {
        }

        public CalculationValue.CalculationValueBuilder fajrAngle(double fajrAngle) {
            this.fajrAngle = fajrAngle;
            return this;
        }

        public CalculationValue.CalculationValueBuilder maghribSelector(boolean maghribSelector) {
            this.maghribSelector = maghribSelector;
            return this;
        }

        public CalculationValue.CalculationValueBuilder maghribParameterValue(double maghribParameterValue) {
            this.maghribParameterValue = maghribParameterValue;
            return this;
        }

        public CalculationValue.CalculationValueBuilder ishaSelector(boolean ishaSelector) {
            this.ishaSelector = ishaSelector;
            return this;
        }

        public CalculationValue.CalculationValueBuilder ishaParameterValue(double ishaParameterValue) {
            this.ishaParameterValue = ishaParameterValue;
            return this;
        }

        public CalculationValue build() {
            return new CalculationValue(fajrAngle, maghribSelector, maghribParameterValue, ishaSelector, ishaParameterValue);
        }

        public String toString() {
            return "CalculationValue.CalculationValueBuilder(fajrAngle=" + this.fajrAngle + ", maghribSelector=" + this.maghribSelector + ", maghribParameterValue=" + this.maghribParameterValue + ", ishaSelector=" + this.ishaSelector + ", ishaParameterValue=" + this.ishaParameterValue + ")";
        }
    }
}
