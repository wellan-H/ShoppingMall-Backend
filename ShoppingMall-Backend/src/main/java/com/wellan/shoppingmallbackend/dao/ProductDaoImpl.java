package com.wellan.shoppingmallbackend.dao;

import com.wellan.shoppingmallbackend.dto.ProductQueryParam;
import com.wellan.shoppingmallbackend.dto.ProductRequest;
import com.wellan.shoppingmallbackend.model.Product;
import com.wellan.shoppingmallbackend.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class ProductDaoImpl implements ProductDao{
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Product> getProducts(ProductQueryParam productQueryParam) {
        String sql = "SELECT product_id, product_name, category, " +
                "image_url, price, stock, description, created_date, last_modified_date " +
                "FROM product WHERE 1=1 ";
        Map<String,Object>map = new HashMap<>();
        if(productQueryParam.getCategory()!=null){
            sql= sql+" AND category = :category";
            map.put("category",productQueryParam.getCategory().name());
        }
        if(productQueryParam.getSearch()!=null){
            sql= sql+" AND product_name LIKE :search ";
            map.put("search", "%"+productQueryParam.getSearch()+"%");
        }
        sql = sql+" ORDER BY "+productQueryParam.getOrderBy()+" "+productQueryParam.getSort();
        sql=sql+" LIMIT :limit OFFSET :offset";
        map.put("limit",productQueryParam.getLimit());
        map.put("offset",productQueryParam.getOffset());
        return namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(map), new ProductRowMapper());


    }

    @Override
    public Product getProductById(Integer productId) {
        String sql = "SELECT product_id, product_name, " +
                "category, image_url, price, stock, " +
                "description, created_date, last_modified_date " +
                "FROM product WHERE product_id = :productId";
        Map<String,Object> map = new HashMap<>();
        map.put("productId", productId);
        List<Product> list = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        if (list.size()>0){
            return list.get(0);
        }else return null;


    }

    @Override
    public Integer createdProduct(ProductRequest productRequest) {
        String sql = "INSERT INTO product" +
                "(product_name, category, " +
                "image_url, price, stock, description, " +
                "created_date, last_modified_date) VALUES " +
                "(:productName, :category, " +
                ":imageUrl, :price, :stock, :description, " +
                ":createdDate, :lastModifiedDate)";
        Map<String,Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category",productRequest.getCategory());
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());
        Date now = new Date();
        map.put("createdDate",now);
        map.put("lastModifiedDate",now);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        int id = keyHolder.getKey().intValue();
        return id;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        String sql  = "UPDATE product SET product_name=:productName, " +
                "category=:category, image_url=:imageUrl, price=:price, " +
                "stock=:stock, description=:description, last_modified_date = " +
                ":lastModifiedDate WHERE product_id = :productId";
        Map<String,Object> map  = new HashMap<>();
        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory());
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description", productRequest.getDescription());
        map.put("lastModifiedDate",new Date());
        map.put("productId", productId);
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteProductById(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id=:productId";
        Map<String,Object> map  = new HashMap<>();
        map.put("productId", productId);
        namedParameterJdbcTemplate.update(sql,map);
    }
}
