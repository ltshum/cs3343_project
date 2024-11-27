package testSystem;

import static org.junit.Assert.assertEquals;

import java.sql.Time;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import system.Timeslot;

public class TimeslotTest {
    Timeslot timeslot;

    @Before
    public void setUp() {
        timeslot = new Timeslot("12:00 - 13:00");
    }

    @Test
    public void testGetTimeslotSession() {
        assertEquals("12:00 - 13:00", timeslot.getTimeslotSession());
    }

    @Test
    public void testGetTimeslotDate() {
        assertEquals(null, timeslot.getTimeslotDate());
    }

    @Test
    public void testSetTimeslotDate() {
        timeslot.setTimeslotDate(LocalDate.parse("2024-11-28"));
        assertEquals(LocalDate.parse("2024-11-28"), timeslot.getTimeslotDate());
    }
}
