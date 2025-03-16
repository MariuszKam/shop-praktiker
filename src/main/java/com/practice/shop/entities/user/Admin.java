package com.practice.shop.entities.user;

import com.practice.shop.entities.admin.Report;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Admin extends User {

    private List<Report> reports;

    public Admin(int id, String name, String email) {
        super(id, name, email);
    }

}
