package com.wellan.shoppingmallbackend.service;

import com.wellan.shoppingmallbackend.Product;

public interface ProductService {
    Product selectById(Integer id);
    void deleteById(Integer id);
    void add(Product product);

    void updateById(Integer id,Product product);
}
