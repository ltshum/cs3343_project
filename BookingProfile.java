import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
                    System.out.print("Which timeslot do you want to book (HH:mm)-(HH:mm): ");
                    String timeslot = in.nextLine(); // Read timeslot input
                    Timeslot bkTimeslot =new Timeslot(timeslot);
                    String[] parts = timeslot.split("-");
                        LocalTime startTime = LocalTime.parse(parts[0].trim());
                        LocalTime endTime = LocalTime.parse(parts[1].trim());
                        System.out.println("Start Time: " + startTime);
                        System.out.println("End Time: " + endTime);
                    System.out.print("What is your contact number ");
                    String contactnumber = in.nextLine(); // Read timeslot input
                    System.out.println("This is the contactnumber: " + contactnumber);
                    Timeslot ts= new Timeslot(timeslot);
                    try {
                        // LocalTime bookingtime = LocalTime.parse(timeslot);
                        // System.out.println("This is the bookingtime you want to book: " + bookingtime);
                        ArrayList<Table> tblist=restaurant.getAllTables();
                        System.out.println("This is inside the try part");
                        for (Table t: tblist){
                            System.out.println("This is inside the loop part");
                            if(t.canbook(customernumber, bkTimeslot)){
                                System.out.println("This is inside the can bk part");
                                restaurant.booking(startTime,endTime, customernumber, contactnumber); 
                                ac.booking(startTime,endTime, customernumber, contactnumber);
                                t.addBookingTimeslot(bkTimeslot);
                            }
                        }
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
                        System.out.print("Which timeslot do you want to book (HH:mm)-(HH:mm): ");
                        String timeslot = in.nextLine(); // Read timeslot input
                        Timeslot bkTimeslot =new Timeslot(timeslot);
                        String[] parts = timeslot.split("-");
                        LocalTime startTime = LocalTime.parse(parts[0].trim());
                        LocalTime endTime = LocalTime.parse(parts[1].trim());
                        System.out.println("Start Time: " + startTime);
                        System.out.println("End Time: " + endTime);
                        System.out.print("What is your contact number ");
                        String contactnumber = in.nextLine(); // Read timeslot input
                        Timeslot ts= new Timeslot(timeslot);
                        try {
                            LocalTime bookingtime = LocalTime.parse(timeslot);
                            System.out.print("How many seats would you like to book? ");
                            int customernumber = in.nextInt();
                            ArrayList<Table> table=restaurant.getAllTables();
                            for (Table t: table){
                            if(t.canbook(customernumber, bkTimeslot)){
                                restaurant.booking(startTime,endTime, customernumber, contactnumber); 
                                ac.booking(startTime,endTime, customernumber, contactnumber);
                                t.addBookingTimeslot(bkTimeslot);
                            }
                        }
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
}
//         }else
//         switch (op) {
//             case 1 -> {
//                 System.out.println("Book today");
//                 System.out.print("How many seats would you like to book? ");
//                 int customernumber = in.nextInt();
//                 in.nextLine(); // Consume the newline character
//                 System.out.println("This is the number of persons you want to book: " + customernumber);
                
//                 System.out.print("Which timeslot do you want to book (HH:mm): ");
//                 String timeslot = in.nextLine(); // Read timeslot input
//                 Timeslot bkTimeslot =new Timeslot(timeslot);
//                 String[] parts = timeslot.split("-");
//                     LocalTime startTime = LocalTime.parse(parts[0].trim());
//                     LocalTime endTime = LocalTime.parse(parts[1].trim());
//                     System.out.println("Start Time: " + startTime);
//                     System.out.println("End Time: " + endTime);
//                 System.out.print("What is your contact number ");
//                 String contactnumber = in.nextLine(); // Read timeslot input
//                 System.out.println("This is the contactnumber: " + contactnumber);

//                 try {
//                     LocalTime bookingtime = LocalTime.parse(userInput);
//                     System.out.println("This is the bookingtime you want to book: " + bookingtime);
//                     restaurant.booking(bookingtime, customernumber, contactnumber); 
//                 } catch (DateTimeParseException e) {
//                     System.out.println("Invalid time format. Please use HH:mm.");
//                 }
//             }
//             case 2 -> {
//                 System.out.println("Book another day");
//                 System.out.print("Which date do you want to book (yyyy-MM-dd): ");
//                 String dateInput = in.nextLine();
                
//                 try {
//                     LocalDate bookingdate = LocalDate.parse(dateInput);
//                     System.out.println("This is the bookingdate you want to book: " + bookingdate);

                    
//                     System.out.print("Which timeslot do you want to book (HH:mm)-(HH:Mmm): ");
//                     String timeslot = in.nextLine(); // Read timeslot input
//                     System.out.print("What is your contact number ");
//                     String contactnumber = in.nextLine(); // Read timeslot input
//                     Timeslot ts= new Timeslot(timeslot);
//                     try {
//                         LocalTime bookingtime = LocalTime.parse(timeslot);
//                         System.out.print("How many seats would you like to book? ");
//                         int customernumber = in.nextInt();
//                         ArrayList<Table> tb= restaurant.getAllTables();
//                         for(Table t:tb){}
//                         restaurant.bookinganotherdate(bookingtime, customernumber, bookingdate,contactnumber);
//                     } catch (DateTimeParseException e) {
//                         System.out.println("Invalid time format. Please use HH:mm.");
//                     }
//                 } catch (DateTimeParseException e) {
//                     System.out.println("Invalid date format. Please use yyyy-MM-dd.");
//                 }
//             }
//             case 3 -> {
//                 return;
//             }
//             default -> System.out.println("Invalid option. Please try again.");
//         }
//     }
// }