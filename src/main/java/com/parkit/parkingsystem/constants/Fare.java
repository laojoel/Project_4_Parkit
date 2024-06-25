package com.parkit.parkingsystem.constants;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Fare {
    public static final double BIKE_RATE_PER_HOUR = 1.0;
    public static final double CAR_RATE_PER_HOUR = 1.5;
    public static final double FREE_DURATION_IN_HOUR = 0.5;
    public static final double RECURRENCE_DISCOUNT_RATE = 0.05;
    public static final double MS_PER_HOUR = 3600000.0;
    public static double BIKE_RATE_PER_MS() {return BIKE_RATE_PER_HOUR/MS_PER_HOUR;}
    public static double CAR_RATE_PER_MS() {return CAR_RATE_PER_HOUR/MS_PER_HOUR;}
    public static double FREE_DURATION_IN_MS() {return FREE_DURATION_IN_HOUR*MS_PER_HOUR;}
    public static double ROUND_PRICE_DECIMAL(double input){return (new BigDecimal(input).setScale(2, RoundingMode.FLOOR)).doubleValue();}
}
