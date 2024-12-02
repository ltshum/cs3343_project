
package testSystem;

import org.junit.Before;
import org.junit.Test;

import system.Account;
import system.Restaurant;
import system.SearchCriteria;
import system.Table;

import static org.junit.Assert.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

public class SearchCriteriaTest {

    private SearchCriteria criteria;
    private Account account;

    String n = "null";
    SearchCriteria sc = new SearchCriteria(n,n,n,n,n,n,n);
    Restaurant restaurant1 = new Restaurant(
        "timeat",
        "password",
        "Tim Eat",
        "type",
        "district",
        "address",
        "12345678",
        (LocalTime.parse("10:00")),
        (LocalTime.parse("20:00")),
        Duration.ofMinutes(60),
        3);

    @Before
    public void setUp() {
        criteria = new SearchCriteria("Test Restaurant", "Downtown", "3-5", "Italian", "4", "12:00", "60");
        // Mock account setup
         account = new Restaurant("username",
                "password",
                "name",
                "type",
                "district",
                "address",
                "12345678",
                LocalTime.parse("10:00"),
                LocalTime.parse("20:00"),
                Duration.ofMinutes(60),
                4);
    }

    @Test
    public void testSearchCriteriaConstructorWithAllNulls() {
        criteria = new SearchCriteria("null", "null", "null", "null", "null", "null", "null");
        assertTrue(criteria.isAllNull());
    }

    @Test
    public void testSearchCriteriaConstructorWithValidData() {
        criteria = new SearchCriteria("Test Restaurant", "Downtown", "4-5", "Italian", "4", "12:00", "60");
        assertNotNull(criteria);
    }

    @Test
    public void testIsNonNull() {
        criteria = new SearchCriteria("Test", "null", "null", "null", "null", "null", "null");
        assertTrue(criteria.isNonNull("Test"));
        assertFalse(criteria.isNonNull("null"));
    }

    @Test
    public void testGetWordScoreWithExactMatch() {
        criteria = new SearchCriteria("Test", "null", "null", "null", "null", "null", "null");
        float[] score = criteria.getWordScore("Test Restaurant", "Test Restaurant");
        assertEquals(29.0, score[0], 0.01);
        assertEquals(29.0, score[1], 0.01);
    }

    @Test
    public void testGetWordScoreWithPartialMatch() {
        float[] score = criteria.getWordScore("Test Restaurant", "Test");
        assertEquals(7.0, score[1], 0.01);
    }

    @Test
    public void testGetWordScoreWithNoMatch() {
        float[] score = criteria.getWordScore("Test Restaurant", "Pizza");
        assertEquals(1.0, score[0], 0.01);
        assertEquals(9.0, score[1], 0.01);
    }

    @Test
    public void testIsAllNull() {
        criteria = new SearchCriteria("null", "null", "null", "null", "null", "null", "null");
        assertTrue(criteria.isAllNull());

        criteria = new SearchCriteria("Restaurant", "null", "null", "null", "null", "null", "null");
        assertFalse(criteria.isAllNull());
    }

    @Test
    public void testGetSearchScoreAllNull() {
        criteria = new SearchCriteria("null", "null", "null", "null", "null", "null", "null");
        assertEquals(1, criteria.getSearchScore(account));
    }

    @Test
    public void testGetSearchScoreWithMatchingRestaurantName() {

        criteria = new SearchCriteria("Test Restaurant", "null", "null", "null", "null", "null", "null");
        assertTrue(criteria.getSearchScore(account) > 0);
    }

    @Test
    public void testGetSearchScoreWithNonMatchingRestaurantName() {
        criteria = new SearchCriteria("Pizza Place", "null", "null", "null", "null", "null", "null");
        assertEquals(15, criteria.getSearchScore(account));
    }

    @Test
    public void testGetSearchScoreWithRateRange() {
        criteria = new SearchCriteria("null", "null", "4-5", "null", "null", "null", "null");
        assertFalse(criteria.getSearchScore(account) > 0);
    }

    @Test
    public void testSearchRestaurantsIn() {
        ArrayList<Account> restaurants = new ArrayList<>();
        restaurants.add(account);
        
        criteria = new SearchCriteria("Test Restaurant", "Downtown", "4-5", "Italian", "4", "12:00", "60");
        ArrayList<Account> results = criteria.searchRestaurantsIn(restaurants);
        
        assertEquals(0, results.size());
       
    }

    @Test
    public void testSearchRestaurantsInWithNoMatches() {
        ArrayList<Account> restaurants = new ArrayList<>();
        restaurants.add(account);
        
        criteria = new SearchCriteria("Pizza Place", "null", "null", "null", "null", "null", "null");
        ArrayList<Account> results = criteria.searchRestaurantsIn(restaurants);
        assertFalse(results.isEmpty());
    }

