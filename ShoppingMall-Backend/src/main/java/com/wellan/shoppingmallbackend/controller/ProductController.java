package com.wellan.shoppingmallbackend.controller;

import com.wellan.shoppingmallbackend.constant.ProductCategory;
import com.wellan.shoppingmallbackend.dto.ProductQueryParam;
import com.wellan.shoppingmallbackend.dto.ProductRequest;
import com.wellan.shoppingmallbackend.model.Product;
import com.wellan.shoppingmallbackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(@RequestParam(required = false) ProductCategory category,
                                                     @RequestParam(required = false) String search){
        ProductQueryParam productQueryParam = new ProductQueryParam();
        productQueryParam.setProductCategory(category);
        productQueryParam.setSearch(search);
        List<Product> productList = productService.getProducts(productQueryParam);
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer productId){
        Product product = productService.getProductById(productId);
        if (product!=null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer id = productService.createProduct(productRequest);
        Product product = productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest){
        Product product = productService.getProductById(productId);
        if (product==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        productService.updateProduct(productId,productRequest);
        Product productUpdated = productService.getProductById(productId);
        return  ResponseEntity.status(HttpStatus.OK).body(productUpdated);

    }
    @DeleteMapping("/products/{productId}")
    public ResponseEntity deleteProduct(@PathVariable Integer productId){
        productService.deleteProductById(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
