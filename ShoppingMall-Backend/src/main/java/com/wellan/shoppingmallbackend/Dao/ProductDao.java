package com.wellan.shoppingmallbackend.Dao;

import com.wellan.shoppingmallbackend.Product;

public interface ProductDao {
    Product selectById(Integer id);
    void deleteById(Integer id);
    void add(Product product);
    void updateById(Integer id, Product product);
}
