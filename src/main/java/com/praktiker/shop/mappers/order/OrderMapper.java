package com.praktiker.shop.mappers.order;

import com.praktiker.shop.dto.order.OrderResponse;
import com.praktiker.shop.entities.order.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring", uses = OrderItemMapper.class)
public interface OrderMapper {

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "totalAmount", source = "order", qualifiedByName = "calculateTotal")
    OrderResponse toResponse(Order order);

    List<OrderResponse> toResponse(List<Order> orders);

    @Named("calculateTotal")
    static BigDecimal getTotal(Order order) {
        return order.getTotalPrice();
    }
}