package com.wellan.shoppingmallbackend.service;

import com.wellan.shoppingmallbackend.constant.ProductCategory;
import com.wellan.shoppingmallbackend.dto.ProductQueryParam;
import com.wellan.shoppingmallbackend.dto.ProductRequest;
import com.wellan.shoppingmallbackend.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductService {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);

    List<Product> getProducts(ProductQueryParam  productQueryParam);
}
