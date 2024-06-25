package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.constants.Time;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;

public class FareCalculatorServiceTest {

    private static FareCalculatorService fareCalculatorService;
    private Ticket ticket;

    @BeforeAll
    public static void setUp() {
        fareCalculatorService = new FareCalculatorService();
    }

    @BeforeEach
    public void setUpPerTest() {
        ticket = new Ticket();
    }

    @Test
    public void calculateShortFareCar_NotRecurrent() {
        int minutes = 15;
        long inTime = Time.CURRENT_TIME();
        long outTime = Time.CURRENT_TIME_PLUS_MIN(minutes);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        ticket.setRecurrent(false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals(ticket.getPrice(), 0.0);
    }
    @Test
    public void calculateShortFareBike_NotRecurrent() {
        int minutes = 15;
        long inTime = Time.CURRENT_TIME();
        long outTime = Time.CURRENT_TIME_PLUS_MIN(minutes);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);
        ticket.setRecurrent(false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals(ticket.getPrice(), 0.0);
    }
    @Test
    public void calculateShortFareCar_Recurrent() {
        int minutes = 15;
        long inTime = Time.CURRENT_TIME();
        long outTime = Time.CURRENT_TIME_PLUS_MIN(minutes);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        ticket.setRecurrent(true);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals(ticket.getPrice(), 0.0);
    }
    @Test
    public void calculateShortFareBike_Recurrent() {
        int minutes = 15;
        long inTime = Time.CURRENT_TIME();
        long outTime = Time.CURRENT_TIME_PLUS_MIN(minutes);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);
        ticket.setRecurrent(true);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals(ticket.getPrice(), 0.0);
    }

    @Test
    public void calculateFareCar_NotRecurrent(){
        double hour = 1.0;
        long inTime = Time.CURRENT_TIME_MINUS_HOUR(hour);
        long outTime = Time.CURRENT_TIME();
        long deltaTime = outTime - inTime;
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        ticket.setRecurrent(false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        double price = Fare.CAR_RATE_PER_MS()*(Time.HOUR_TO_MS(hour)-Fare.FREE_DURATION_IN_MS());
        assertEquals(Fare.ROUND_PRICE_DECIMAL(price), ticket.getPrice());
    }

    @Test
    public void calculateFareBike_NotRecurrent(){
        double hour = 1.0;
        long inTime = Time.CURRENT_TIME_MINUS_HOUR(hour);
        long outTime = Time.CURRENT_TIME();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);
        ticket.setRecurrent(false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        double price = Fare.BIKE_RATE_PER_MS()*(Time.HOUR_TO_MS(hour)-Fare.FREE_DURATION_IN_MS());
        assertEquals(Fare.ROUND_PRICE_DECIMAL(price), ticket.getPrice());
    }

    @Test
    public void calculateFareCar_Recurrent() {
        double hour = 1.0;
        long inTime = Time.CURRENT_TIME_MINUS_HOUR(hour);
        long outTime = Time.CURRENT_TIME();
        long deltaTime = outTime - inTime;
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        ticket.setRecurrent(true);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        double basePrice = Fare.CAR_RATE_PER_MS()*(Time.HOUR_TO_MS(hour)-Fare.FREE_DURATION_IN_MS());
        double discountedPrice = basePrice-(basePrice*Fare.RECURRENCE_DISCOUNT_RATE);
        assertEquals(Fare.ROUND_PRICE_DECIMAL(discountedPrice), ticket.getPrice());
    }
    @Test
    public void calculateFareBike_Recurrent() {
        double hour = 1.0;
        long inTime = Time.CURRENT_TIME_MINUS_HOUR(hour);
        long outTime = Time.CURRENT_TIME();
        long deltaTime = outTime - inTime;
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);
        ticket.setRecurrent(true);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        double basePrice = Fare.BIKE_RATE_PER_MS()*(Time.HOUR_TO_MS(hour)-Fare.FREE_DURATION_IN_MS());
        double discountedPrice = basePrice-(basePrice*Fare.RECURRENCE_DISCOUNT_RATE);
        assertEquals(Fare.ROUND_PRICE_DECIMAL(discountedPrice), ticket.getPrice());
    }

    @Test
    public void calculateFareUnkownType(){
        long inTime = Time.CURRENT_TIME_MINUS_MIN(60);
        long outTime = Time.CURRENT_TIME();
        ParkingSpot parkingSpot = new ParkingSpot(1, null,false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket));
    }

    @Test
    public void calculateFareBikeWithFutureInTime(){
        long inTime = Time.CURRENT_TIME_PLUS_MIN(60);
        long outTime = Time.CURRENT_TIME();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket));
    }
}
