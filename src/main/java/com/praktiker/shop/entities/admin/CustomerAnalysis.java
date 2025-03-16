package com.praktiker.shop.entities.admin;

import com.praktiker.shop.entities.user.Customer;

import java.util.List;

public class CustomerAnalysis extends Report<Customer> {


    public CustomerAnalysis(List<Customer> reportList) {
        super(reportList);
    }
}
