package com.parkit.parkingsystem.constants;

import java.time.Instant;

public class Time {
    public static long MIN_TO_MS(double MIN) {return (long)(60*MIN*1000);}
    public static long HOUR_TO_MS(double HOUR) {return (long)(60*60*HOUR*1000);}
    public static long DAY_TO_MS(double DAY) {return (long)(60*60*24*DAY*1000);}
    public static double MS_TO_MIN(long MS) {return MS/(1000.0*60.0);}
    public static double MS_TO_HOUR(long MS) {return MS/(1000.0*60.0*60.0);}
    public static double MS_TO_DAY(long MS) {return MS/(1000.0*60.0*60.0*24.0);}
    public static long CURRENT_TIME() {return Instant.now().toEpochMilli();}
    public static long CURRENT_TIME_MINUS_MIN(double MIN) {return CURRENT_TIME() - MIN_TO_MS(MIN);}
    public static long CURRENT_TIME_MINUS_HOUR(double HOUR) {return CURRENT_TIME() - HOUR_TO_MS(HOUR);}
    public static long CURRENT_TIME_MINUS_DAY(double DAY) {return CURRENT_TIME() - DAY_TO_MS(DAY);}
    public static long CURRENT_TIME_PLUS_MIN(long MIN) {return CURRENT_TIME() + MIN_TO_MS(MIN);}
    public static long CURRENT_TIME_PLUS_HOUR(double HOUR) {return CURRENT_TIME() + HOUR_TO_MS(HOUR);}
    public static long CURRENT_TIME_PLUS_DAY(double DAY) {return CURRENT_TIME() + DAY_TO_MS(DAY);}
}
