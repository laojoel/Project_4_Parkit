package com.parkit.parkingsystem;
import com.parkit.parkingsystem.constants.Time;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import java.time.Instant;

public class TimeTest {
    @Test
    public void CurrentTime() {
        long FunctionTime = Time.CURRENT_TIME();
        long ExpectedTime = Instant.now().toEpochMilli();
        long Delta = Math.abs(FunctionTime-ExpectedTime);
        Assert.assertTrue(Delta <= 50);
    }

    @Test
    public void Minutes_To_Milliseconds() {
        long Minute = 30;
        Assert.assertEquals(Time.MIN_TO_MS(Minute), 1000*60*Minute);
    }

    @Test
    public void Hours_To_Milliseconds() {
        long Hour = 3;
        Assert.assertEquals(Time.HOUR_TO_MS(Hour), 1000*60*60*Hour);
    }

    @Test
    public void Day_To_Milliseconds() {
        long Day = 3;
        Assert.assertEquals(Time.DAY_TO_MS(Day), 1000*60*60*24*Day);
    }

    @Test
    public void Milliseconds_To_Minutes() {
        Assert.assertEquals(Time.MS_TO_MIN(1800000), 30.0);
    }
    @Test
    public void Milliseconds_To_Hours() {
        Assert.assertEquals(Time.MS_TO_HOUR(21600000), 6.0);
    }
    @Test
    public void Milliseconds_To_Day() {
        Assert.assertEquals(Time.MS_TO_DAY(172800000), 2.0);
    }

    @Test
    public void Future_Time_Min() {
        long Minute = 30;
        long FunctionTime = Time.CURRENT_TIME_PLUS_MIN(Minute);
        long ExpectedTime = Instant.now().toEpochMilli()+(1000*60*Minute);
        long Delta = Math.abs(FunctionTime-ExpectedTime);
        Assert.assertTrue(Delta <= 50);
    }
    @Test
    public void Future_Time_Hour() {
        long Hour = 6;
        long FunctionTime = Time.CURRENT_TIME_PLUS_HOUR(Hour);
        long ExpectedTime = Instant.now().toEpochMilli()+(1000*60*60*Hour);
        long Delta = Math.abs(FunctionTime-ExpectedTime);
        Assert.assertTrue(Delta <= 50);
    }
    @Test
    public void Future_Time_DAY() {
        long Day = 3;
        long FunctionTime = Time.CURRENT_TIME_PLUS_DAY(Day);
        long ExpectedTime = Instant.now().toEpochMilli()+(1000*60*60*24*Day);
        long Delta = Math.abs(FunctionTime-ExpectedTime);
        Assert.assertTrue(Delta <= 50);
    }
    @Test
    public void Past_Time_Min() {
        long Minute = 30;
        long FunctionTime = Time.CURRENT_TIME_MINUS_MIN(Minute);
        long ExpectedTime = Instant.now().toEpochMilli()-(1000*60*Minute);
        long Delta = Math.abs(FunctionTime-ExpectedTime);
        Assert.assertTrue(Delta <= 50);
    }
    @Test
    public void Past_Time_Hour() {
        long Hour = 6;
        long FunctionTime = Time.CURRENT_TIME_MINUS_HOUR(Hour);
        long ExpectedTime = Instant.now().toEpochMilli()-(1000*60*60*Hour);
        long Delta = Math.abs(FunctionTime-ExpectedTime);
        Assert.assertTrue(Delta <= 50);
    }
    @Test
    public void Past_Time_DAY() {
        long Day = 3;
        long FunctionTime = Time.CURRENT_TIME_MINUS_DAY(Day);
        long ExpectedTime = Instant.now().toEpochMilli()-(1000*60*60*24*Day);
        long Delta = Math.abs(FunctionTime-ExpectedTime);
        Assert.assertTrue(Delta <= 50);
    }
}
