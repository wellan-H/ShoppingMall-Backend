package com.wellan.shoppingmallbackend.controller;

import com.wellan.shoppingmallbackend.Product;
import com.wellan.shoppingmallbackend.ProductRowmapper;
import com.wellan.shoppingmallbackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class ProductController {
//    namedTemplate
    @Autowired
    private ProductService productService;
    //CRUD
//    Read
    @GetMapping("/product/{productId}")
    public Product selectByproductId(@PathVariable Integer productId){
        return productService.selectById(productId);
    }
//    Create()
    @PostMapping("/product")
    public String create(@RequestBody Product product){
        productService.add(product);
        return "create 方法";
    }
//    Update()
    @PutMapping("/product/{productId}")
    public void update(@PathVariable Integer productId,
                         @RequestBody Product product){
        productService.updateById(productId,product);
    }
//    Delete()
    @DeleteMapping("/product/{productId}")
    public void delete(@PathVariable Integer productId){
        productService.deleteById(productId);
    }

}
