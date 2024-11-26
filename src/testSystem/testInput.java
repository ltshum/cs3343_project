package testSystem;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

// class for transforming array of Strings into input lines
// returns Scanner to test cases for reading the input lines
public class testInput {
    public static Scanner input(String[] in) {
		String input = "";
		for (String i : in) {
			input += i + System.lineSeparator();
		}
		System.setIn(new ByteArrayInputStream(input.getBytes()));
        return new Scanner(System.in);
	}
}
