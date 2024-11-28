package acm;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PermissionRegistry {

    private static final Map<Role, List<Permission>> rolePermissions = new HashMap<>();

    static {
        // Register permissions for all roles initially
        registerPermissions(Role.RESTAURANT, Arrays.asList(
                new Permission(Role.RESTAURANT, Resource.PROFILE, Set.of(Privilege.READ, Privilege.UPDATE)),
                new Permission(Role.RESTAURANT, Resource.VIEW_BOOKING, Set.of(Privilege.CREATE, Privilege.READ, Privilege.UPDATE, Privilege.DELETE)),
                new Permission(Role.RESTAURANT, Resource.TABLE_MANAGEMENT, Set.of(Privilege.CREATE, Privilege.READ, Privilege.UPDATE, Privilege.DELETE)),
                new Permission(Role.RESTAURANT, Resource.WEEKLY_REPORT, Set.of(Privilege.CREATE, Privilege.READ, Privilege.UPDATE, Privilege.DELETE))
        ));

        registerPermissions(Role.CUSTOMER, Arrays.asList(
                new Permission(Role.CUSTOMER, Resource.PROFILE, Set.of(Privilege.READ, Privilege.UPDATE)),
                new Permission(Role.CUSTOMER, Resource.VIEW_BOOKING, Set.of(Privilege.READ)),
                new Permission(Role.CUSTOMER, Resource.SEARCH_RESTAURANT, Set.of(Privilege.READ))
        ));
    }

    public static void registerPermissions(Role role, List<Permission> permissions) {
        rolePermissions.put(role, permissions);
    }

    public static List<Permission> getPermissionsForRole(Role role) {
        return rolePermissions.getOrDefault(role, Collections.emptyList());
    }

    // public static void updatePermissionsForRole(Role role, List<Permission> newPermissions) {
    //     rolePermissions.put(role, newPermissions);
    // }
}
