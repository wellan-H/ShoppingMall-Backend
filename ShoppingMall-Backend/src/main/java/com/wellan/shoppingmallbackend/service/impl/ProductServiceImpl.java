package com.wellan.shoppingmallbackend.service.impl;

import com.wellan.shoppingmallbackend.constant.ProductQueryParam;
import com.wellan.shoppingmallbackend.dao.ProductDao;
import com.wellan.shoppingmallbackend.dto.ProductRequest;
import com.wellan.shoppingmallbackend.model.Product;
import com.wellan.shoppingmallbackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }



    @Override
    public void updateProductById(Integer productId, ProductRequest productRequest) {
        productDao.updateProductById(productId,productRequest);
    }
    @Override
    public void deleteProductById(Integer productId) {
        productDao.deleteProductById(productId);
    }

    @Override
    public List<Product> getProducts(ProductQueryParam productQueryParam) {
        return productDao.getProducts(productQueryParam);
    }

    @Override
    public Integer countProduct(ProductQueryParam productQueryParam) {
        return productDao.countProduct(productQueryParam);
    }
}
