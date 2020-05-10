package com.hsa.labs.time.namaz.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.hsa.labs.time.namaz.domain.AdjustingMethodsForHigherLatitudes;
import com.hsa.labs.time.namaz.domain.CalculationMethods;
import com.hsa.labs.time.namaz.domain.CalculationValue;
import com.hsa.labs.time.namaz.domain.JuristicMethods;
import com.hsa.labs.time.namaz.domain.TimeFormatter;
import com.hsa.labs.time.namaz.rest.PrayerTimeRequest;
import com.hsa.labs.time.namaz.rest.PrayerTimeResponse;
import com.hsa.labs.time.namaz.service.PrayerTimeService;
import com.hsa.labs.time.namaz.utils.PrayerTimeTransformer;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NamazTimeController {

    @Autowired
    private PrayerTimeService prayerTimeService;

    @Autowired
    private PrayerTimeTransformer prayerTimeTransformer;

    @PostMapping("/todayPrayerTime")
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

        return prayerTimeTransformer.transform(prayerTimes);
    }

    @GetMapping("/next")
    public String getNextPrayerName() {

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
                42.425,
                -83.3744,
                -4);

        PrayerTimeResponse ptr = prayerTimeTransformer.transform(prayerTimes);


        DateTime currentDateTime = DateTime.now();

        LocalDate date = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm a");
        DateTime fajarDateTime = DateTime.parse(date.toString() + " " + ptr.getFajar(), dateTimeFormatter);
        DateTime dhuhrDateTime = DateTime.parse(date.toString() + " " + ptr.getDhuhr(), dateTimeFormatter);
        DateTime asrDateTime = DateTime.parse(date.toString() + " " + ptr.getAsr(), dateTimeFormatter);
        DateTime mughribDateTime = DateTime.parse(date.toString() + " " + ptr.getMaghrib(), dateTimeFormatter);
        DateTime ishaDateTime = DateTime.parse(date.toString() + " " + ptr.getIsha(), dateTimeFormatter);

        if (currentDateTime.isBefore(fajarDateTime)) {
            return ptr.getFajar();
        } else if (currentDateTime.isBefore(dhuhrDateTime)) {
            return ptr.getDhuhr();
        } else if (currentDateTime.isBefore(asrDateTime)) {
            return ptr.getAsr();
        } else if (currentDateTime.isBefore(mughribDateTime)) {
            return ptr.getMaghrib();
        } else if (currentDateTime.isBefore(ishaDateTime)) {
            return ptr.getIsha();
        } else if (currentDateTime.isAfter(ishaDateTime)) {
            cal.add(Calendar.DATE, 1);
            return prayerTimeTransformer.transform(prayerTimeService.getPrayerTimes(cal,
                    42.425,
                    -83.3744,
                    -4)).getFajar();
        }

        return "Error: Unable to Calucate Time";
    }
}
