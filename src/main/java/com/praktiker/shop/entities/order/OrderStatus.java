package com.praktiker.shop.entities.order;

public enum OrderStatus {

    CREATED,
    PAID,
    SHIPPED,
    DELIVERED,
    CANCELED;

    public boolean canTransitionTo(OrderStatus nextStatus) {
        return switch (this) {
            case CREATED -> nextStatus == PAID || nextStatus == CANCELED;
            case PAID -> nextStatus == SHIPPED || nextStatus == CANCELED;
            case SHIPPED -> nextStatus == DELIVERED;
            case DELIVERED, CANCELED -> false;
        };
    }
}
