package com.wellan.shoppingmallbackend.dao;

import com.wellan.shoppingmallbackend.constant.ProductCategory;
import com.wellan.shoppingmallbackend.dto.ProductQueryParam;
import com.wellan.shoppingmallbackend.dto.ProductRequest;
import com.wellan.shoppingmallbackend.model.Product;

import java.util.List;

public interface ProductDao {
    Product getProductById(Integer productId);

    Integer createdProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);

    List<Product> getProducts(ProductQueryParam productQueryParam);
}