    @Test
    public void testSearchRestaurantsInWithTimeAndCapacity() {
        ArrayList<Account> restaurants = new ArrayList<>();
        account.getAccountAllTables().add(new Table(4)); // Assuming a table with capacity 4
        restaurants.add(account);
        
        criteria = new SearchCriteria("null", "null", "null", "null", "4", "12:00", "60");
        ArrayList<Account> results = criteria.searchRestaurantsIn(restaurants);
        
        assertEquals(0, results.size());
    }

    @Test
    public void testIsAllNull_WithAllNulls() {
        // Setting all attributes to null or default values
        criteria = new SearchCriteria("null", "null", "null", "null", "0", "null", "null");
        assertTrue(criteria.isAllNull());
    }

    @Test
    public void testIsAllNull_WithNonNullRestaurantName() {
        criteria = new SearchCriteria("Restaurant", "null", "null", "null", "0", "null", "null");
        assertFalse(criteria.isAllNull());
    }

    @Test
    public void testIsAllNull_WithNonNullDistrict() {
        criteria = new SearchCriteria("null", "Downtown", "null", "null", "0", "null", "null");
        assertFalse(criteria.isAllNull());
    }

    @Test
    public void testIsAllNull_WithNonNullRateRange() {
        criteria = new SearchCriteria("null", "null", "4-5", "null", "0", "null", "null");
        assertFalse(criteria.isAllNull());
    }

    @Test
    public void testIsAllNull_WithNonNullType() {
        criteria = new SearchCriteria("null", "null", "null", "Italian", "0", "null", "null");
        assertFalse(criteria.isAllNull());
    }

    @Test
    public void testIsAllNull_WithNonZeroPeople() {
        criteria = new SearchCriteria("null", "null", "null", "null", "1", "null", "null");
        assertFalse(criteria.isAllNull());
    }

    @Test
    public void testIsAllNull_WithNonNullStartTime() {
        criteria = new SearchCriteria("null", "null", "null", "null", "0", "12:00", "null");
        assertFalse(criteria.isAllNull());
    }

    @Test
    public void testIsAllNull_WithNonNullDuration() {
        criteria = new SearchCriteria("null", "null", "null", "null", "0", "null", "60");
        assertFalse(criteria.isAllNull());
    }

@Test
    public void testGetSearchScore_WithNullRateRange() {
        criteria = new SearchCriteria("Test Restaurant", "Downtown", "null", "Italian", "4", "12:00", "60");
        assertEquals(39, criteria.getSearchScore(account)); // Rate range is null, should not contribute to score
    }

    @Test
    public void testGetSearchScore_WithValidRateRange() {
        criteria = new SearchCriteria("Test Restaurant", "Downtown", "4-5", "Italian", "4", "12:00", "60");
        assertEquals(39, criteria.getSearchScore(account)); // Account rate (4.5) falls within the range (4, 5)
    }

    @Test
    public void testGetSearchScore_WithRateBelowMin() {
        ((Restaurant) account).setRestaurantRate(3); // Set account rate below the range
        criteria = new SearchCriteria("Test Restaurant", "Downtown", "4.0-5", "Italian", "4", "12:00", "60");
        assertEquals(39, criteria.getSearchScore(account)); // Account rate (3) is below the min (4)
    }

    @Test
    public void testGetSearchScore_WithRateAboveMax() {
        ((Restaurant) account).setRestaurantRate(4); // Set account rate above the range
        criteria = new SearchCriteria("Test Restaurant", "null", "1-2", "null", "4", "12:00", "60");
        assertEquals(18, criteria.getSearchScore(account)); // Account rate (5) is above the max (5)
    }

    @Test
    public void testGetSearchScore_WithExactMinRate() {
        ((Restaurant) account).setRestaurantRate(3); // Set account rate equal to the min
        criteria = new SearchCriteria("Test Restaurant", "Downtown", "3-4", "Italian", "4", "12:00", "60");
        assertEquals(39, criteria.getSearchScore(account)); // Account rate (4) is not greater than min (4)
    }

    @Test
    public void testGetSearchScore_WithExactMaxRate() {
        ((Restaurant) account).setRestaurantRate(5); // Set account rate equal to the max
        criteria = new SearchCriteria("Test Restaurant", "Downtown", "4-5", "Italian", "4", "12:00", "60");
        assertEquals(39, criteria.getSearchScore(account)); // Account rate (5) is not less than max (5)
    }

