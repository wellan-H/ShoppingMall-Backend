package com.wellan.shoppingmallbackend.dao.impl;

import com.wellan.shoppingmallbackend.dao.OrderDao;
import com.wellan.shoppingmallbackend.dto.OrderQueryParams;
import com.wellan.shoppingmallbackend.model.Order;
import com.wellan.shoppingmallbackend.model.OrderItem;
import com.wellan.shoppingmallbackend.rowmapper.OrderItemRowMapper;
import com.wellan.shoppingmallbackend.rowmapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public Integer createOrder(Integer userId, Integer totalAmount) {
        String sql ="INSERT INTO `order`(user_id, total_amount, created_date, " +
                "last_modified_date) VALUES (:userId, :totalAmount, :createdDate, " +
                ":lastModifiedDate)";
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("totalAmount",totalAmount);
        Date now  = new Date();
        map.put("createdDate",now);
        map.put("lastModifiedDate",now);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        int orderId = keyHolder.getKey().intValue();
        return orderId;
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {
//        根據是否批量添加，有兩種方式
//        透過for loop一筆筆添加
//        for (OrderItem orderItem :orderItemList){
//            String sql = "INSERT INTO order_item(order_id, product_id, quantity, amount) " +
//                    "VALUES (:orderId, :productId, :quantity, :amount)";
//            Map<String, Object> map = new HashMap<>();
//            map.put("orderId", orderItem.getOrderId());
//            map.put("productId", orderItem.getProductId());
//            map.put("quantity", orderItem.getQuantity());
//            map.put("amount",orderItem.getAmount());
//            namedParameterJdbcTemplate.update(sql,map);
//
//        }
        //  批量添加
        String sql = "INSERT INTO order_item(order_id, product_id, quantity, amount) " +
                "VALUES (:orderId, :productId, :quantity, :amount)";
        MapSqlParameterSource[] sqlParameterSources  = new MapSqlParameterSource[orderItemList.size()];
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem  = orderItemList.get(i);
            sqlParameterSources[i] = new MapSqlParameterSource();
            sqlParameterSources[i].addValue("orderId", orderId);
            sqlParameterSources[i].addValue("productId", orderItem.getProductId());
            sqlParameterSources[i].addValue("quantity", orderItem.getQuantity());
            sqlParameterSources[i].addValue("amount",orderItem.getAmount());

        }
        namedParameterJdbcTemplate.batchUpdate(sql,sqlParameterSources);

    }

    @Override
    public Order getOrderById(Integer orderId) {
        String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date" +
                " FROM `order` WHERE order_id = :orderId";
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());
        if (orderList.size()>0){
            return orderList.get(0);
        }else return null;
    }

    @Override
    public List<OrderItem> getOrderItemByOrderId(Integer orderId) {
        String sql = "SELECT oi.order_item_id, oi.order_id, oi.product_id, oi.quantity, oi.amount, p.product_name, p.image_url  " +
                "FROM order_item AS oi " +
                "LEFT JOIN product AS p ON oi.product_id = p.product_id " +
                "WHERE order_id = :orderId";
        Map<String , Object> map = new HashMap<>();
        map.put("orderId",orderId);
        List<OrderItem> orderItemList = namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());

        return orderItemList;
    }

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {
        String sql = "SELECT COUNT(*) FROM `order` WHERE 1= 1";
        Map<String,Object> map = new HashMap<>();
//        查詢條件
        sql = addFilteringSql(sql,map,orderQueryParams);

        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
        return total;
    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        String sql = "SELECT order_id, user_id, total_amount, " +
                "created_date, last_modified_date FROM `order` WHERE 1 = 1 ";
        Map<String , Object> map = new HashMap<>();
//        查詢條件
        sql = addFilteringSql(sql,map,orderQueryParams);
//        排序
        sql+= " ORDER BY created_date DESC ";
//        分頁
        sql+=" LIMIT :limit OFFSET :offset";
        map.put("limit", orderQueryParams.getLimit());
        map.put("offset", orderQueryParams.getOffset());
        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());
        return orderList;
    }
    private String addFilteringSql(String sql , Map<String,Object> map , OrderQueryParams orderQueryParams){
        if(orderQueryParams.getUserId()!=null){
            sql+=" AND user_id = :userId";
            map.put("userId",orderQueryParams.getUserId());
        }
        return sql;
    }
}
