package com.practice.shop.entities.user;

import java.util.List;

public class Admin extends User {

    private List<Report> reports;

    public Admin(int id, String name, String email) {
        super(id, name, email);
    }
}
