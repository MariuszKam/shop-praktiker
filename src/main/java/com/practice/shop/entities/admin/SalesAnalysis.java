package com.practice.shop.entities.admin;

import com.practice.shop.entities.order.Order;

import java.util.List;

public class SalesAnalysis extends Report<Order> {


    public SalesAnalysis(List<Order> reportList) {
        super(reportList);
    }
}
