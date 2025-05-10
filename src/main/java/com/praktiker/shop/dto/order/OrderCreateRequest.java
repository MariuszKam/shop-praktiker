package com.praktiker.shop.dto.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class OrderCreateRequest {

    @NotNull(message = "List of items cannot be null")
    @Size(min = 1, message = "Order must contain at least one item")
    private List<OrderItemRequest> items;
}

