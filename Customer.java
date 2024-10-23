
// Other imports as needed
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Customer extends Account {

    private String customerName;
    private String customerContact;
    private ArrayList<Integer> allRestaurant = new ArrayList<>();
    private ArrayList<String> allWrittenComment = new ArrayList<>();

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

    public void setAllRestaurant(ArrayList<Integer> allRestaurant) {
        this.allRestaurant = allRestaurant;
    }

    public void setAllWrittenComment(ArrayList<String> allWrittenComment) {
        this.allWrittenComment = allWrittenComment;
    }

}
