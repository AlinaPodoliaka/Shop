package internetshop.service;

import internetshop.model.Role;
import internetshop.model.User;

import java.util.Set;

public interface RoleService {
    Role setRole(User user, Role.RoleName roleName);

    Role getRoleByName(Role.RoleName roleName);

    Set<Role> getAllRoles(User user);
}
