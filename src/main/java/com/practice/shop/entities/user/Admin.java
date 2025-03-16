package com.practice.shop.entities.user;

import com.practice.shop.entities.admin.Report;

import java.util.List;

public class Admin extends User {

    private List<Report> reports;

    public Admin(int id, String name, String email) {
        super(id, name, email);
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }
}
