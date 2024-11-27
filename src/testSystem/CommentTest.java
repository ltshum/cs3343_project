package testSystem;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import system.Comment;

public class CommentTest {
    Comment comment1;
    Comment comment2;

    @Before
    public void setUp() {
        comment1 = new Comment("John Doe", "Good food", 4.5f, LocalDate.now());
        comment2 = new Comment("Jane Doe", "Bad service", 2.5f, LocalDate.parse("2024-11-27"));
    }               

    @Test
    public void testGetCommentCustomerName() {
        assertEquals("John Doe", comment1.getCommentCustomerName());
        assertEquals("Jane Doe", comment2.getCommentCustomerName());
    }

    @Test
    public void testGetCommentContent() {
        assertEquals("Good food", comment1.getCommentContent());
        assertEquals("Bad service", comment2.getCommentContent());
    }

    @Test
    public void testGetCommentDate() {
        assertEquals(LocalDate.now(), comment1.getCommentDate());
        assertEquals(LocalDate.parse("2024-11-27"), comment2.getCommentDate());
    }

    @Test
    public void testGetCommentRate() {
        assertEquals(4.5f, comment1.getCommentRate(), 0.01);
        assertEquals(2.5f, comment2.getCommentRate(), 0.01);
    }
    
}
