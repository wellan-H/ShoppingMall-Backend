package com.wellan.shoppingmallbackend.service;

import com.wellan.shoppingmallbackend.dto.CreateOrderRequest;
import com.wellan.shoppingmallbackend.model.Order;

public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);
}
