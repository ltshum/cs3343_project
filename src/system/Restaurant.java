package system;

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
    private final ArrayList<Comment> allComments = new ArrayList<>();
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
                table.addTimeslot(session);
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

    public void updateSeatNo(int tableID, int seatNum) {
        Table table = allTables.get(tableID - 1);
        table.setSeatNum(seatNum);
    }

    public boolean tableValidation(int tableID) {
        for (Table table: allTables) {
            if (table.getTableID() == tableID){
                return true;
            }
        }   
        return false;
    }

    public int availableTableID(int ppl, String timeslotSession, LocalDate date) {
        for (Table table : allTables) {
            if (table.isTimeslotAvailable(timeslotSession, date)) {
                if (table.getSeatNum() >= ppl) {
                    table.setTimeslotUnavailable(timeslotSession, date);
                    return table.getTableID();
                }
            }
        }
        return 0;
    }

    public void getAllTableInfo() {
        StringBuilder tableID = new StringBuilder("                ");
        StringBuilder seat = new StringBuilder("                ");
        StringBuilder status = new StringBuilder("                ");
        for (Table table : allTables) {
            tableID.append(String.format("| Table ID: %-13d ", table.getTableID()));
            seat.append(String.format("| Seat: %-17d ", table.getSeatNum()));
            status.append(String.format("| Status: %-15s ", table.getStatus()));
        }
        System.out.println(tableID.toString());
        System.out.println(seat.toString());
        System.out.println(status.toString());
    }

    public void updateTableInfo( Scanner in, int tableID) {
        Table table = allTables.get(tableID - 1);

        boolean isValidSeatNo = false;
        while (!isValidSeatNo) {
            System.out.print("\nNew Seat: ");
            try {
                int newSeat = Integer.parseInt(in.nextLine());
                table.setSeatNum(newSeat);
                isValidSeatNo = true;
                System.out.println("Table with table ID " + tableID + " has been updated to " + newSeat + " seats.");
            } catch (NumberFormatException e) {
                System.out.print("\nInvalid input! Please input again.");
            }
        }
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
            System.out.println("# Change table amount will clear current tables' inforamtion #\n");
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
                String input = in.nextLine();
                if (input.charAt(0) == 'X') {
                    break outerloop;
                } else {
                    try {
                        switch (Integer.parseInt(input)) {
                            case 1 -> {
                                System.out.print("Please input new Restaurant Name: ");
                                setRestaurantName(in.nextLine());
                                System.out.println("\nRestaurant Name has been changed to " + getRestaurantName() + "\n");
                                isValidOption = true;
                            }
                            case 2 -> {
                                System.out.print("Please input new Type: ");
                                setType(in.nextLine());
                                System.out.println("\nType has been changed to " + getType() + "\n");
                                isValidOption = true;
                            }
                            case 3 -> {
                                System.out.print("Please input new Distrct: ");
                                setDistrict(in.nextLine());
                                System.out.println("\nDistrict has been changed to " + getDistrict() + "\n");
                                isValidOption = true;
                            }
                            case 4 -> {
                                System.out.print("Please input new Address: ");
                                setAddress(in.nextLine());
                                System.out.println("\nAddress has been changed to " + getAddress() + "\n");
                                isValidOption = true;
                            }
                            case 5 -> {
                                System.out.print("Please input new Phone: ");
                                setRestaurantContact(in.nextLine());
                                System.out.println("\nPhone has been changed to " + getRestaurantContact() + "\n");
                                isValidOption = true;
                            }
                            case 6 -> {
                                boolean isValidOpenTime = false;
                                while (!isValidOpenTime) {
                                    System.out.print("Please input new Open Time (HH:mm): ");
                                    try {
                                        setOpenTime(LocalTime.parse(in.nextLine()));
                                        generateTimeslots();
                                        System.out.println("\nOpen Time has been changed to " + getOpenTime() + "\n");
                                        isValidOpenTime = true;
                                        isValidOption = true;
                                    } catch (DateTimeParseException e) {
                                        System.out.println("\nInvalid input! Please input again.");
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
                                        System.out.println("\nClose Time has been changed to " + getCloseTime() + "\n");
                                        isValidCloseTime = true;
                                        isValidOption = true;
                                    } catch (DateTimeParseException e) {
                                        System.out.println("\nInvalid input! Please input again.");
                                    }
                                }
                            }
                            case 8 -> {
                                boolean isValidSessionDuration = false;
                                while (!isValidSessionDuration) {
                                    System.out.print("Please input new Session Duration in mintues: ");
                                    try {
                                        int newSessionDuration = Integer.parseInt(in.nextLine());
                                        setSessionDuration(Duration.ofMinutes(newSessionDuration));
                                        generateTimeslots();
                                        System.out.println("\nSession Duration has been changed to " + getSessionDuration() + "\n");
                                        isValidSessionDuration = true;
                                        isValidOption = true;
                                    } catch (NumberFormatException e) {
                                        System.out.println("\nInvalid input! Please input again.");
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
                                        System.out.println("\nTable Amount has been changed to " + getAllTables().size() + "\n");
                                        isValidTableAmount = true;
                                        isValidOption = true;
                                    } catch (NumberFormatException e) {
                                        System.out.println("\nInvalid input! Please input again.");
                                    }
                                }
                            }
                            default -> {
                                System.out.println("\nInvalid input! Please input again.");
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("\nInvalid input! Please input again.");
                    }
                }
            }
            
        }
    }

    public void addBooking(Booking bk) {
        this.allBookings.add(bk);
    }
}
