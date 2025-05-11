package com.praktiker.shop.services.cart;

import com.praktiker.shop.dto.cart.CartItemRequest;
import com.praktiker.shop.dto.cart.CartRequest;
import com.praktiker.shop.dto.cart.CartResponse;
import com.praktiker.shop.dto.order.OrderResponse;
import com.praktiker.shop.entities.cart.Cart;
import com.praktiker.shop.entities.cart.CartItem;
import com.praktiker.shop.entities.order.Order;
import com.praktiker.shop.entities.order.OrderItem;
import com.praktiker.shop.entities.order.OrderStatus;
import com.praktiker.shop.entities.product.Product;
import com.praktiker.shop.entities.product.stock.ProductStock;
import com.praktiker.shop.entities.user.User;
import com.praktiker.shop.exceptions.cart.*;
import com.praktiker.shop.exceptions.product.ProductNotFoundException;
import com.praktiker.shop.exceptions.product.stock.ProductStockNotFoundException;
import com.praktiker.shop.exceptions.product.stock.InsufficientStockException;
import com.praktiker.shop.mappers.cart.CartItemMapper;
import com.praktiker.shop.mappers.cart.CartMapper;
import com.praktiker.shop.mappers.order.OrderMapper;
import com.praktiker.shop.persistance.cart.CartItemRepository;
import com.praktiker.shop.persistance.cart.CartRepository;
import com.praktiker.shop.persistance.order.OrderRepository;
import com.praktiker.shop.persistance.product.ProductRepository;
import com.praktiker.shop.persistance.product.ProductStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    private final CartMapper cartMapper;

    private final CartItemMapper cartItemMapper;

    private final OrderMapper orderMapper;

    private final ProductStockRepository productStockRepository;


    public CartResponse getCart(User user) {
        Cart cart = getCartByUsername(user.getUsername());

        return cartMapper.toResponse(cart);
    }

    @Transactional
    public CartResponse addCartItem(CartItemRequest request, User user) {
        validateStock(request);

        Cart cart = getCartByUsername(user.getUsername());

        if (cart.containsProduct(request.productId()))
            throw new CartItemExistsInCart("Cart Item already exist in Cart");

        Product product = getProductById(request.productId());

        CartItem cartItem = cartItemMapper.toEntity(request, cart, product);

        cart.getItems().add(cartItem);

        return cartMapper.toResponse(cart);
    }

    @Transactional
    public CartResponse changeCartItemQuantity(Long id, CartItemRequest request, User user) {
        validateStock(request);

        Cart cart = getCartByUsername(user.getUsername());

        CartItem cartItem = getCartItemById(id);

        ensureCartItemIsInCart(cart, cartItem);

        cartItem.changeQuantity(request.quantity());

        return cartMapper.toResponse(cart);
    }

    @Transactional
    public void deleteCartItem(Long id, User user) {
        Cart cart = getCartByUsername(user.getUsername());

        CartItem cartItem = getCartItemById(id);

        ensureCartItemIsInCart(cart, cartItem);

        cart.removeItem(cartItem);
    }

    @Transactional
    public CartResponse replaceCartItems(CartRequest request, User user) {
        validateStock(request.items());

        Cart cart = getCartByUsername(user.getUsername());

        cart.clear();

        Set<Long> productIds = request.items()
                                      .stream()
                                      .map(CartItemRequest::productId)
                                      .collect(Collectors.toSet());

        List<Product> products = productRepository.findAllById(productIds);

        Map<Long, Product> productMap = products.stream()
                                                .collect(Collectors.toMap(Product::getId, Function.identity()));

        List<CartItem> cartItems = request.items()
                                          .stream()
                                          .map(item -> {
                                              Product product = productMap.get(item.productId());
                                              if (product == null) {
                                                  throw new ProductNotFoundException(
                                                          "Product [%d] not found".formatted(item.productId()));
                                              }
                                              return cartItemMapper.toEntity(item, cart, product);
                                          })
                                          .toList();

        cart.getItems().addAll(cartItems);

        return cartMapper.toResponse(cart);
    }

    @Transactional
    public OrderResponse checkout(User user) {
        Cart cart = getCartByUsername(user.getUsername());

        if (cart.getItems().isEmpty()) throw new EmptyCartCheckoutException("Cannot checkout empty cart");

        Order order = Order.builder()
                           .orderStatus(OrderStatus.CREATED)
                           .user(user)
                           .build();

        List<OrderItem> orderItems = cart.getItems()
                                         .stream()
                                         .map(cartItem ->
                                                      OrderItem.builder()
                                                               .quantity(cartItem.getQuantity())
                                                               .order(order)
                                                               .product(cartItem.getProduct())
                                                               .build())
                                         .toList();

        order.getOrderItems().addAll(orderItems);

        orderRepository.save(order);

        cart.clear();

        return orderMapper.toResponse(order);
    }


    private Cart getCartByUsername(String username) {
        return cartRepository.findByUserUsername(username)
                             .orElseThrow(() -> new CartNotFoundException(
                                     "Cart for user [%s] not found".formatted(username)
                             ));
    }

    private Product getProductById(Long id) {
        return productRepository.findById(id)
                                .orElseThrow(() -> new ProductNotFoundException(
                                        "Product [%d] not found".formatted(id)
                                ));
    }

    private CartItem getCartItemById(Long id) {
        return cartItemRepository.findById(id)
                                 .orElseThrow(() -> new CartItemNotFoundException(
                                         "Cart item [%d] not found".formatted(id)
                                 ));
    }

    private void ensureCartItemIsInCart(Cart cart, CartItem cartItem) {
        if (!cart.owns(cartItem))
            throw new CartItemNotExistsInCart("Cart Item [%d] is not in Cart".formatted(cartItem.getId()));
    }

    private void validateStock(CartItemRequest request) {
        ProductStock productStock = productStockRepository.findById(request.productId())
                                                          .orElseThrow(() -> new ProductStockNotFoundException(
                                                                  "Product Stock [%d] not found".formatted(
                                                                          request.productId())
                                                          ));

        if (productStock.getAmount().compareTo(request.quantity()) < 0) {
            throw new InsufficientStockException(
                    "Requested quantity [%s] exceeds stock [%s] for product [%s]".formatted(
                            request.quantity(), productStock.getAmount(), productStock.getProduct().getName()
                    ));
        }
    }

    private void validateStock(List<CartItemRequest> requests) {
        Set<Long> productIds = requests
                .stream()
                .map(CartItemRequest::productId)
                .collect(Collectors.toSet());

        List<ProductStock> productStock = productStockRepository.findAllById(productIds);

        Map<Long, ProductStock> productStockMap = productStock
                .stream()
                .collect(Collectors.toMap(ProductStock::getId, Function.identity()));

        for (CartItemRequest request : requests) {
            ProductStock stock = productStockMap.get(request.productId());

            if (stock == null) {
                throw new ProductStockNotFoundException("Product Stock [%d] not found".formatted(request.productId()));
            }

            if (stock.getAmount().compareTo(request.quantity()) < 0) {
                throw new InsufficientStockException(
                        "Requested quantity [%s] exceeds stock [%s] for product [%s]".formatted(
                                request.quantity(), stock.getAmount(), stock.getProduct().getName()
                        ));
            }
        }


    }
}
