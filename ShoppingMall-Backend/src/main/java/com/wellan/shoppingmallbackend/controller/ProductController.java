package com.wellan.shoppingmallbackend.controller;

import com.wellan.shoppingmallbackend.model.Product;
import com.wellan.shoppingmallbackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
public class ProductController {
//    namedTemplate
    @Autowired
    private ProductService productService;
    //CRUD
//    Read
    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> selectByproductId(@PathVariable Integer productId){
        Product product = productService.selectById(productId);
        if(product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
//    Create()
    @PostMapping("/product")
    public String create(@RequestBody Product product){
        productService.add(product);
        return "create 方法";
    }
//    Update()
    @PutMapping("/product/{productId}")
    public void update(@PathVariable @NotNull  Integer productId,
                         @RequestBody Product product){
        productService.updateById(productId,product);
    }
//    Delete()
    @DeleteMapping("/product/{productId}")
    public void delete(@PathVariable @NotNull  Integer productId){
        productService.deleteById(productId);
    }

}
