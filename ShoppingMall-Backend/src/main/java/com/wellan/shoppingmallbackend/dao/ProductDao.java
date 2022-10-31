package com.wellan.shoppingmallbackend.dao;

import com.wellan.shoppingmallbackend.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);
}
