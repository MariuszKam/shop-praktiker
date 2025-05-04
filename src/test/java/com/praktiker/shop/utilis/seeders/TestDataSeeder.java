package com.praktiker.shop.utilis.seeders;

import com.praktiker.shop.entities.order.Order;
import com.praktiker.shop.entities.order.OrderItem;
import com.praktiker.shop.entities.order.Payment;
import com.praktiker.shop.entities.product.Product;
import com.praktiker.shop.entities.user.Role;
import com.praktiker.shop.entities.user.User;
import com.praktiker.shop.exceptions.RoleNotFoundException;
import com.praktiker.shop.exceptions.UserNotFoundException;
import com.praktiker.shop.persistance.*;
import com.praktiker.shop.utilis.factories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestComponent;

import java.util.List;
import java.util.Set;

@TestComponent
@RequiredArgsConstructor
public class TestDataSeeder {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    public void initializeDatabase() {
        seedRoles();
        seedUsers();
        seedProducts();
        seedOrders();
    }

    private void seedRoles() {
        Set<Role> roles = RoleTestFactories.roles();
        roleRepository.saveAll(roles);
    }

    private void seedUsers() {
        List<User> users = UserTestFactory.createUsers();

        Role admin = roleRepository.findByName("ROLE_ADMIN")
                                   .orElseThrow(() -> new RoleNotFoundException("Role not found"));

        Role user = roleRepository.findByName("ROLE_USER")
                                  .orElseThrow(() -> new RoleNotFoundException("Role not found"));

        users.forEach(u -> u.getRoles().add(user));

        users.getFirst().getRoles().add(admin);
        userRepository.saveAll(users);
    }

    private void seedProducts() {
        List<Product> products = ProductTestFactory.createProductsForRepo();
        productRepository.saveAll(products);
    }

    private void seedOrders() {
        List<Product> products = productRepository.findAll();
        List<List<OrderItem>> orderItemLists = OrderItemTestFactory.createOrderItemsListsForRepo(products);

        User user = userRepository.findByUsername("Adam")
                                  .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<Payment> payments = PaymentTestFactory.createPaymentsForRepo();

        List<Order> orders = OrderTestFactory.createOrdersForRepo(orderItemLists, user, payments);

        orderRepository.saveAll(orders);
    }
}
