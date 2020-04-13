package com.hsa.labs.time.namaz.utils;

import com.hsa.labs.time.namaz.rest.PrayerTimeResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PrayerTimeTransformer {

    public PrayerTimeResponse transform(ArrayList<String> prayerTimes) {
        PrayerTimeResponse prayerTimeResponse = new PrayerTimeResponse();
        if (prayerTimes != null && prayerTimes.size() == 7) {
            prayerTimeResponse.setFajar(prayerTimes.get(0));
            prayerTimeResponse.setSunrise(prayerTimes.get(1));
            prayerTimeResponse.setDhuhr(prayerTimes.get(2));
            prayerTimeResponse.setAsr(prayerTimes.get(3));
            prayerTimeResponse.setSunset(prayerTimes.get(4));
            prayerTimeResponse.setMaghrib(prayerTimes.get(5));
            prayerTimeResponse.setIsha(prayerTimes.get(6));
        }
        return prayerTimeResponse;
    }
}
