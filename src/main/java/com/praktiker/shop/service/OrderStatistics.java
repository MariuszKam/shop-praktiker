package com.praktiker.shop.service;

import com.praktiker.shop.entities.admin.Report;
import com.praktiker.shop.entities.admin.SalesAnalysis;
import com.praktiker.shop.entities.order.Order;
import com.praktiker.shop.entities.product.Product;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrderStatistics {

    private final Report<Order> report;

    public OrderStatistics(List<Order> orders) {
        this.report = new SalesAnalysis(orders);
    }

    public double getAverageOrderSum() {
        return report.getReportList()
                .stream().mapToDouble(order -> order.getProducts().stream()
                        .mapToDouble(Product::getPrice).sum())
                .average().orElse(0.0);
    }

    public Order getMostExpensive() {
        return report.getReportList().stream()
                .max(Comparator.comparingDouble(order ->
                        order.getProducts().stream().mapToDouble(Product::getPrice).sum()))
                .orElse(null);
    }

    public Product getBestseller() {
        return Objects.requireNonNull(report.getReportList().stream()
                .flatMap(order -> order.getProducts().stream())
                .collect(Collectors.groupingBy(product -> product, Collectors.counting()))
                .entrySet().stream().max(Comparator.comparingLong(Map.Entry::getValue))
                .orElse(null)).getKey();
    }
}
