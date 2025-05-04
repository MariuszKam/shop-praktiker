package com.praktiker.shop.mappers;

import com.praktiker.shop.dto.order.OrderItemRequest;
import com.praktiker.shop.dto.product.ProductResponse;
import com.praktiker.shop.entities.product.Product;

import java.util.List;

public class ProductMapper {

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(),
                                   product.getProductType().name());
    }

    public static Long toProductId(OrderItemRequest request) {
        return request.getProductId();
    }

    public static List<Long> toProductsId(List<OrderItemRequest> requests) {
        return requests.stream().map(OrderItemRequest::getProductId).toList();
    }
}
