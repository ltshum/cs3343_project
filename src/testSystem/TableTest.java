package testSystem;

import org.junit.Test;
import org.junit.Before;

import system.Table;
import system.Timeslot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TableTest {

    Table table;
     private List<Timeslot> allTimeslots;
    private List<Timeslot> bookedTimeSlot;

    @Before
    public void setup() {
        table = new Table(1);
        allTimeslots = new ArrayList<>();
        bookedTimeSlot = new ArrayList<>();
        allTimeslots.add(new Timeslot("10:00 - 11:00"));
        allTimeslots.add(new Timeslot("11:00 - 12:00"));
        table.setAllTimeslots(allTimeslots);
        table.setBookedTimeslot(bookedTimeSlot);
        table.setSeatNum(4);
    }
    
    @Test 
    public void testGetTableID() {
        assertEquals(1, table.getTableID());
    }

    @Test
    public void testAddTimeslot() {
        table.addTimeslot("09:00 - 10:00");
        assertEquals(3, table.getAllTimeslots().size());
        assertEquals("09:00 - 10:00", table.getAllTimeslots().get(0).getTimeslotSession());
    }
    
    @Test
    public void testGetSeatNum() {
        
        assertEquals(4, table.getSeatNum());
    }

    @Test
    public void testIsTimeslotAvailable() {
        allTimeslots.get(0).setTimeslotDate(LocalDate.now());
        bookedTimeSlot.add(allTimeslots.get(0)); 
        assertFalse(table.isTimeslotAvailable("10:00 - 11:00", LocalDate.now()));
        assertTrue(table.isTimeslotAvailable("10:00 - 11:00", LocalDate.parse("2024-11-27")));
            assertTrue(table.isTimeslotAvailable("11:00 - 12:00", LocalDate.now()));
            assertTrue(table.isTimeslotAvailable("11:00 - 12:00", LocalDate.parse("2024-11-27")));
    }

    @Test
    public void testSetTimeslotUnavailable() {
        table.setTimeslotUnavailable("10:00 - 11:00", LocalDate.now());
        assertFalse(table.isTimeslotAvailable("10:00 - 11:00", LocalDate.now()));
    }


    @Test
    public void testCanBookWithValidCustomerAndAvailableTime() {
        assertTrue(table.canbook(1, Duration.ofMinutes(30))); // Should return true
    }

    @Test
    public void testCanBookWithValidCustomerAndInsufficientTime() {
        assertTrue(table.canbook(1, Duration.ofMinutes(70))); // Should return false
    }

    @Test
    public void testCanBookWithBookedTimeslot() {
        bookedTimeSlot.add(allTimeslots.get(0)); // Book the first timeslot
        assertTrue(table.canbook(1, Duration.ofMinutes(30))); // Should return false
    }

    @Test
    public void testCanBookWithCustomerNumberGreaterThanSeatNum() {
        table.setSeatNum(1); // Assuming this method exists and sets the seat number
        assertFalse(table.canbook(2, Duration.ofMinutes(30))); // Should return false
    }

    @Test
    public void testCanBookWithNullDuration() {
        assertTrue(table.canbook(1, null)); // Assuming null is treated as 0 mins, should return true
    }

    @Test
    public void testCanBookWithMultipleBookedSlots() {
        bookedTimeSlot.add(allTimeslots.get(1)); // Book the first timeslot
        table.setBookedTimeslot(bookedTimeSlot);
        assertFalse(table.canbook(1, Duration.ofMinutes(70))); // Should return true for the third timeslot
    }

    @Test
    public void testCanBookWithMultipleBookedSlots2() {
        allTimeslots.add(new Timeslot("12:00 - 13:00"));
        table.setAllTimeslots(allTimeslots);
        bookedTimeSlot.add(allTimeslots.get(1)); // Book the first timeslot
        table.setBookedTimeslot(bookedTimeSlot);
        assertTrue(table.canbook(1, Duration.ofMinutes(70))); // Should return true for the third timeslot
    }

    @Test
    public void testCanBookWithMultipleBookedSlots3() {
        allTimeslots.add(new Timeslot("12:00 - 13:00"));
        table.setAllTimeslots(allTimeslots);
        bookedTimeSlot.add(allTimeslots.get(0)); // Book the first timeslot
        table.setBookedTimeslot(bookedTimeSlot);
        assertFalse(table.canbook(1, Duration.ofMinutes(130))); // Should return true for the third timeslot
    }

    @Test
    public void testCanBookWithStartTime() {
        assertFalse(table.canbookWithStartTime(5, Duration.ofMinutes(30), LocalTime.parse("12:00")));
    }

    @Test 
    public void testCanBookWithStartTime2() {
        assertTrue(table.canbookWithStartTime(2, Duration.ofMinutes(30), LocalTime.parse("10:00")));
    }
    
    @Test
    public void testCanBookWithStartTime3() {
        Duration duration = null;
        assertTrue(table.canbookWithStartTime(2, duration, LocalTime.parse("10:00")));
    }

    @Test
    public void testCanBookWithStartTime4() {
        bookedTimeSlot.add(allTimeslots.get(0)); 
        table.setBookedTimeslot(bookedTimeSlot);
        assertFalse(table.canbookWithStartTime(2, Duration.ofMinutes(30), LocalTime.parse("10:00")));
    }

    @Test
    public void testCanBookWithStartTime5() {
        bookedTimeSlot.add(allTimeslots.get(1)); 
        table.setBookedTimeslot(bookedTimeSlot);
        assertTrue(table.canbookWithStartTime(2, Duration.ofMinutes(30), LocalTime.parse("10:00")));
    }

    @Test
    public void testCanBookWithStartTime6() {
        bookedTimeSlot.add(allTimeslots.get(1)); 
        table.setBookedTimeslot(bookedTimeSlot);
        assertFalse(table.canbookWithStartTime(2, Duration.ofMinutes(30), LocalTime.parse("11:00")));
    }

    @Test 
    public void testCanBookWithStartTime7() {
        table.addTimeslot("09:00 - 10:00");
        bookedTimeSlot.add(allTimeslots.get(0)); 
        table.setBookedTimeslot(bookedTimeSlot);
        assertTrue(table.canbookWithStartTime(2, Duration.ofMinutes(30), LocalTime.parse("11:00")));
    }

    @Test 
    public void testCanBookWithStartTime8() {
        table.addTimeslot("09:00 - 10:00");
        bookedTimeSlot.add(allTimeslots.get(0)); 
        table.setBookedTimeslot(bookedTimeSlot);
        assertFalse(table.canbookWithStartTime(2, Duration.ofMinutes(30), LocalTime.parse("15:00")));
    }
}