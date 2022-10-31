package com.wellan.shoppingmallbackend.service;

import com.wellan.shoppingmallbackend.model.Product;

public interface ProductService {
    Product getProductById(Integer productId);
}
