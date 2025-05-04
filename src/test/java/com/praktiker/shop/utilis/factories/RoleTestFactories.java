package com.praktiker.shop.utilis.factories;

import com.praktiker.shop.entities.user.Role;

import java.util.Set;

public class RoleTestFactories {

    public static Role creatRole(String name) {
        Role role = new Role();
        role.setName(name);
        return role;
    }

    public static Set<Role> roles() {
        return Set.of(creatRole("ROLE_ADMIN"),
                      creatRole("ROLE_USER"));
    }
}
