package com.wellan.shoppingmallbackend.service.impl;

import com.wellan.shoppingmallbackend.dao.OrderDao;
import com.wellan.shoppingmallbackend.dao.ProductDao;
import com.wellan.shoppingmallbackend.dao.UserDao;
import com.wellan.shoppingmallbackend.dto.BuyItem;
import com.wellan.shoppingmallbackend.dto.CreateOrderRequest;
import com.wellan.shoppingmallbackend.dto.OrderQueryParams;
import com.wellan.shoppingmallbackend.model.Order;
import com.wellan.shoppingmallbackend.model.OrderItem;
import com.wellan.shoppingmallbackend.model.Product;
import com.wellan.shoppingmallbackend.model.User;
import com.wellan.shoppingmallbackend.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService  {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserDao userDao;

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        //檢查該  user 是否存在
        User user  = userDao.getUserById(userId);
        if (user == null){
            log.warn("該userId:{}不存在",userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();
        for (BuyItem buyItem: createOrderRequest.getBuyItemList()
             ) {
            Product product = productDao.getProductById(buyItem.getProductId());
            if (product==null){
                log.warn("商品{}不存在",buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else if (product.getStock()<buyItem.getQuantity()) {
                log.warn("商品{}數量庫存不足，無法購買。剩餘庫存：{}，欲購買數量：{}",buyItem.getProductId(),product.getStock(),buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            productDao.updateStock(product.getProductId(),product.getStock()-buyItem.getQuantity());
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

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);
        List<OrderItem> orderItemList = orderDao.getOrderItemByOrderId(orderId);
        order.setItemList(orderItemList);
        return order;
    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        List<Order> orderList = orderDao.getOrders(orderQueryParams);
        for (Order order:orderList){
            List<OrderItem> orderItemList = orderDao.getOrderItemByOrderId(order.getOrderId());
            order.setItemList(orderItemList);
        }
        return orderList;
    }

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {
        return orderDao.countOrder(orderQueryParams);
    }
}
