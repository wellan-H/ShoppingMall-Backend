package com.wellan.shoppingmallbackend.service;

import com.wellan.shoppingmallbackend.dto.CreateOrderRequest;

public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
