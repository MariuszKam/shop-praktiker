package com.practice.shop.entities.admin;

import com.practice.shop.entities.user.Customer;

import java.util.List;

public class CustomerAnalysis extends Report<Customer> {


    public CustomerAnalysis(List<Customer> reportList) {
        super(reportList);
    }
}
