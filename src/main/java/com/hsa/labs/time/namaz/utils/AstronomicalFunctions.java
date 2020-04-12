package com.hsa.labs.time.namaz.utils;

import org.springframework.stereotype.Component;

@Component
public class AstronomicalFunctions {

    private TrigonometricFunctions tf;

    public AstronomicalFunctions(TrigonometricFunctions tf) {
        this.tf = tf;
    }

    // ---------------------- Calculation Functions -----------------------
    // References:
    // http://www.ummah.net/astronomy/saltime
    // http://aa.usno.navy.mil/faq/docs/SunApprox.html
    // compute declination angle of sun and equation of time
    public double[] sunPosition(double jd) {

        double D = jd - 2451545;
        double g = tf.fixangle(357.529 + 0.98560028 * D);
        double q = tf.fixangle(280.459 + 0.98564736 * D);
        double L = tf.fixangle(q + (1.915 * tf.dsin(g)) + (0.020 * tf.dsin(2 * g)));

        // double R = 1.00014 - 0.01671 * [self dcos:g] - 0.00014 * [self dcos:
        // (2*g)];
        double e = 23.439 - (0.00000036 * D);
        double d = tf.darcsin(tf.dsin(e) * tf.dsin(L));
        double RA = (tf.darctan2((tf.dcos(e) * tf.dsin(L)), (tf.dcos(L)))) / 15.0;
        RA = tf.fixhour(RA);
        double EqT = q / 15.0 - RA;
        double[] sPosition = new double[2];
        sPosition[0] = d;
        sPosition[1] = EqT;

        return sPosition;
    }

    // compute equation of time
    public double equationOfTime(double jd) {
        double eq = sunPosition(jd)[1];
        return eq;
    }

    // compute declination angle of sun
    public double sunDeclination(double jd) {
        double d = sunPosition(jd)[0];
        return d;
    }

    // compute mid-day (Dhuhr, Zawal) time
    public double computeMidDay(double t, double jd) {
        double T = equationOfTime(jd + t);
        double Z = tf.fixhour(12 - T);
        return Z;
    }
}
