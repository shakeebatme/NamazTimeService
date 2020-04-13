package com.hsa.labs.time.namaz.controller;

import com.hsa.labs.time.namaz.domain.*;
import com.hsa.labs.time.namaz.rest.PrayerTimeRequest;
import com.hsa.labs.time.namaz.rest.PrayerTimeResponse;
import com.hsa.labs.time.namaz.service.PrayerTimeService;
import com.hsa.labs.time.namaz.utils.PrayerTimeTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@RestController
public class NamazTimeController {

    @Autowired
    private PrayerTimeService prayerTimeService;

    @Autowired
    private PrayerTimeTransformer prayerTimeTransformer;

    @GetMapping("/todayPrayerTime")
    public PrayerTimeResponse getTimeForToday(@RequestBody PrayerTimeRequest prayerTimeRequest) {

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);

        TimeFormatter timeFormatter = new TimeFormatter(false, true, false, false);
        CalculationValue isna = new CalculationMethods().getIsna();
        JuristicMethods juristicMethods = new JuristicMethods(false, true);
        AdjustingMethodsForHigherLatitudes higherLatitudes = new AdjustingMethodsForHigherLatitudes(false, false, false, true);

        prayerTimeService.setCalculationValue(isna);
        prayerTimeService.setTimeFormatter(timeFormatter);
        prayerTimeService.setJuristicMethods(juristicMethods);
        prayerTimeService.setAdjustingMethodsForHigherLatitudes(higherLatitudes);

        ArrayList<String> prayerTimes = prayerTimeService.getPrayerTimes(cal,
                prayerTimeRequest.getLatitude(),
                prayerTimeRequest.getLongitude(),
                prayerTimeRequest.getTimeZone());

        PrayerTimeResponse prayerTimeResponse = prayerTimeTransformer.transform(prayerTimes);
        return prayerTimeResponse;
    }
}
