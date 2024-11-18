
// Other imports as needed
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Customer extends Account {

    private String customerName;
    private String customerContact;
    private ArrayList<Integer> allRestaurant = new ArrayList<>();
    private ArrayList<String> allWrittenComment = new ArrayList<>();
    private ArrayList<Booking> allBookings = new ArrayList<>();

    public Customer(String userName, String password, String name, String contact) {
        super(Arrays.asList(Role.CUSTOMER), userName, password, getCustomerPermissions());
        this.customerName = name;
        this.customerContact = contact;
    }

    // Permissions for the customer
    private static List<Permission> getCustomerPermissions() {
        return Arrays.asList(
                new Permission(Role.CUSTOMER, Resource.PROFILE, Set.of(Privilege.READ, Privilege.UPDATE)),
                new Permission(Role.CUSTOMER, Resource.VIEW_BOOKING, Set.of(Privilege.READ)),
                new Permission(Role.CUSTOMER, Resource.SEARCH_RESTAURANT, Set.of(Privilege.READ))
        );
    }

    public String getCustomerName() {
        return customerName;
    }

    public ArrayList<Booking> getAllBookings() {
        return allBookings;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public ArrayList<Integer> getAllRestaurant() {
        return allRestaurant;
    }

    public ArrayList<String> getAllWrittenComment() {
        return allWrittenComment;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public void addBooking(Booking bk) {
        this.allBookings.add(bk);
    }

    public ArrayList<Booking> getallBooking() {
        return allBookings;
    }

    public void bookinganotherdate(LocalTime startTime, LocalTime endTime, int customernumber, LocalDate bookingdate, String contactnumber, Restaurant restaurant, Customer customer) {
        Booking bk = new Booking(startTime, endTime, customernumber, bookingdate, contactnumber, restaurant, customer);
        this.allBookings.add(bk);
        bk.bookingSuccess();
    }

    public void setAllRestaurant(ArrayList<Integer> allRestaurant) {
        this.allRestaurant = allRestaurant;
    }

    public void setAllWrittenComment(ArrayList<String> allWrittenComment) {
        this.allWrittenComment = allWrittenComment;
    }

    public void edit(Scanner in) {
        while (true) {
            System.out.println("# If you want to back to last page please input X #");
            System.out.println("1. Name\n");
            System.out.println("2. Phone\n");
            System.out.print("Please input what information you want to change in list: ");
            String input = in.next();

            if (input.charAt(0) == 'X') {
                return;
            } else {
                try {
                    switch (Integer.parseInt(input)) {
                        case 1 -> {
                            System.out.print("Please input new Name: ");
                            setCustomerName(in.next());
                        }
                        case 2 -> {
                            System.out.print("Please input new Phone: ");
                            setCustomerContact(in.next());
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input!");
                } catch (Exception e) {
                    System.out.printf("Error: %s\n", e.getMessage());
                }
            }
        }
    }

}
