package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Instant;

public class ParkingService {

    private static final Logger logger = LogManager.getLogger("ParkingService");

    final private static FareCalculatorService fareCalculatorService = new FareCalculatorService();

    final private InputReaderUtil inputReaderUtil;
    final private ParkingSpotDAO parkingSpotDAO;
    final private TicketDAO ticketDAO;

    public ParkingService(InputReaderUtil inputReaderUtil, ParkingSpotDAO parkingSpotDAO, TicketDAO ticketDAO){
        this.inputReaderUtil = inputReaderUtil;
        this.parkingSpotDAO = parkingSpotDAO;
        this.ticketDAO = ticketDAO;
    }

    public void processIncomingVehicle() {
        try{
            ParkingSpot parkingSpot = getNextParkingNumberIfAvailable();

            String vehicleRegNumber = getVehicleRegNumber();
            parkingSpot.setAvailable(false);
            parkingSpotDAO.updateParking(parkingSpot);//allot this parking space and mark it's availability as false

            long  inTime = Instant.now().toEpochMilli();
            Ticket ticket = new Ticket();
            ticket.setParkingSpot(parkingSpot);
            ticket.setVehicleRegNumber(vehicleRegNumber);
            ticket.setPrice(0);
            ticket.setInTime(inTime);
            ticket.setOutTime(0);
            ticketDAO.saveNewTicket(ticket);
            System.out.println("Generated Ticket and saved in DB");
            System.out.println("Please park your vehicle in spot number:"+parkingSpot.getId());
            System.out.println("Recorded in-time for vehicle number:"+vehicleRegNumber+" is:"+inTime);

        }catch(Exception e){
            logger.error("Unable to process incoming vehicle",e);
        }
    }

    private String getVehicleRegNumber() throws Exception {
        System.out.println("Please type the vehicle registration number and press enter key");
        return inputReaderUtil.readVehicleRegistrationNumber();
    }

    public ParkingSpot getNextParkingNumberIfAvailable(){
        ParkingSpot parkingSpot = null;
        try{
            ParkingType parkingType = getVehicleType();
            int parkingNumber = parkingSpotDAO.getNextAvailableSlot(parkingType);
            if(parkingNumber > 0){
                parkingSpot = new ParkingSpot(parkingNumber,parkingType, true);
            }
        }
        catch(Exception e){logger.error("Error getting next available spot", e);}
        return parkingSpot;
    }

    private ParkingType getVehicleType(){
        System.out.println("Please select vehicle type from menu");
        System.out.println("1 CAR");
        System.out.println("2 BIKE");
        int input = inputReaderUtil.readSelection();
        switch(input){
            case 1: {
                return ParkingType.CAR;
            }
            case 2: {
                return ParkingType.BIKE;
            }
            default: {
                System.out.println("Incorrect input provided");
                throw new IllegalArgumentException("Entered input is invalid");
            }
        }
    }

    public void processExitingVehicle() {
        try {
            Ticket ticket = ticketDAO.getTicket(getVehicleRegNumber());
            long outTime = Instant.now().toEpochMilli();
            ticket.setOutTime(outTime);
            fareCalculatorService.calculateFare(ticket);
            boolean updateSuccess = ticketDAO.updateTicket(ticket);
            if (updateSuccess) {
                ParkingSpot parkingSpot = ticket.getParkingSpot();
                parkingSpot.setAvailable(true);
                parkingSpotDAO.updateParking(parkingSpot);
                System.out.println("Please pay the parking fare:" + ticket.getPrice());
                System.out.println("Recorded out-time for vehicle number:" + ticket.getVehicleRegNumber() + " is:" + outTime);
            }
        }catch(Exception e){
            logger.error("Unable to process exiting vehicle",e);
        }
    }

}
