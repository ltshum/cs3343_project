
// Other imports as needed
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Restaurant extends Account {

    private String restaurantName;
    private String type;
    private String district;
    private String address;
    private String restaurantContact;
    private float rate;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Duration sessionDuration;
    private ArrayList<Table> allTables = new ArrayList<>();
    private ArrayList<Comment> allComments = new ArrayList<>();
    private ArrayList<Booking> allBookings = new ArrayList<>();

    //Test
    Comment cm1 = new Comment("User1", "Great", 3);
    Comment cm2 = new Comment("User2", "Good", 4);

    //private ArrayList<Comment> allComments = new ArrayList<>();
    public Restaurant(String userName, String password, String name, String type, String district, String address, String contact, LocalTime openTime, LocalTime closeTime, Duration sessionDuration, int tableNum) {
        super(Arrays.asList(Role.RESTAURANT), userName, password, getRestaurantPermissions());
        this.rate = 0;
        this.restaurantName = name;
        this.type = type;
        this.district = district;
        this.address = address;
        this.restaurantContact = contact;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.sessionDuration = sessionDuration;
        this.allTables = new ArrayList<>();
        this.allBookings = new ArrayList<>();
        initializeTables(tableNum);
        generateTimeslots();
        this.allComments.add(cm1);
        this.allComments.add(cm2);
        //this.allTables = new ArrayList<>();
    }

    private void initializeTables(int tableCount) {
        for (int i = 1; i <= tableCount; i++) {
            allTables.add(new Table(i));
        }
    }

    public String getTimeslots() {
        LocalTime currentTime = openTime;
        String result = "";
        while (currentTime.plus(sessionDuration).isBefore(closeTime)
                || currentTime.plus(sessionDuration).equals(closeTime)) {
            String session = currentTime.toString() + " - " + currentTime.plus(sessionDuration).toString();
            currentTime = currentTime.plus(sessionDuration);
            result = result + session + " \n";
        }
        return result;
    }

    public void setTimeslots(LocalTime openTime, LocalTime closeTime, Duration sessionDuration) {
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.sessionDuration = sessionDuration;
        LocalTime currentTime = openTime;
        while (currentTime.plus(sessionDuration).isBefore(closeTime)
                || currentTime.plus(sessionDuration).equals(closeTime)) {
            String session = currentTime.toString() + " - " + currentTime.plus(sessionDuration).toString();
            for (Table table : allTables) {
                table.addTimeslot(new Timeslot(session));
            }
            currentTime = currentTime.plus(sessionDuration);
        }
    }

    private void generateTimeslots() {
        LocalTime currentTime = openTime;
        while (currentTime.plus(sessionDuration).isBefore(closeTime)
                || currentTime.plus(sessionDuration).equals(closeTime)) {
            String session = currentTime.toString() + " - " + currentTime.plus(sessionDuration).toString();
            for (Table table : allTables) {
                table.addTimeslot(new Timeslot(session));
            }
            currentTime = currentTime.plus(sessionDuration);
        }
    }

    // Permissions for the restaurant
    private static List<Permission> getRestaurantPermissions() {
        return Arrays.asList(
                new Permission(Role.RESTAURANT, Resource.PROFILE, Set.of(Privilege.READ, Privilege.UPDATE)),
                new Permission(Role.RESTAURANT, Resource.VIEW_BOOKING, Set.of(Privilege.CREATE, Privilege.READ, Privilege.UPDATE, Privilege.DELETE)),
                new Permission(Role.RESTAURANT, Resource.TABLE_MANAGEMENT, Set.of(Privilege.CREATE, Privilege.READ, Privilege.UPDATE, Privilege.DELETE)),
                new Permission(Role.RESTAURANT, Resource.WEEKLY_REPORT, Set.of(Privilege.CREATE, Privilege.READ, Privilege.UPDATE, Privilege.DELETE))
        );
    }

    public String getComment() {
        String result = "";
        for (Comment cm : allComments) {
            result = result + cm.getCustomer_name() + ": " + cm.getContent() + " " + cm.getRate() + "\n";
        }
        return result;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    public String getRestaurantContact() {
        return restaurantContact;
    }

    public float getRate() {
        return rate;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public long getSessionDuration() {
        return sessionDuration.toMinutes();
    }

    public ArrayList<Table> getAllTables() {
        return allTables;
    }

    public String getDistrict() {
        return district;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRestaurantContact(String restaurantContact) {
        this.restaurantContact = restaurantContact;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

    public void setSessionDuration(Duration sessionDuration) {
        this.sessionDuration = sessionDuration;
    }

    public void setAllTables(ArrayList<Table> allTables) {
        this.allTables = allTables;
    }

    public void edit(Scanner in) {
        while (true) {
            System.out.println("# If you want to back to last page please input X #");
            System.out.println("# Comment is not allowed to change #\n");
            System.out.println("# Change open/close/session time will regenerate timeslots #\n");
            System.out.println("1. Restaurant Name\n");
            System.out.println("2. Type\n");
            System.out.println("3. District\n");
            System.out.println("4. Address\n");
            System.out.println("5. Phone\n");
            System.out.println("6. Open Time\n");
            System.out.println("7. Close Time\n");
            System.out.println("8. Session Duration\n");
            System.out.println("9. Table Amount\n");

            System.out.print("Please input what information you want to change in list: ");
            String input = in.next();

            if (input.charAt(0) == 'X') {
                return;
            } else {
                try {
                    switch (Integer.parseInt(input)) {
                        case 1 -> {
                            System.out.print("Please input new Restaurant Name: ");
                            setRestaurantName(in.next());
                        }
                        case 2 -> {
                            System.out.print("Please input new Type: ");
                            setType(in.next());
                        }
                        case 3 -> {
                            System.out.print("Please input new Distrct: ");
                            setDistrict(in.next());
                        }
                        case 4 -> {
                            System.out.print("Please input new Address: ");
                            setAddress(in.next());
                        }
                        case 5 -> {
                            System.out.print("Please input new Phone: ");
                            setRestaurantContact(in.next());
                        }
                        case 6 -> {
                            System.out.print("Please input new Open Time: ");
                            setOpenTime(LocalTime.parse(in.next()));
                            generateTimeslots();
                        }
                        case 7 -> {
                            System.out.print("Please input new Close Time: ");
                            setCloseTime(LocalTime.parse(in.next()));
                            generateTimeslots();
                        }
                        case 8 -> {
                            System.out.print("Please input new Session Duration: ");
                            setSessionDuration(Duration.ofMinutes(in.nextInt()));
                            generateTimeslots();
                        }
                        case 9 -> {
                            System.out.print("Please input new Table Amount: ");
                            allTables.clear();
                            initializeTables(in.nextInt());
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

    public void addBooking(Booking bk) {
        this.allBookings.add(bk);
    }

    public void bookinganotherdate(LocalTime starTime, LocalTime endtime, int customernumber, LocalDate bookingdate, String contactnumber, Restaurant restaurant, Customer customer) {
        Booking bk = new Booking(starTime, endtime, customernumber, bookingdate, contactnumber, restaurant, customer);
        this.allBookings.add(bk);
        bk.bookingSuccess();
    }

    public ArrayList<Booking> getAllBookings() {
        return allBookings;
    }

    public void addComment(String customerName, String content, int rate) {
        Comment comment = new Comment(customerName, content, rate);
        allComments.add(comment);
        System.out.println("\nComment added!");
    }

}
