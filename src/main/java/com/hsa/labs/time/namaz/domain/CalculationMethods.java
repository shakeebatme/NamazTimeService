package com.hsa.labs.time.namaz.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Setter
@Getter
@Component
public class CalculationMethods {

    // Calculation Methods
    private CalculationValue jafari; // Ithna Ashari;
    private CalculationValue karachi; // University of Islamic Sciences, Karachi
    private CalculationValue isna; // Islamic Society of North America (ISNA)
    private CalculationValue mwl; // Muslim World League (MWL)
    private CalculationValue makkah; // Umm al-Qura, Makkah
    private CalculationValue egypt; // Egyptian General Authority of Survey
    private CalculationValue custom; // Custom Setting
    private CalculationValue tehran; // Institute of Geophysics, University of Tehran

    public CalculationMethods() {
        this.jafari = CalculationValue.builder()
                .fajrAngle(16)
                .maghribSelector(false)
                .maghribParameterValue(4)
                .ishaSelector(false)
                .ishaParameterValue(14)
                .build();

        this.karachi = CalculationValue.builder()
                .fajrAngle(18)
                .maghribSelector(true)
                .maghribParameterValue(0)
                .ishaSelector(false)
                .ishaParameterValue(18)
                .build();

        this.isna = CalculationValue.builder()
                .fajrAngle(15)
                .maghribSelector(true)
                .maghribParameterValue(0)
                .ishaSelector(false)
                .ishaParameterValue(15)
                .build();

        this.mwl = CalculationValue.builder()
                .fajrAngle(18)
                .maghribSelector(true)
                .maghribParameterValue(0)
                .ishaSelector(false)
                .ishaParameterValue(17)
                .build();

        this.makkah = CalculationValue.builder()
                .fajrAngle(18.5)
                .maghribSelector(true)
                .maghribParameterValue(0)
                .ishaSelector(true)
                .ishaParameterValue(90)
                .build();

        this.egypt = CalculationValue.builder()
                .fajrAngle(19.5)
                .maghribSelector(true)
                .maghribParameterValue(0)
                .ishaSelector(false)
                .ishaParameterValue(17.5)
                .build();

        this.tehran = CalculationValue.builder()
                .fajrAngle(17.7)
                .maghribSelector(false)
                .maghribParameterValue(4.5)
                .ishaSelector(false)
                .ishaParameterValue(14)
                .build();

        this.custom = CalculationValue.builder()
                .fajrAngle(18)
                .maghribSelector(true)
                .maghribParameterValue(0)
                .ishaSelector(false)
                .ishaParameterValue(17)
                .build();

    }
}
