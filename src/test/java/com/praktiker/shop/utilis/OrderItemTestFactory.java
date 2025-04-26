package com.praktiker.shop.utilis;

import com.praktiker.shop.entities.order.OrderItem;
import com.praktiker.shop.entities.product.Product;

import java.math.BigDecimal;
import java.util.List;

public class OrderItemTestFactory {

    public static OrderItem createOrderItem(Long id, BigDecimal quantity, Product product) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(id);
        orderItem.setQuantity(quantity);
        orderItem.setProduct(product);
        return orderItem;
    }

    public static List<OrderItem> createOrderItemList(Long id, BigDecimal quantity, Product product) {
        OrderItem orderItem = createOrderItem(id, quantity, product);
        return List.of(orderItem);
    }

    public static List<OrderItem> createOrderItemList(Long id, BigDecimal quantity) {
        Product product = ProductTestFactory.createProduct();
        return createOrderItemList(id, quantity, product);
    }
}
