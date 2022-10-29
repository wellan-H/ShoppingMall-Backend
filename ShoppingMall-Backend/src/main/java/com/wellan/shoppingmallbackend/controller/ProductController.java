package com.wellan.shoppingmallbackend.controller;

import com.wellan.shoppingmallbackend.Product;
import com.wellan.shoppingmallbackend.ProductRowmapper;
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
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    //CRUD
//    Read
    @GetMapping("/product/{productId}")
    public Product selectByproductId(@PathVariable Integer productId){
        String sql = "SELECT productId,name FROM Product WHERE id = :productId";
        Map<String,Object> map = new HashMap<>();
        map.put("productproductId",productId);
        List<Product> list = namedParameterJdbcTemplate.query(sql, map, new ProductRowmapper());
        if(list.size()>0){
            return list.get(0);
        }else return null;
    }
//    Create()
    @PostMapping("/product")
    public String create(@RequestBody Product product){
        String sql  = "INSERT INTO product VALUE(:productName)";
        Map<String, Object> map = new HashMap<>();
        map.put("productName",product.getProductName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
        int productId = keyHolder.getKey().intValue();
        return "create 方法";
    }
//    Update()
    @PutMapping("/product/{productId}")
    public String update(@PathVariable Integer productId,
                         @RequestBody Product product){
        String sql = "UPDATE product(name) VALUE(:productName) WHERE id =:productId ";
        Map<String,Object> map = new HashMap<>();
        map.put("productName",product.getProductName());
        return "update，獲得productId:"+productId;
    }
//    Delete()
    @DeleteMapping("/product/{productproductId}")
    public void delete(@PathVariable Integer productproductId){
        String sql = "DELETE FROM product WHERE productId = :productId";
        Map<String,Object> map = new HashMap<>();
        map.put("productproductId",productproductId);
        namedParameterJdbcTemplate.update(sql,map);
    }

}
