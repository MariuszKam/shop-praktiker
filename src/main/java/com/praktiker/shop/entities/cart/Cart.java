package com.praktiker.shop.entities.cart;

import com.praktiker.shop.entities.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "carts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Cart {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items;

    public BigDecimal getTotalPrice() {
        return items.stream()
                    .map(item -> item.getProduct().getPrice().multiply(item.getQuantity()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean containsProduct(Long id) {
        return items.stream().anyMatch(cartItem -> cartItem.getProduct().getId().equals(id));
    }

    public boolean owns(CartItem item) {
        return item != null && this.items.contains(item);
    }

    public void removeItem(CartItem item) {
        items.remove(item);
        item.detach();
    }

    public void clear() {
        items.forEach(CartItem::detach);
        items.clear();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o)
                .getHibernateLazyInitializer()
                .getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this)
                .getHibernateLazyInitializer()
                .getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Cart cart = (Cart) o;
        return getId() != null && Objects.equals(getId(), cart.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this)
                .getHibernateLazyInitializer()
                .getPersistentClass()
                .hashCode() : getClass().hashCode();
    }
}
