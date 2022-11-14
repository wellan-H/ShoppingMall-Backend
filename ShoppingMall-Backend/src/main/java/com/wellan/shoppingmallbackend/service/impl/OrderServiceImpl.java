package com.wellan.shoppingmallbackend.service.impl;

import com.wellan.shoppingmallbackend.dao.OrderDao;
import com.wellan.shoppingmallbackend.dao.ProductDao;
import com.wellan.shoppingmallbackend.dto.BuyItem;
import com.wellan.shoppingmallbackend.dto.CreateOrderRequest;
import com.wellan.shoppingmallbackend.model.OrderItem;
import com.wellan.shoppingmallbackend.model.Product;
import com.wellan.shoppingmallbackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService  {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;
    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();
        for (BuyItem buyItem: createOrderRequest.getBuyItemList()
             ) {
            Product product = productDao.getProductById(buyItem.getProductId());
            //計算總額
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount+=amount;
            //轉換BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);
            orderItemList.add(orderItem);
        }
        //創建訂單
        Integer orderId = orderDao.createOrder(userId,totalAmount);
        orderDao.createOrderItems(orderId,orderItemList);
        return orderId;
    }
}
