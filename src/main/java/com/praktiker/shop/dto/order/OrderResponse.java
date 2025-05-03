package com.praktiker.shop.dto.order;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(Long orderId,
                            String username,
                            BigDecimal totalAmount,
                            String status,
                            List<OrderItemResponse> items) {
}
