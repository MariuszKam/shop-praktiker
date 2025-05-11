package com.praktiker.shop.controllers.statistics;

import com.praktiker.shop.dto.order.OrderResponse;
import com.praktiker.shop.dto.product.ProductResponse;
import com.praktiker.shop.services.statistics.OrderStatisticsService;
import com.praktiker.shop.services.statistics.ProductStatisticService;
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

    private final OrderStatisticsService orderStatisticsService;

    private final ProductStatisticService productStatisticService;

    @GetMapping("/order/average")
    public ResponseEntity<BigDecimal> getAverageOrder() {
        return ResponseEntity.ok(orderStatisticsService.getAverageOrderSum());
    }

    @GetMapping("/order/most-expensive")
    public ResponseEntity<OrderResponse> getMostExpensive() {
        return ResponseEntity.ok(orderStatisticsService.getMostExpensive());
    }

    @GetMapping("/product/best-seller")
    public ResponseEntity<ProductResponse> getBestSeller() {
        return ResponseEntity.ok(productStatisticService.getBestseller());
    }
}
