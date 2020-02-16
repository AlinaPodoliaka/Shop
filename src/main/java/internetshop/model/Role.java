package internetshop.model;

import internetshop.dao.IdGenerator;

import java.util.Objects;

public class Role {
    private Long id;
    private RoleName roleName;

    private Role(RoleName roleName) {
        this.roleName = roleName;
        this.setId(IdGenerator.getRoleId());
    }

    public Role(String roleName) {
        this.roleName = RoleName.valueOf(roleName);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    public static Role of(String roleName) {
        return new Role(RoleName.valueOf(roleName));
    }

    public enum RoleName {
        USER, ADMIN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role = (Role) o;
        return Objects.equals(id, role.id)
                && roleName == role.roleName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleName);
    }
}
