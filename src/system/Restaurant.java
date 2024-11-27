package system;

// Other imports as needed
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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

    public ArrayList<Comment> getAllCommentsList() {
        return allComments;
    }

    public String getAllComments() {
        String result = "";
        for (Comment cm : allComments) {
            result = result + cm.getCustomer_name() + ": " + cm.getContent() + " " + cm.getRate() + "\n";
        }
        return result;
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
    public String getProfileDetail(){
        return "Rate: " + this.getRate()
                        + "\nRestaurant Name: " + restaurantName
                        + "\nType: " + type
                        + "\nDistrict: " + district
                        + "\nAddress: " + address
                        + "\nPhone: " + restaurantContact
                        + "\nOpen Time: " + openTime
                        + "\nClose Time: " + closeTime
                        + "\nSession Duration: " + sessionDuration.toMinutes() + "mins"
                        + "\nTable Amount: " + allTables.size()
                        + "\n\nTimeslot: \n" + this.getTimeslots()
                        + "\nComment: \n" + this.getAllComments();
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
                                        LocalTime newOpenTime =LocalTime.parse(in.nextLine());
                                        if(newOpenTime.isAfter(closeTime)){
                                            System.out.println("Open Time must not  earlier than Open Time.Please input again");
                                        }else{
                                        setOpenTime(newOpenTime);
                                        generateTimeslots();
                                        System.out.println("\nOpen Time has been changed to " + getOpenTime() + "\n");
                                        isValidOpenTime = true;
                                        isValidOption = true;
                                        }
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
                                        LocalTime newClosingTime =LocalTime.parse(in.nextLine());
                                        System.out.println("THis is the newClosingTIme being inputted "+newClosingTime);  
                                            if(newClosingTime.isBefore(openTime)){
                                            System.out.println("Close Time must not be earlier than Open Time.Please input again");
                                        }else{
                                        setCloseTime(newClosingTime);
                                        generateTimeslots();
                                        System.out.println("\nClose Time has been changed to " + getCloseTime() + "\n");
                                        isValidCloseTime = true;
                                        isValidOption = true;
                                        }
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
                                        if(newSessionDuration<=0){
                                            System.out.println("New session duration cannot be smaller or equal to 0.Please input again");
                                        }else{
                                        setSessionDuration(Duration.ofMinutes(newSessionDuration));
                                        generateTimeslots();
                                        System.out.println("\nSession Duration has been changed to " + getSessionDuration() + "\n");
                                        isValidSessionDuration = true;
                                        isValidOption = true;
                                        }
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
                                        int TableAmountInput =Integer.parseInt(in.nextLine());
                                        if(TableAmountInput<=0){
                                            System.out.println("Table Amount cannot be smaller or equal to 0.Please input again");
                                        }else{
                                        initializeTables(TableAmountInput);
                                        System.out.println("\nTable Amount has been changed to " + getAllTables().size() + "\n");
                                        isValidTableAmount = true;
                                        isValidOption = true;
                                        }
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

    @Override
    public int getBookingRecord(LocalDate date){
        Collections.sort(allBookings, Comparator.comparing(Booking::getTimeslot));
        int totalBookings = 0;
        ArrayList<Booking> requiredBookings = new ArrayList<>();
        for (Booking booking : allBookings) {
            if (booking.getDate().equals(date)) {
                totalBookings++;
                requiredBookings.add(booking);
            }
        }
        List<List<Booking>> groupedBookings = new ArrayList<>();

        for (Booking booking : requiredBookings) {
            boolean added = false;
            for (List<Booking> group : groupedBookings) {
                if (!group.isEmpty() && group.get(0).getTimeslot().equals(booking.getTimeslot())) {
                    group.add(booking);
                    added = true;
                    break;
                }
            }
            if (!added) {
                List<Booking> newGroup = new ArrayList<>();
                newGroup.add(booking);
                groupedBookings.add(newGroup);
            }
        }
        for (List<Booking> group : groupedBookings) {
            System.out.println(group.get(0).getTimeslot());
            StringBuilder tableID = new StringBuilder("            ");
            StringBuilder booker = new StringBuilder("            ");
            StringBuilder ppl = new StringBuilder("            ");
            StringBuilder contact = new StringBuilder("            ");
            StringBuilder arrived = new StringBuilder("            ");
            for (Booking booking : group) {
                tableID.append(String.format("| Table ID: %-13d ", booking.getTableID()));
                booker.append(String.format("| Booker: %-13s ", booking.getCustomer().getCustomerName()));
                ppl.append(String.format("| Ppl No: %-13s ", booking.getPpl()));
                contact.append(String.format("| Contact: %-13s ", booking.getCustomerContact()));
                arrived.append(String.format("| Arrived?: %-13s ", booking.hasArrived()));
            }
            System.out.println(tableID.toString());
            System.out.println(booker.toString());
            System.out.println(ppl.toString());
            System.out.println(contact.toString());
            System.out.println(arrived.toString());
        }
        return totalBookings;
    }

    public void addBooking(Booking bk) {
        this.allBookings.add(bk);
    }
}