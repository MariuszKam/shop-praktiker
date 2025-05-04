package com.praktiker.shop.controllers;

import com.praktiker.shop.dto.order.OrderResponse;
import com.praktiker.shop.dto.product.ProductResponse;
import com.praktiker.shop.services.statistics.OrderStatistics;
import com.praktiker.shop.services.statistics.ProductStatistic;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticController {

    private final OrderStatistics orderStatistics;

    private final ProductStatistic productStatistic;

    @GetMapping("/order/average")
    public ResponseEntity<BigDecimal> getAverageOrder() {
        return ResponseEntity.ok(orderStatistics.getAverageOrderSum());
    }

    @GetMapping("/order/most-expensive")
    public ResponseEntity<OrderResponse> getMostExpensive() {
        return ResponseEntity.ok(orderStatistics.getMostExpensive());
    }

    @GetMapping("/product/best-seller")
    public ResponseEntity<ProductResponse> getBestSeller() {
        return ResponseEntity.ok(productStatistic.getBestseller());
    }
}
