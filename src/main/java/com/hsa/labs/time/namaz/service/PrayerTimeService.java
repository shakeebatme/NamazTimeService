package com.hsa.labs.time.namaz.service;


import com.hsa.labs.time.namaz.domain.*;
import com.hsa.labs.time.namaz.utils.AstronomicalFunctions;
import com.hsa.labs.time.namaz.utils.TrigonometricFunctions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@Service
public class PrayerTimeService {

    private int dhuhrMinutes; // minutes after mid-day for Dhuhr
    private double lat; // latitude
    private double lng; // longitude
    private double timeZone; // time-zone
    private double JDate; // Julian date

    // Calculation Methods
    private TimeFormatter timeFormatter;
    private JuristicMethods juristicMethods;
    private CalculationValue calculationValue;
    private TrigonometricFunctions tf;
    private AstronomicalFunctions af;
    private AdjustingMethodsForHigherLatitudes adjustingMethodsForHigherLatitudes;

    // Time Names
    private ArrayList<String> timeNames;
    private String invalidTime; // The string used for invalid times

    // ------------------- Calc Method Parameters --------------------
    private int[] offsets;


    public PrayerTimeService(TimeFormatter timeFormatter, JuristicMethods juristicMethods, CalculationValue calculationValue, TrigonometricFunctions tf, AstronomicalFunctions af, AdjustingMethodsForHigherLatitudes adjustingMethodsForHigherLatitudes) {
        this.timeFormatter = timeFormatter;
        this.juristicMethods = juristicMethods;
        this.calculationValue = calculationValue;
        this.tf = tf;
        this.af = af;
        this.adjustingMethodsForHigherLatitudes = adjustingMethodsForHigherLatitudes;

        // Time Names
        timeNames = new ArrayList<>();
        timeNames.add("Fajr");
        timeNames.add("Sunrise");
        timeNames.add("Dhuhr");
        timeNames.add("Asr");
        timeNames.add("Sunset");
        timeNames.add("Maghrib");
        timeNames.add("Isha");

        invalidTime = "-----"; // The string used for invalid times

        // Tuning offsets {fajr, sunrise, dhuhr, asr, sunset, maghrib, isha}
        offsets = new int[7];

        this.setDhuhrMinutes(0);
    }

    // ---------------------- Misc Functions -----------------------
    // compute the difference between two times
    private double timeDiff(double time1, double time2) {
        return tf.fixhour(time2 - time1);
    }

    // -------------------- Interface Functions --------------------
    // return prayer times for a given date
    public ArrayList<String> getPrayerTimes(Calendar date, double latitude,
                                            double longitude, double tZone) {
        int[] offsets = {0, 0, 0, 0, 0, 0, 0}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
        tune(offsets);
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DATE);

