package com.wellan.shoppingmallbackend.service;

import com.wellan.shoppingmallbackend.dto.ProductRequest;
import com.wellan.shoppingmallbackend.model.Product;
import org.springframework.stereotype.Component;

@Component
public interface ProductService {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
}
