package com.praktiker.shop.entities.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ROLE_ADMIN, ROLE_CUSTOMER, ROLE_SUPPLIER;

    @Override
    public String getAuthority() {
        return name();
    }
}
