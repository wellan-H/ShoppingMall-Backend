package com.wellan.shoppingmallbackend.Dao;

import com.wellan.shoppingmallbackend.model.Product;

public interface ProductDao {
    Product selectById(Integer productId);
    void deleteById(Integer id);
    void add(Product product);
    void updateById(Integer id, Product product);
}
