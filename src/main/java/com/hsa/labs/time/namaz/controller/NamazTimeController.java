package com.hsa.labs.time.namaz.controller;

import com.hsa.labs.time.namaz.rest.PrayerTimeRequest;
import com.hsa.labs.time.namaz.rest.PrayerTimeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NamazTimeController {
    @GetMapping()
    protected PrayerTimeResponse getTimeForToday(@RequestBody PrayerTimeRequest prayerTimeRequest) {

        return null;
    }
}
