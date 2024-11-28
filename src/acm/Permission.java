package acm;

import java.util.Set;

public class Permission {

    private Role role;
    private Resource resource;
    private Set<Privilege> privileges;

    public Permission(Role role, Resource resource, Set<Privilege> privileges) {
        this.role = role;
        this.resource = resource;
        this.privileges = privileges;
    }

    public Role getRole() {
        return role;
    }

    public Resource getResource() {
        return resource;
    }

    public Set<Privilege> getPrivileges() {
        return privileges;
    }

    // public boolean canPerform(Privilege privilege) {
    //     return privileges.contains(privilege);
    // }

}
