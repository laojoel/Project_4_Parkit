package com.parkit.parkingsystem;
import com.parkit.parkingsystem.config.DataBaseConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.mockito.Mock;
import java.sql.Connection;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.when;

public class DataBaseConfigTest {
    @Mock
    private DataBaseConfig dataBaseConfig;

    @Test
    public void testOpenConnection() {
        dataBaseConfig = new DataBaseConfig();
        Connection con = null;
        try {con = dataBaseConfig.getConnection();} catch (Exception ex){}
        assertNotNull(con);
    }

    @Test
    public void testCloneConnection() {
        dataBaseConfig = new DataBaseConfig();
        Connection con = null; try {con = dataBaseConfig.getConnection();} catch (Exception ex){}
        dataBaseConfig.closeConnection(con);
        boolean isClosed = false; try {isClosed = con.isClosed();} catch (Exception ex){}
        assertTrue(isClosed);
    }

    //when(ticketDAO.getTicket(anyString())).thenReturn(ticket);

}
