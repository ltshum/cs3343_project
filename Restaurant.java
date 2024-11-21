
// Other imports as needed
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
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
    Comment cm1 = new Comment("User1", "Great", 3, LocalDate.now());
    Comment cm2 = new Comment("User2", "Good", 4, LocalDate.now());

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

    public String getCommentString() {
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

    public ArrayList<Booking> getAllBookings() {
        return allBookings;
    }

    public ArrayList<Comment> getAllComments() {
        return allComments;
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

    private void initializeTables(int tableCount) {
        for (int i = 1; i <= tableCount; i++) {
            allTables.add(new Table(i));
        }
    }
    
    @Override
    public void edit(Scanner in) {
        outerloop : while (true) {
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

            boolean isValidOption = false;
            while (!isValidOption) {
                System.out.print("Please input what information you want to change in list: ");
                String input = in.next();
                in.nextLine();
                if (input.charAt(0) == 'X') {
                    break outerloop;
                } else {
                    try {
                        switch (Integer.parseInt(input)) {
                            case 1 -> {
                                System.out.print("Please input new Restaurant Name: ");
                                setRestaurantName(in.next());
                                System.out.println("Restaurant Name has been changed to " + getRestaurantName());
                                isValidOption = true;
                            }
                            case 2 -> {
                                System.out.print("Please input new Type: ");
                                setType(in.next());
                                System.out.println("Type has been changed to " + getType());
                                isValidOption = true;
                            }
                            case 3 -> {
                                System.out.print("Please input new Distrct: ");
                                setDistrict(in.next());
                                System.out.println("District has been changed to " + getDistrict());
                                isValidOption = true;
                            }
                            case 4 -> {
                                System.out.print("Please input new Address: ");
                                setAddress(in.next());
                                System.out.println("Address has been changed to " + getAddress());
                                isValidOption = true;
                            }
                            case 5 -> {
                                System.out.print("Please input new Phone: ");
                                setRestaurantContact(in.next());
                                System.out.println("Phone has been changed to " + getRestaurantContact());
                                isValidOption = true;
                            }
                            case 6 -> {
                                boolean isValidOpenTime = false;
                                while (!isValidOpenTime) {
                                    System.out.print("Please input new Open Time (HH:mm): ");
                                    try {
                                        setOpenTime(LocalTime.parse(in.nextLine()));
                                        generateTimeslots();
                                        System.out.println("Open Time has been changed to " + getOpenTime());
                                        isValidOpenTime = true;
                                        isValidOption = true;
                                    } catch (DateTimeParseException e) {
                                        System.out.println("Invalid input! Please input again.");
                                    }
                                }
                            }
                            case 7 -> {
                                boolean isValidCloseTime = false;
                                while (!isValidCloseTime) {
                                    System.out.print("Please input new Close Time(HH:mm): ");
                                    try {
                                        setCloseTime(LocalTime.parse(in.nextLine()));
                                        generateTimeslots();
                                        System.out.println("Close Time has been changed to " + getCloseTime());
                                        isValidCloseTime = true;
                                        isValidOption = true;
                                    } catch (DateTimeParseException e) {
                                        System.out.println("Invalid input! Please input again.");
                                    }
                                }
                            }
                            case 8 -> {
                                boolean isValidSessionDuration = false;
                                while (!isValidSessionDuration) {
                                    System.out.print("Please input new Session Duration in mintues: ");
                                    try {
                                        int newSessionDuration = Integer.parseInt(in.next());
                                        setSessionDuration(Duration.ofMinutes(newSessionDuration));
                                        generateTimeslots();
                                        System.out.println("Session Duration has been changed to " + getSessionDuration());
                                        isValidSessionDuration = true;
                                        isValidOption = true;
                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid input! Please input again.");
                                    }
                                }
                            }
                            case 9 -> {
                                boolean isValidTableAmount = false;
                                while (!isValidTableAmount) {
                                    System.out.print("Please input new Table Amount: ");
                                    allTables.clear();
                                    try {
                                        initializeTables(Integer.parseInt(in.nextLine()));
                                        System.out.println("Table Amount has been changed to " + getAllTables().size());
                                        isValidTableAmount = true;
                                        isValidOption = true;
                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid input! Please input again.");
                                    }
                                }
                            }
                            default -> {
                                System.out.println("Invalid input! Please input again.");
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input! Please input again.");
                    }
                }
            }
            
        }
    }

    public void addBooking(Booking bk) {
        this.allBookings.add(bk);
    }

    public void addComment(String customerName, String content, int rate, LocalDate date) {
        Comment comment = new Comment(customerName, content, rate, date);
        allComments.add(comment);
        float recal_rate = 0;
        for (Comment cm : allComments) {
            recal_rate += cm.getRate();
        }
        this.rate = recal_rate / allComments.size();
        System.out.println("\nComment added!");
    }

    public ArrayList<Booking> getPeriodBooking(LocalDate startDate, LocalDate endDate) {
        ArrayList<Booking> result = new ArrayList<>();
        for (Booking bk : allBookings) {
            if ((bk.getDate().isEqual(startDate) || bk.getDate().isAfter(startDate)) && (bk.getDate().isEqual(endDate) || bk.getDate().isBefore(endDate))) {
                result.add(bk);
            }
        }
        return result;
    }

    public ArrayList<Comment> getPeriodComment(LocalDate startDate, LocalDate endDate) {
        ArrayList<Comment> result = new ArrayList<>();
        for (Comment cm : allComments) {
            if ((cm.getDate().isEqual(startDate) || cm.getDate().isAfter(startDate)) && (cm.getDate().isEqual(endDate) || cm.getDate().isBefore(endDate))) {
                result.add(cm);
            }
        }
        return result;
    }
}
