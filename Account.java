import java.util.List;
import java.util.Scanner;

public abstract class Account {

    private static int idCounter = 1;
    private int id;
    private List<Role> roles;
    private String userName;
    private String password;
    private List<Permission> permissions;

    public Account(List<Role> r, String n, String p, List<Permission> permissions) {
        this.id = idCounter++;
        this.roles = r;
        this.userName = n;
        this.password = p;
        this.permissions = permissions;
    }

    public boolean hasPermission(Resource resource, Privilege privilege) {
        for (Permission permission : permissions) {
            if (roles.contains(permission.getRole())
                    && permission.getResource() == resource
                    && permission.canPerform(privilege)) {
                return true;
            }
        }
        return false;
    }

    public static int getIdCounter() {
        return idCounter;
    }

    public int getId() {
        return id;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public static void setIdCounter(int idCounter) {
        Account.idCounter = idCounter;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public abstract void edit(Scanner in);

}
