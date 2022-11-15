package com.wellan.shoppingmallbackend.service;

import com.wellan.shoppingmallbackend.dto.CreateOrderRequest;
import com.wellan.shoppingmallbackend.dto.OrderQueryParams;
import com.wellan.shoppingmallbackend.model.Order;

import java.util.List;

public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Integer countOrder(OrderQueryParams orderQueryParams);
}
