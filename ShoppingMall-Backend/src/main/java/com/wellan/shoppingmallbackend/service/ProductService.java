package com.wellan.shoppingmallbackend.service;

import com.wellan.shoppingmallbackend.constant.ProductQueryParam;
import com.wellan.shoppingmallbackend.dto.ProductRequest;
import com.wellan.shoppingmallbackend.model.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProductById(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);

    List<Product> getProducts(ProductQueryParam productQueryParam);

    Integer countProduct(ProductQueryParam productQueryParam);
}
