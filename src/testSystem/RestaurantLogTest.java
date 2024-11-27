package testSystem;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import system.Comment;
import system.RestaurantLog;

public class RestaurantLogTest {

    Comment comment1;
    Comment comment2;
    Comment comment3;
    RestaurantLog restaurantLog1;
    RestaurantLog restaurantLog2;
    
    @Before
    public void setUp() {
        comment1 = new Comment("John Doe", "Good food", 4.5f, LocalDate.now());
        comment2 = new Comment("Jane Doe", "Bad service", 2.5f, LocalDate.parse("2024-11-27"));
        comment3 = new Comment("Joe Doe", "normal food", 3.5f, LocalDate.parse("2024-11-25"));
        restaurantLog1 = new RestaurantLog(6, 3.5f, 6, new ArrayList<Comment>(List.of(comment1, comment2)));
        restaurantLog2 = new RestaurantLog(9, 3f, 3, new ArrayList<Comment>(List.of(comment2, comment3)));
    }

    @Test
    public void testGetRank() {
        assertEquals(1, restaurantLog1.getRank());
        assertEquals(2, restaurantLog2.getRank());
    }

    @Test
    public void testGetRate() {
        assertEquals(4.5f, restaurantLog1.getRate(), 0.01);
        assertEquals(2.5f, restaurantLog2.getRate(), 0.01);
    }

    @Test
    public void testGetTotalPpl() {
        assertEquals(6, restaurantLog1.getTotalPpl());
        assertEquals(3, restaurantLog2.getTotalPpl());
    }

    @Test
    public void testGetComments() {
        assertEquals(new ArrayList<Comment>(List.of(comment1, comment2)), restaurantLog1.getComments());
        assertEquals(new ArrayList<Comment>(List.of(comment2, comment3)), restaurantLog2.getComments());
    }
    
}
