package com.wellan.shoppingmallbackend.dao;

import com.wellan.shoppingmallbackend.dto.ProductRequest;
import com.wellan.shoppingmallbackend.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);

    Integer createdProduct(ProductRequest productRequest);
}
