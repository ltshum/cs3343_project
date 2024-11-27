package testSystem;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import system.Comment;
import system.RestaurantLogData;

public class RestaurantLogDataTest {

    Comment comment1 = new Comment("John Doe", "Good food", 4.5f, LocalDate.now());
    Comment comment2 = new Comment("Jane Doe", "Bad service", 2.5f, LocalDate.parse("2024-11-27"));
    Comment comment3 = new Comment("Joe Doe", "normal food", 3.5f, LocalDate.parse("2024-11-25"));
    ArrayList<Comment> lastWeekComments;
    int lastWeekTotalPpl;
    float lastWeekRate;
    int lastWeekRank;
    ArrayList<Comment> thisWeekComments;
    int thisWeekTotalPpl;
    float thisWeekRate;
    int thisWeekRank;
    RestaurantLogData restaurantLogData1;
    RestaurantLogData restaurantLogData2;

    @Before
    public void setUp() {
        lastWeekComments = new ArrayList<Comment>(List.of(comment1, comment2));
        lastWeekTotalPpl = 6;
        lastWeekRate = 3.5f;
        lastWeekRank = 6;
        thisWeekComments = new ArrayList<Comment>(List.of(comment2, comment3));
        thisWeekTotalPpl = 3;
        thisWeekRate = 3;
        thisWeekRank = 9;
        restaurantLogData1 = new RestaurantLogData(lastWeekComments, lastWeekTotalPpl, lastWeekRate, lastWeekRank, thisWeekComments, thisWeekTotalPpl, thisWeekRate, thisWeekRank);
    }

    @Test
    public void testGetLastWeekComments() {
        assertEquals(lastWeekComments, restaurantLogData1.getLastWeekComments());
    }

    @Test
    public void testGetLastWeekTotalPpl() {
        assertEquals(lastWeekTotalPpl, restaurantLogData1.getLastWeekTotalPpl());
    }

    @Test
    public void testGetLastWeekRate() {
        assertEquals(lastWeekRate, restaurantLogData1.getLastWeekRate(), 0.01);
    }

    @Test
    public void testGetLastWeekRank() {
        assertEquals(lastWeekRank, restaurantLogData1.getLastWeekRank());
    }

    @Test
    public void testGetThisWeekComments() {
        assertEquals(thisWeekComments, restaurantLogData1.getThisWeekComments());
    }

    @Test
    public void testGetThisWeekTotalPpl() {
        assertEquals(thisWeekTotalPpl, restaurantLogData1.getThisWeekTotalPpl());
    }

    @Test
    public void testGetThisWeekRate() {
        assertEquals(thisWeekRate, restaurantLogData1.getThisWeekRate(), 0.01);
    }

    @Test
    public void testGetThisWeekRank() {
        assertEquals(thisWeekRank, restaurantLogData1.getThisWeekRank());
    }

    @Test
    public void testSetLastWeekRank() {
        restaurantLogData1.setLastWeekRank(5);
        assertEquals(5, restaurantLogData1.getLastWeekRank());
    }

    @Test
    public void testSetThisWeekRank() {
        restaurantLogData1.setThisWeekRank(8);
        assertEquals(8, restaurantLogData1.getThisWeekRank());
    }
    
}