        return getDatePrayerTimes(year, month + 1, day, latitude, longitude, tZone);
    }

    private ArrayList<String> getDatePrayerTimes(int year, int month, int day,
                                                 double latitude, double longitude, double tZone) {
        this.setLat(latitude);
        this.setLng(longitude);
        this.setTimeZone(tZone);
        this.setJDate(tf.julianDate(year, month, day));
        double lonDiff = longitude / (15.0 * 24.0);
        this.setJDate(this.getJDate() - lonDiff);
        return computeDayTimes();
    }

    // compute prayer times at given julian date
    private ArrayList<String> computeDayTimes() {
        double[] times = {5, 6, 12, 13, 18, 18, 18}; // default times
        times = computePrayerTimes(times);
        times = adjustTimes(times);
        for (int i = 0; i < times.length; i++) {
            times[i] = times[i] + this.offsets[i] / 60.0;
        }
        return adjustTimesFormat(times);
    }

    // compute prayer times at given julian date
    private double[] computePrayerTimes(double[] times) {

        double[] t = dayPortion(times);

        double fajr = this.computeTime(180 - this.calculationValue.getFajrAngle(), t[0]);

        double sunrise = this.computeTime(180 - 0.833, t[1]);

        double Dhuhr = af.computeMidDay(t[2], this.getJDate());
        double Asr = this.computeAsr(this.juristicMethods, t[3]);
        double Sunset = this.computeTime(0.833, t[4]);

        double Maghrib = this.computeTime(this.calculationValue.getMaghribParameterValue(), t[5]);
        double Isha = this.computeTime(this.calculationValue.getIshaParameterValue(), t[6]);

        return new double[]{fajr, sunrise, Dhuhr, Asr, Sunset, Maghrib, Isha};
    }

    // compute time for a given angle G
    private double computeTime(double G, double t) {

        double D = af.sunDeclination(this.getJDate() + t);
        double Z = af.computeMidDay(t, this.getJDate());
        double Beg = -tf.dsin(G) - tf.dsin(D) * tf.dsin(this.getLat());
        double Mid = tf.dcos(D) * tf.dcos(this.getLat());
        double V = tf.darccos(Beg / Mid) / 15.0;

        return Z + (G > 90 ? -V : V);
    }

    // convert double hours to 24h format
    public String floatToTime24(double time) {

        String result;

        if (Double.isNaN(time)) {
            return invalidTime;
        }

        time = tf.fixhour(time + 0.5 / 60.0); // add 0.5 minutes to round
        int hours = (int) Math.floor(time);
        double minutes = Math.floor((time - hours) * 60.0);

        if ((hours >= 0 && hours <= 9) && (minutes >= 0 && minutes <= 9)) {
            result = "0" + hours + ":0" + Math.round(minutes);
        } else if ((hours >= 0 && hours <= 9)) {
            result = "0" + hours + ":" + Math.round(minutes);
        } else if ((minutes >= 0 && minutes <= 9)) {
            result = hours + ":0" + Math.round(minutes);
        } else {
            result = hours + ":" + Math.round(minutes);
        }
        return result;
    }

    // convert double hours to 12h format
    public String floatToTime12(double time, boolean noSuffix) {

        if (Double.isNaN(time)) {
            return invalidTime;
        }

        time = tf.fixhour(time + 0.5 / 60); // add 0.5 minutes to round
        int hours = (int) Math.floor(time);
        double minutes = Math.floor((time - hours) * 60);
        String suffix, result;
        if (hours >= 12) {
            suffix = "pm";
        } else {
            suffix = "am";
        }
        hours = ((((hours + 12) - 1) % (12)) + 1);
        /*hours = (hours + 12) - 1;
        int hrs = (int) hours % 12;
        hrs += 1;*/
        if (!noSuffix) {
            if ((hours >= 0 && hours <= 9) && (minutes >= 0 && minutes <= 9)) {
                result = "0" + hours + ":0" + Math.round(minutes) + " "
                        + suffix;
            } else if ((hours >= 0 && hours <= 9)) {
                result = "0" + hours + ":" + Math.round(minutes) + " " + suffix;
            } else if ((minutes >= 0 && minutes <= 9)) {
                result = hours + ":0" + Math.round(minutes) + " " + suffix;
            } else {
                result = hours + ":" + Math.round(minutes) + " " + suffix;
            }

        } else {
            if ((hours >= 0 && hours <= 9) && (minutes >= 0 && minutes <= 9)) {
                result = "0" + hours + ":0" + Math.round(minutes);
            } else if ((hours >= 0 && hours <= 9)) {
                result = "0" + hours + ":" + Math.round(minutes);
            } else if ((minutes >= 0 && minutes <= 9)) {
                result = hours + ":0" + Math.round(minutes);
            } else {
                result = hours + ":" + Math.round(minutes);
            }
        }
        return result;

    }

    // convert double hours to 12h format with no suffix
    @SuppressWarnings(value = "unused")
    public String floatToTime12NS(double time) {
        return floatToTime12(time, true);
    }

    // ---------------------- Compute Prayer Times -----------------------


    // compute the time of Asr
    // Shafii: step=1, Hanafi: step=2
    private double computeAsr(JuristicMethods juristicMethods, double t) {
        double step = 1;
        if (juristicMethods.isHanafi()) {
            step = 2;
        }
        double D = af.sunDeclination(this.getJDate() + t);
        double G = -tf.darccot(step + tf.dtan(Math.abs(this.getLat() - D)));
        return computeTime(G, t);
    }

    // adjust times in a prayer time array
    private double[] adjustTimes(double[] times) {
        for (int i = 0; i < times.length; i++) {
            times[i] += this.getTimeZone() - this.getLng() / 15;
        }

        times[2] += this.getDhuhrMinutes() / 60; // Dhuhr
        if (this.calculationValue.isMaghribSelector()) {
            times[5] = times[4] + this.calculationValue.getMaghribParameterValue() / 60;
        }
        if (this.calculationValue.isIshaSelector()) // Isha
        {
            times[6] = times[5] + this.calculationValue.getIshaParameterValue() / 60;
        }

        if (!this.adjustingMethodsForHigherLatitudes.isNone()) {
            adjustHighLatTimes(times);
        }

        return times;
    }

    // convert times array to given time format
    private ArrayList<String> adjustTimesFormat(double[] times) {

        ArrayList<String> result = new ArrayList<String>();

        if (this.getTimeFormatter().isFloating()) {
            for (double time : times) {
                result.add(String.valueOf(time));
            }
            return result;
        }

        for (int i = 0; i < 7; i++) {
            if (this.getTimeFormatter().isTime12()) {
                result.add(floatToTime12(times[i], false));
            } else if (this.getTimeFormatter().isTime12NS()) {
                result.add(floatToTime12(times[i], true));
            } else {
                result.add(floatToTime24(times[i]));
            }
        }
        return result;
    }

    // adjust Fajr, Isha and Maghrib for locations in higher latitudes
    private double[] adjustHighLatTimes(double[] times) {
        double nightTime = timeDiff(times[4], times[1]); // sunset to sunrise

        // Adjust Fajr
        double FajrDiff = nightPortion(this.calculationValue.getFajrAngle()) * nightTime;

        if (Double.isNaN(times[0]) || timeDiff(times[0], times[1]) > FajrDiff) {
            times[0] = times[1] - FajrDiff;
        }

        // Adjust Isha
        double IshaAngle = (!this.calculationValue.isIshaSelector()) ? this.calculationValue.getIshaParameterValue() : 18;
        double IshaDiff = this.nightPortion(IshaAngle) * nightTime;
        if (Double.isNaN(times[6]) || this.timeDiff(times[4], times[6]) > IshaDiff) {
            times[6] = times[4] + IshaDiff;
        }

        // Adjust Maghrib
        double MaghribAngle = (!this.calculationValue.isMaghribSelector()) ? this.calculationValue.getMaghribParameterValue() : 4;
        double MaghribDiff = nightPortion(MaghribAngle) * nightTime;
        if (Double.isNaN(times[5]) || this.timeDiff(times[4], times[5]) > MaghribDiff) {
            times[5] = times[4] + MaghribDiff;
        }

        return times;
    }

    // the night portion used for adjusting times in higher latitudes
    private double nightPortion(double angle) {
        double calc = 0;

        if (this.adjustingMethodsForHigherLatitudes.isAngleBased())
            calc = (angle) / 60.0;
        else if (this.adjustingMethodsForHigherLatitudes.isMidNight())
            calc = 0.5;
        else if (this.adjustingMethodsForHigherLatitudes.isOneSeventh())
            calc = 0.14286;

        return calc;
    }

    // convert hours to day portions
    private double[] dayPortion(double[] times) {
        for (int i = 0; i < 7; i++) {
            times[i] /= 24;
        }
        return times;
    }

    // Tune timings for adjustments
    // Set time offsets
    public void tune(int[] offsetTimes) {

        for (int i = 0; i < offsetTimes.length; i++) { // offsetTimes length
            // should be 7 in order
            // of Fajr, Sunrise,
            // Dhuhr, Asr, Sunset,
            // Maghrib, Isha
            this.offsets[i] = offsetTimes[i];
        }
    }

    private double[] tuneTimes(double[] times) {
        for (int i = 0; i < times.length; i++) {
            times[i] = times[i] + this.offsets[i] / 60.0;
        }

        return times;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        double latitude = 42.479064;
        double longitude = -83.377222;
        double timezone = -4;
        // Test Prayer times here
        TimeFormatter timeFormatter = new TimeFormatter(false, true, false, false);
        CalculationValue isna = new CalculationMethods().getIsna();
        JuristicMethods juristicMethods = new JuristicMethods(false, true);
        AdjustingMethodsForHigherLatitudes higherLatitudes = new AdjustingMethodsForHigherLatitudes(false, false, false, true);
        TrigonometricFunctions trigonometricFunctions = new TrigonometricFunctions();
        AstronomicalFunctions astronomicalFunctions = new AstronomicalFunctions(trigonometricFunctions);
        PrayerTimeService prayers = new PrayerTimeService(timeFormatter,
                juristicMethods,
                isna,
                trigonometricFunctions,
                astronomicalFunctions,
                higherLatitudes);

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);

        ArrayList<String> prayerTimes = prayers.getPrayerTimes(cal, latitude, longitude, timezone);
        ArrayList<String> prayerNames = prayers.getTimeNames();

        for (int i = 0; i < prayerTimes.size(); i++) {
            System.out.println(prayerNames.get(i) + " - " + prayerTimes.get(i));
        }

    }

    public int getDhuhrMinutes() {
        return this.dhuhrMinutes;
    }

    public double getLat() {
        return this.lat;
    }

    public double getLng() {
        return this.lng;
    }

    public double getTimeZone() {
        return this.timeZone;
    }

    public double getJDate() {
        return this.JDate;
    }

    public TimeFormatter getTimeFormatter() {
        return this.timeFormatter;
    }

    public JuristicMethods getJuristicMethods() {
        return this.juristicMethods;
    }

    public CalculationValue getCalculationValue() {
        return this.calculationValue;
    }

    public TrigonometricFunctions getTf() {
        return this.tf;
    }

    public AstronomicalFunctions getAf() {
        return this.af;
    }

    public AdjustingMethodsForHigherLatitudes getAdjustingMethodsForHigherLatitudes() {
        return this.adjustingMethodsForHigherLatitudes;
    }

    public ArrayList<String> getTimeNames() {
        return this.timeNames;
    }

    public String getInvalidTime() {
        return this.invalidTime;
    }

    public int[] getOffsets() {
        return this.offsets;
    }

    public void setDhuhrMinutes(int dhuhrMinutes) {
        this.dhuhrMinutes = dhuhrMinutes;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setTimeZone(double timeZone) {
        this.timeZone = timeZone;
    }

    public void setJDate(double JDate) {
        this.JDate = JDate;
    }

    public void setTimeFormatter(TimeFormatter timeFormatter) {
        this.timeFormatter = timeFormatter;
    }

    public void setJuristicMethods(JuristicMethods juristicMethods) {
        this.juristicMethods = juristicMethods;
    }

    public void setCalculationValue(CalculationValue calculationValue) {
        this.calculationValue = calculationValue;
    }

    public void setTf(TrigonometricFunctions tf) {
        this.tf = tf;
    }

    public void setAf(AstronomicalFunctions af) {
        this.af = af;
    }

    public void setAdjustingMethodsForHigherLatitudes(AdjustingMethodsForHigherLatitudes adjustingMethodsForHigherLatitudes) {
        this.adjustingMethodsForHigherLatitudes = adjustingMethodsForHigherLatitudes;
    }

    public void setTimeNames(ArrayList<String> timeNames) {
        this.timeNames = timeNames;
    }

    public void setInvalidTime(String invalidTime) {
        this.invalidTime = invalidTime;
    }

    public void setOffsets(int[] offsets) {
        this.offsets = offsets;
    }
}
