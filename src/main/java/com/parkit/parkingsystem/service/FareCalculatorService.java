package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;
public class FareCalculatorService {


    public void calculateFare(Ticket ticket){
        System.out.println("Is Recurent = " + ticket.isRecurrent());
        if( (ticket.getOutTime() == 0) || (ticket.getOutTime()<=(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime());
        }
        double price = 0.0;
        long duration = ticket.getOutTime() - ticket.getInTime(); // In MS
        long chargedDuration = (long)(duration - Fare.FREE_DURATION_IN_MS());
        if (chargedDuration >= 1) {
            switch (ticket.getParkingSpot().getParkingType()){
                case CAR: {
                    price = chargedDuration * Fare.CAR_RATE_PER_MS();
                    break;
                }
                case BIKE: {
                    price = chargedDuration * Fare.BIKE_RATE_PER_MS();
                    break;
                }
                default: throw new IllegalArgumentException("Unkown Parking Type");
            }

            if (ticket.isRecurrent()) {
                price -= price * Fare.RECURRENCE_DISCOUNT_RATE;
            }
        }

        ticket.setPrice(Fare.ROUND_PRICE_DECIMAL(price));
    }
}