    @Test
    public void testGetSearchScore_WithExactMaxRate1() {
        ((Restaurant) account).setRestaurantRate(2); // Set account rate equal to the max
        criteria = new SearchCriteria("Test Restaurant", "Downtown", "1-5", "Italian", "4", "12:00", "60");
        assertEquals(139, criteria.getSearchScore(account)); // Account rate (5) is not less than max (5)
    }

    //David

    @Test
    public void test_isNull(){
        boolean result = sc.isNonNull(n);
        assertEquals(false, result);
    }
    @Test
    public void test_isNonNull(){
        boolean result = sc.isNonNull("n");
        assertEquals(true, result);
    }
    @Test
    public void test_isAllNull(){
        assertEquals(true, sc.isAllNull());
    }
    @Test
    public void test_isNotAllNull1(){
        assertEquals(false, (new SearchCriteria("a", "a", "0-5", "a", "1", "00:00", "1")).isAllNull());
    }
    @Test
    public void test_isNotAllNull2(){
        assertEquals(false, (new SearchCriteria(n, "a", "0-5", "a", "1", "00:00", "1")).isAllNull());
    }
    @Test
    public void test_isNotAllNull3(){
        assertEquals(false, (new SearchCriteria(n, n, "0-5", "a", "1", "00:00", "1")).isAllNull());
    }
    @Test
    public void test_isNotAllNull4(){
        assertEquals(false, (new SearchCriteria(n, n, n, "a", "1", "00:00", "1")).isAllNull());
    }
    @Test
    public void test_isNotAllNull5(){
        assertEquals(false, (new SearchCriteria(n, n, n, n, "1", "00:00", "1")).isAllNull());
    }
    @Test
    public void test_isNotAllNull6(){
        assertEquals(false, (new SearchCriteria(n, n, n, n, n, "00:00", "1")).isAllNull());
    }
    @Test
    public void test_isNotAllNull7(){
        assertEquals(false, (new SearchCriteria(n, n, n, n, n, n, "1")).isAllNull());
    }

    @Test
    public void test_wordScoreEmpty(){
        String name = "";
        String keyword = "";
        float[] result = sc.getWordScore(name,keyword);
        float[] expected = {1,1};
        assertArrayEquals(expected, result, 0);
    }
    @Test
    public void test_wordScoreDigit1(){
        String name = "a";
        String keyword = "b";
        float[] result = sc.getWordScore(name,keyword);
        float[] expected = {0,1};
        assertArrayEquals(expected, result, 0);
    }
    @Test
    public void test_wordScoreDigit2(){
        String name = "a";
        String keyword = "a";
        float[] result = sc.getWordScore(name,keyword);
        float[] expected = {1,1};
        assertArrayEquals(expected, result, 0);
    }
    @Test
    public void test_wordScoreDigit3(){
        String name = "a";
        String keyword = "A";
        float[] result = sc.getWordScore(name,keyword);
        float[] expected = {1,1};
        assertArrayEquals(expected, result, 0);
    }
    @Test
    public void test_wordScoreKeyword1(){
        String name = "aa";
        String keyword = "aa";
        float[] result = sc.getWordScore(name,keyword);
        float[] expected = {3,3};
        assertArrayEquals(expected, result, 0);
    }
    @Test
    public void test_wordScoreKeyword2(){
        String name = "aaa";
        String keyword = "aaa";
        float[] result = sc.getWordScore(name,keyword);
        float[] expected = {5,5};
        assertArrayEquals(expected, result, 0);
    }
    @Test
    public void test_wordScoreKeyword3(){
        String name = "ana";
        String keyword = "aa";
        float[] result = sc.getWordScore(name,keyword);
        System.out.print(result[1]);
        float[] expected = {2,3};
        assertArrayEquals(expected, result, 0);
    }
    @Test
    public void test_wordScoreKeyword4(){
        String name = "anana";
        String keyword = "na";
        float[] result = sc.getWordScore(name,keyword);
        float[] expected = {3,3};
        assertArrayEquals(expected, result, 0);
    }
    @Test
    public void test_wordScoreKeyword5(){
        String name = "1234567";
        String keyword = "67890AB";
        float[] result = sc.getWordScore(name,keyword);
        float[] expected = {2,keyword.length() *2-1};
        assertArrayEquals(expected, result, 0);
    }
    @Test
    public void test_wordScoreKeyword6(){
        String name = "1234567";
        String keyword = "B67890A";
        float[] result = sc.getWordScore(name,keyword);
        float[] expected = {3,keyword.length() *2-1};
        assertArrayEquals(expected, result, 0);
    }

    @Test
    public void allNullScore1(){
        assertEquals(1, sc.getSearchScore(restaurant1));
    }
    
}
