package system;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import acm.Role;

public class Customer extends Account {

    public Customer(String userName, String password, String name, String contact) {
        super(Arrays.asList(Role.CUSTOMER), userName, password, name, contact);
    }

    @Override
    public String getProfileDetail() {
        return "Name: " + this.getName()
                + "\nPhone: " + this.getContact();
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
                                setName(in.nextLine());
                                System.out.println("\nName has been changed to " + getName() + "\n");
                                isValidOption = true;
                            }
                            case 2 -> {
                                System.out.print("Please input new Phone: ");
                                setContact(in.nextLine());
                                System.out.println("\nPhone has been changed to " + getContact() + "\n");
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
        Collections.sort(getAllBookings(), Comparator.comparing(Booking::getBookingTimeslot));
        String bookingString = "";
        int index = 0;
        for (Booking booking : getAllBookings()) {
            if (booking.getBookingDate().equals(date)) {
                index++;
                bookingString += index + ". " + booking.getCustomerBookingData();
            }
        }
        System.out.println(bookingString);
        return index;
    }

    public Booking getBookingToBeCommentInCustomer(int inputNumber, LocalDate date) {

        ArrayList<Booking> requiredbookings = new ArrayList<>();
        for (Booking booking : getAllBookings()) {
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

}
