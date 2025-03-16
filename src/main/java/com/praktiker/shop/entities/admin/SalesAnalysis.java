package com.praktiker.shop.entities.admin;

import com.praktiker.shop.entities.order.Order;

import java.util.List;

public class SalesAnalysis extends Report<Order> {


    public SalesAnalysis(List<Order> reportList) {
        super(reportList);
    }
}
