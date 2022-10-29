package com.wellan.shoppingmallbackend.controller;

import com.wellan.shoppingmallbackend.Product;
import com.wellan.shoppingmallbackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
