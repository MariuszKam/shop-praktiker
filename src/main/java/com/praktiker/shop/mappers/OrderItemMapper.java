package com.praktiker.shop.mappers;

import com.praktiker.shop.dto.order.OrderItemRequest;
import com.praktiker.shop.dto.order.OrderItemResponse;
import com.praktiker.shop.entities.order.Order;
import com.praktiker.shop.entities.order.OrderItem;
import com.praktiker.shop.entities.product.Product;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OrderItemMapper {

    /********************************* ORDER ITEMS ENTITY *********************************/

    public static OrderItem toEntity(OrderItemRequest request, Product product, Order order) {
        return OrderItem.builder()
                        .quantity(request.getQuantity())
                        .order(order)
                        .product(product).build();
    }

    public static List<OrderItem> toEntities(List<OrderItemRequest> requests, List<Product> products, Order order) {
        Map<Long, Product> productMap = products.stream()
                                                .collect(Collectors.toMap(Product::getId, Function.identity()));

        return requests.stream().map(request -> OrderItemMapper.toEntity(
                               request,
                               productMap.get(request.getProductId()),
                               order))
                       .toList();
    }

    /********************************* ORDER ITEMS RESPONS *********************************/

    public static OrderItemResponse toResponse(OrderItem orderItem) {
        return new OrderItemResponse(
                orderItem.getProduct().getId(),
                orderItem.getProduct().getName(),
                orderItem.getQuantity(),
                orderItem.getProduct().getPrice());
    }

    public static List<OrderItemResponse> toResponse(List<OrderItem> orderItems) {
        return orderItems.stream().map(OrderItemMapper::toResponse).toList();
    }

    /********************************* ORDERS ITEMS REQUEST *********************************/

    public static OrderItemRequest toRequest(OrderItem orderItem) {
        return new OrderItemRequest(orderItem.getProduct().getId(), orderItem.getQuantity());
    }

    public static List<OrderItemRequest> toRequests(List<OrderItem> orderItems) {
        return orderItems.stream().map(OrderItemMapper::toRequest).toList();
    }
}
