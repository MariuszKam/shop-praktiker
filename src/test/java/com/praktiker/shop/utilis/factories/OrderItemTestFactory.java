package com.praktiker.shop.utilis.factories;

import com.praktiker.shop.dto.order.OrderItemRequest;
import com.praktiker.shop.dto.order.OrderItemResponse;
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

    /********************************* ORDER ITEMS RESPONSES *********************************/

    public static OrderItemResponse createOrderItemResponse(Long productId,
                                                            String productName,
                                                            BigDecimal quantity,
                                                            BigDecimal pricePerUnit) {

        return new OrderItemResponse(productId, productName, quantity, pricePerUnit);
    }

    public static List<OrderItemResponse> createOrderItemResponseList() {
        return List.of(createOrderItemResponse(1L, "Book", BigDecimal.ONE, BigDecimal.valueOf(20.99)),
                       createOrderItemResponse(2L, "Disk", BigDecimal.ONE, BigDecimal.valueOf(200.00)));
    }

    /********************************* ORDER ITEMS REQUESTS *********************************/

    public static OrderItemRequest createOrderItemRequest(Long productId, BigDecimal quantity) {
        return new OrderItemRequest(productId, quantity);
    }

    public static List<OrderItemRequest> createOrderItemRequestList() {
        return List.of(createOrderItemRequest(1L, BigDecimal.valueOf(10.00)),
                       createOrderItemRequest(2L, BigDecimal.valueOf(15.00)),
                       createOrderItemRequest(3L, BigDecimal.valueOf(20.00)));
    }
}
