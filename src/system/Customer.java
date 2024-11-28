package system;

import acm.Role;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Customer extends Account {

    private String customerName;
    private String customerContact;
    private ArrayList<String> allWrittenComment = new ArrayList<>();
    private final ArrayList<Booking> allBookings = new ArrayList<>();

    public Customer(String userName, String password, String name, String contact) {
        super(Arrays.asList(Role.CUSTOMER), userName, password);
        this.customerName = name;
        this.customerContact = contact;
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

    public ArrayList<String> getAllWrittenComment() {
        return allWrittenComment;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public void setAllWrittenComment(ArrayList<String> allWrittenComment) {
        this.allWrittenComment = allWrittenComment;
    }

    @Override
    public String getProfileDetail() {
        return "Name: " + this.getCustomerName()
                + "\nPhone: " + this.getCustomerContact();
    }

    @Override
    public void edit(Scanner in) {
        while (true) {
            System.out.println("# If you want to back to last page please input X #");
            System.out.println("1. Name\n");
            System.out.println("2. Phone\n");
            boolean isValidOption = false;
            while (!isValidOption) {
                System.out.print("Please input what information you want to change in list: ");
                String input = in.nextLine();
                if (input.equals("X")) {
                    return;
                } else {
                    try {
                        switch (Integer.parseInt(input)) {
                            case 1 -> {
                                System.out.print("Please input new Name: ");
                                setCustomerName(in.nextLine());
                                System.out.println("\nName has been changed to " + getCustomerName() + "\n");
                                isValidOption = true;
                            }
                            case 2 -> {
                                System.out.print("Please input new Phone: ");
                                setCustomerContact(in.nextLine());
                                System.out.println("\nPhone has been changed to " + getCustomerContact() + "\n");
                                isValidOption = true;
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
    public int getBookingRecord(LocalDate date) {
        Collections.sort(allBookings, Comparator.comparing(Booking::getBookingTimeslot));
        String bookingString = "";
        int index = 0;
        for (Booking booking : allBookings) {
            if (booking.getBookingDate().equals(date)) {
                index++;
                bookingString += index + ". " + booking.getCustomerBookingData();
            }
        }
        System.out.println(bookingString);
        return index;
    }

    public void addBooking(Booking bk) {
        this.allBookings.add(bk);
    }

    public Booking getBookingToBeCommentInCustomer(int inputNumber, LocalDate date) {
        
        ArrayList<Booking> requiredbookings = new ArrayList<>();
        for (Booking booking : allBookings) {
            if (booking.getBookingDate().equals(date)) {
                requiredbookings.add(booking);
            }
        }
        return requiredbookings.get(inputNumber - 1);
    }

    public boolean checkHasAttendInCustomer(int inputNumber, LocalDate date) {
        if (!getBookingToBeCommentInCustomer(inputNumber, date).hasArrived()) {
            System.out.println("\nYou can only make comment after you have attended the appointment.");
            return false;
        }
        return true;
    }

    public void makeCommentInCustomer(int inputNumber, LocalDate date, int rate, String commentString) {
        try {
            Restaurant restaurant = getBookingToBeCommentInCustomer(inputNumber, date).getBookingRestaurant();
            ArrayList<Comment> allComments = restaurant.getAllCommentsList();
            Comment comment = new Comment(getCustomerName(), commentString, rate, date);
            allComments.add(comment);
            float recal_rate = 0;
            for (Comment cm : allComments) {
                recal_rate += cm.getCommentRate();
            }
            restaurant.setRate(recal_rate / allComments.size());
            System.out.println("\nComment added!");
        } catch (Exception e) {
            System.out.println("Restaurant not found.");
        }
    }

    public Restaurant getRestaurantToBeComment(int inputNumber, LocalDate date) {
        return getBookingToBeCommentInCustomer(inputNumber, date).getBookingRestaurant();
    }
}
