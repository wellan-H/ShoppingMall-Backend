package com.wellan.shoppingmallbackend.controller;

import com.wellan.shoppingmallbackend.constant.ProductCategory;
import com.wellan.shoppingmallbackend.constant.ProductQueryParam;
import com.wellan.shoppingmallbackend.dto.ProductRequest;
import com.wellan.shoppingmallbackend.model.Product;
import com.wellan.shoppingmallbackend.service.ProductService;
import com.wellan.shoppingmallbackend.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
@Validated
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
//             查詢條件
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,
//            排序條件
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,
//            分頁條件
            @RequestParam(defaultValue = "5") @Min(0)@Max(1000) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset
            ){
        ProductQueryParam productQueryParam = new ProductQueryParam();
        productQueryParam.setCategory(category);
        productQueryParam.setSearch(search);
        productQueryParam.setOrderBy(orderBy);
        productQueryParam.setSort(sort);
        productQueryParam.setLimit(limit);
        productQueryParam.setOffset(offset);
        List<Product> productList = productService.getProducts(productQueryParam);
        Page<Product> productPage =new Page<>();
        productPage.setOffset(offset);
        productPage.setLimit(limit);
        productPage.setTotal(productService.countProduct(productQueryParam));
        productPage.setList(productList);
        return ResponseEntity.status(HttpStatus.OK).body(productPage);



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
        Integer productId = productService.createProduct(productRequest);
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProductById(@PathVariable Integer productId,
                                                     @RequestBody ProductRequest productRequest){
        Product product = productService.getProductById(productId);
        if (product==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        productService.updateProductById(productId,productRequest);
        Product productUpdated = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(productUpdated);
    }
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProductById(@PathVariable Integer productId){
        productService.deleteProductById(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
