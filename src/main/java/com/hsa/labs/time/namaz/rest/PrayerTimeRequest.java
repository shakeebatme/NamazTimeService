package com.hsa.labs.time.namaz.rest;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Setter
@Getter
@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrayerTimeRequest {

    Calendar date;
    double latitude;
    double longitude;
    double tZone;
    private int dhuhrMinutes; // minutes after mid-day for Dhuhr
}
