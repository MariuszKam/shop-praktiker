package com.praktiker.shop.utilis.factories;

import com.praktiker.shop.entities.order.OrderItem;
import com.praktiker.shop.entities.product.Product;

import java.math.BigDecimal;
import java.util.List;

public class OrderItemTestFactory {

    /********************************* ORDER ITEMS *********************************/

    public static OrderItem createOrderItem(Long id, BigDecimal quantity, Product product) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(id);
        orderItem.setQuantity(quantity);
        orderItem.setProduct(product);
        return orderItem;
    }

    public static OrderItem createOrderItemForRepo(BigDecimal quantity, Product product) {
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(quantity);
        orderItem.setProduct(product);
        return orderItem;
    }

    public static List<OrderItem> createOrderItemList(Long id, BigDecimal quantity, Product product) {
        return List.of(createOrderItem(id, quantity, product));
    }

    public static List<OrderItem> createOrderItemList(Long id, BigDecimal quantity) {
        return createOrderItemList(id, quantity, ProductTestFactory.createProduct());
    }

    public static List<OrderItem> createOrderItemList(Long id) {
        return createOrderItemList(id, BigDecimal.TWO, ProductTestFactory.createProduct());
    }

    public static List<OrderItem> createOrderItemList() {
        return List.of(createOrderItem(1L, BigDecimal.ONE, ProductTestFactory.createProductList().getFirst()),
                       createOrderItem(2L, BigDecimal.TWO, ProductTestFactory.createProductList().get(1)));
    }

    public static List<OrderItem> createOrderItemsForRepo(List<Product> products) {
        return List.of(
                createOrderItemForRepo(BigDecimal.ONE, products.getFirst()),
                createOrderItemForRepo(BigDecimal.TWO, products.get(1)),
                createOrderItemForRepo(BigDecimal.ONE, products.get(2))
        );
    }

    public static List<List<OrderItem>> createOrderItemsListsForRepo(List<Product> products) {
        return List.of(createOrderItemsForRepo(products),
                       createOrderItemsForRepo(products),
                       createOrderItemsForRepo(products));
    }

}
