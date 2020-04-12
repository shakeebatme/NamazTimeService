package com.hsa.labs.time.namaz.domain;

import lombok.*;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Builder
@Component
@AllArgsConstructor
@NoArgsConstructor
public class CalculationValue {

    private double fajrAngle;
    private boolean maghribSelector;
    private double maghribParameterValue;
    private boolean ishaSelector;
    private double ishaParameterValue;
}
