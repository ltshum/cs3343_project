import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import java.time.format.DateTimeParseException;

public class BookingProfile {

    Server server = Server.getInstance();
    private final Restaurant restaurant;
    Scanner in = new Scanner(System.in);
    private final Account ac ;

    public BookingProfile(Restaurant restaurant) {
        this.restaurant = restaurant;
        this.ac=null;
    }
    public BookingProfile(Restaurant restaurant,Account ac) {
        this.restaurant = restaurant;
        this.ac =ac;
    }

	public void displayBookingProfile(Scanner in) {

        System.out.println("\n# Here is restaurant information #\n");
        System.out.println(server.getRestaurantBookingDetail(restaurant));
        System.out.println("\n1. Book today");
        System.out.println("\n2. Book another day");
        System.out.println("\n3. Back");
        System.out.print("\nWhat action do you want to do?: ");

        int op = in.nextInt();
        in.nextLine(); // Consume the newline character
        if(ac instanceof Customer){
            switch (op) {
                case 1 -> {
                    System.out.println("Book today");
                    System.out.print("How many seats would you like to book? ");
                    int customernumber = in.nextInt();
                    in.nextLine(); // Consume the newline character
                    System.out.println("This is the number of persons you want to book: " + customernumber);
                    
                    System.out.print("Which timeslot do you want to book (HH:mm): ");
                    String userInput = in.nextLine(); // Read timeslot input
    
                    System.out.print("What is your contact number ");
                    String contactnumber = in.nextLine(); // Read timeslot input
                    System.out.println("This is the contactnumber: " + contactnumber);
    
                    try {
                        LocalTime bookingtime = LocalTime.parse(userInput);
                        System.out.println("This is the bookingtime you want to book: " + bookingtime);
                        restaurant.booking(bookingtime, customernumber, contactnumber); 
                        ac.booking(bookingtime, customernumber, contactnumber);
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid time format. Please use HH:mm.");
                    }
                }
                case 2 -> {
                    System.out.println("Book another day");
                    System.out.print("Which date do you want to book (yyyy-MM-dd): ");
                    String dateInput = in.nextLine();
                    
                    try {
                        LocalDate bookingdate = LocalDate.parse(dateInput);
                        System.out.println("This is the bookingdate you want to book: " + bookingdate);
                        System.out.print("Which timeslot do you want to book (HH:mm): ");
                        String timeslot = in.nextLine(); // Read timeslot input
                        System.out.print("What is your contact number ");
                        String contactnumber = in.nextLine(); // Read timeslot input
                        
                        try {
                            LocalTime bookingtime = LocalTime.parse(timeslot);
                            System.out.print("How many seats would you like to book? ");
                            int customernumber = in.nextInt();
                            restaurant.bookinganotherdate(bookingtime, customernumber, bookingdate,contactnumber);
                            ac.bookinganotherdate(bookingtime, customernumber, bookingdate,contactnumber);
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid time format. Please use HH:mm.");
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                    }
                }
                case 3 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }else
        switch (op) {
            case 1 -> {
                System.out.println("Book today");
                System.out.print("How many seats would you like to book? ");
                int customernumber = in.nextInt();
                in.nextLine(); // Consume the newline character
                System.out.println("This is the number of persons you want to book: " + customernumber);
                
                System.out.print("Which timeslot do you want to book (HH:mm): ");
                String userInput = in.nextLine(); // Read timeslot input

                System.out.print("What is your contact number ");
                String contactnumber = in.nextLine(); // Read timeslot input
                System.out.println("This is the contactnumber: " + contactnumber);

                try {
                    LocalTime bookingtime = LocalTime.parse(userInput);
                    System.out.println("This is the bookingtime you want to book: " + bookingtime);
                    restaurant.booking(bookingtime, customernumber, contactnumber); 
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid time format. Please use HH:mm.");
                }
            }
            case 2 -> {
                System.out.println("Book another day");
                System.out.print("Which date do you want to book (yyyy-MM-dd): ");
                String dateInput = in.nextLine();
                
                try {
                    LocalDate bookingdate = LocalDate.parse(dateInput);
                    System.out.println("This is the bookingdate you want to book: " + bookingdate);

                    
                    System.out.print("Which timeslot do you want to book (HH:mm): ");
                    String timeslot = in.nextLine(); // Read timeslot input
                    System.out.print("What is your contact number ");
                    String contactnumber = in.nextLine(); // Read timeslot input
                    
                    try {
                        LocalTime bookingtime = LocalTime.parse(timeslot);
                        System.out.print("How many seats would you like to book? ");
                        int customernumber = in.nextInt();
                        restaurant.bookinganotherdate(bookingtime, customernumber, bookingdate,contactnumber);
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid time format. Please use HH:mm.");
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                }
            }
            case 3 -> {
                return;
            }
            default -> System.out.println("Invalid option. Please try again.");
        }
    }
}