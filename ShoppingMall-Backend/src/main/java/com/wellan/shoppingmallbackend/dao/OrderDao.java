package com.wellan.shoppingmallbackend.dao;

import com.wellan.shoppingmallbackend.model.Order;
import com.wellan.shoppingmallbackend.model.OrderItem;

import java.util.List;

public interface OrderDao {
    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);

    Order getOrderById(Integer orderId);

    List<OrderItem> getOrderItemByOrderId(Integer orderId);
}
