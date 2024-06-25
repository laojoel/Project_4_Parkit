package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.constants.Time;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static com.parkit.parkingsystem.constants.ParkingType.CAR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.testng.AssertJUnit.assertNull;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {
    @InjectMocks
    private ParkingService parkingService;
    @Mock
    private static InputReaderUtil inputReaderUtil;
    @Mock
    private ParkingSpotDAO parkingSpotDAO;
    @Mock
    private TicketDAO ticketDAO;

    @Test
    public void processIncomingVehicleTest() throws Exception{
        // Arrange
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(parkingSpotDAO.getNextAvailableSlot(any(ParkingType.class))).thenReturn(1);
        // Act
        parkingService.processIncomingVehicle();
        // Assert
        verify(ticketDAO).saveNewTicket(any(Ticket.class));
    }

    @Test
    public void processExitingVehicleTest() throws Exception{
        // Arrange
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        Ticket ticket = new Ticket();
        ticket.setInTime(Time.CURRENT_TIME_MINUS_HOUR(1.0));
        ticket.setParkingSpot(new ParkingSpot(1, CAR,false));
        ticket.setVehicleRegNumber("ABCDEF");
        when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
        when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);
        parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        // Act
        parkingService.processExitingVehicle();
        // Assert
        verify(parkingSpotDAO, times(1)).updateParking(any(ParkingSpot.class));
    }

    @Test
    public void processExitingVehicleTestUnableUpdate () throws Exception{
        // Arrange
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        Ticket ticket = new Ticket();
        ticket.setInTime(Time.CURRENT_TIME_MINUS_HOUR(1.0));
        ticket.setParkingSpot(new ParkingSpot(1, CAR,false));
        ticket.setVehicleRegNumber("ABCDEF");
        when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
        when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(false);
        parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        // Act
        parkingService.processExitingVehicle();
        // Assert
        verify(parkingSpotDAO, times(0)).updateParking(any(ParkingSpot.class));
    }

    @Test
    public void getNextParkingNumberIfAvailableTest() throws Exception {
        // Arrange
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(parkingSpotDAO.getNextAvailableSlot(CAR)).thenReturn(1);
        // Act and Assert
        assertEquals(1, parkingService.getNextParkingNumberIfAvailable().getId());
    }

    @Test
    public void getNextParkingNumberIfAvailableParkingNumberNotFound() {
        // Arrange
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(parkingSpotDAO.getNextAvailableSlot(CAR)).thenReturn(0);
        // Act and Assert
        assertNull(parkingService.getNextParkingNumberIfAvailable());
    }

    @Test
    public void testGetNextParkingNumberIfAvailableParkingNumberWrongArgument() {
        // Arrange
        when(inputReaderUtil.readSelection()).thenReturn(3);
        // Act and Assert
        assertNull(parkingService.getNextParkingNumberIfAvailable());
    }

}
