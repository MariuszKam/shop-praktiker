package com.praktiker.shop.dto.order;

import com.praktiker.shop.entities.order.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderStatusUpdateRequest {

    @NotNull(message = "Status cannot be null")
    private OrderStatus status;
}
