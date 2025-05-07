package com.praktiker.shop.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class OrderCreateRequest {

    @NotNull(message = "List of items cannot be null")
    private List<OrderItemRequest> items;
}

