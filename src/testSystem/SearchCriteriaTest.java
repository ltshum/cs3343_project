package testSystem;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import View.Home;
import View.RestaurantList;
import View.SearchRestaurant;
import system.Booking;
import system.Customer;
import system.Restaurant;
import system.Server;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import system.Account;
import java.util.Arrays;
import system.SearchCriteria;


public class SearchCriteriaTest {
    String n = "null";
    SearchCriteria sc = new SearchCriteria(n,n,n,n,n,n,n);
    // public boolean isEqualArray(float[] a, float[] b){
    //     if (a.length != b.length){
    //         return false;
    //     }
    //     for (int i = 0; i < a.length; i++){
    //         if (a[i]!= b[i]){
    //             return false;
    //         }
    //     }
    //     return true;
    // }
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
    public void test_isNotAllNull(){
        assertEquals(false, (new SearchCriteria("a", "a", "0-5", "a", "0", "00:00", "0")).isAllNull());
    }

    @Test
    public void test_wordScoreEmpty(){
        String name = "";
        String keyword = "";
        float[] result = sc.getWordScore(name,keyword);
        float[] expected = {1,1};
        assertEquals(true, Arrays.equals(result, expected));
    }
    @Test
    public void test_wordScoreDigit1(){
        String name = "a";
        String keyword = "b";
        float[] result = sc.getWordScore(name,keyword);
        float[] expected = {1,1};
        assertEquals(false, Arrays.equals(result, expected));
    }
    @Test
    public void test_wordScoreDigit2(){
        String name = "a";
        String keyword = "a";
        float[] result = sc.getWordScore(name,keyword);
        float[] expected = {1,1};
        assertEquals(true, Arrays.equals(result, expected));
    }
    @Test
    public void test_wordScoreDigit3(){
        String name = "a";
        String keyword = "A";
        float[] result = sc.getWordScore(name,keyword);
        float[] expected = {1,1};
        assertEquals(true, Arrays.equals(result, expected));
    }
    @Test
    public void test_wordScoreKeyword1(){
        String name = "aa";
        String keyword = "aa";
        float[] result = sc.getWordScore(name,keyword);
        float[] expected = {3,3};
        assertEquals(true, Arrays.equals(result, expected));
    }
    @Test
    public void test_wordScoreKeyword2(){
        String name = "aaa";
        String keyword = "aaa";
        float[] result = sc.getWordScore(name,keyword);
        float[] expected = {5,5};
        assertEquals(true, Arrays.equals(result, expected));
    }
    @Test
    public void test_wordScoreKeyword3(){
        String name = "ana";
        String keyword = "aa";
        float[] result = sc.getWordScore(name,keyword);
        System.out.print(result[1]);
        float[] expected = {2,3};
        assertEquals(true, Arrays.equals(result, expected));
    }
    @Test
    public void test_wordScoreKeyword4(){
        String name = "anana";
        String keyword = "na";
        float[] result = sc.getWordScore(name,keyword);
        float[] expected = {3,3};
        assertEquals(true, Arrays.equals(result, expected));
    }
    @Test
    public void test_wordScoreKeyword5(){
        String name = "1234567";
        String keyword = "67890AB";
        float[] result = sc.getWordScore(name,keyword);
        float[] expected = {2,keyword.length() *2-1};
        assertEquals(true, Arrays.equals(result, expected));
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
    public void allNullScore(){
        
    }
}
