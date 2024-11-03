// import java.time.LocalDate;
// import java.time.format.DateTimeFormatter;
// import java.util.Scanner;

// public class MonthDayExample {
//     public static void main(String[] args) {
//         Scanner scanner = new Scanner(System.in);
//         System.out.print("Enter month (MM): ");
//         int month = scanner.nextInt();
//         System.out.print("Enter day (DD): ");
//         int day = scanner.nextInt();

//         // Use the current year as the default
//         int year = LocalDate.now().getYear();
//         LocalDate date = LocalDate.of(year, month, day);

//         // Format and display the date
//         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//         System.out.println("Date: " + date.format(formatter));
//     }
// }
