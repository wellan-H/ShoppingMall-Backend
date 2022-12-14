package com.wellan.shoppingmallbackend.dao.impl;

import com.wellan.shoppingmallbackend.constant.ProductQueryParam;
import com.wellan.shoppingmallbackend.dao.ProductDao;
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
public class ProductDaoImpl implements ProductDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void updateStock(Integer productId, Integer stock) {
        String  sql = "UPDATE product SET stock = :stock, last_modified_date = :lastModifiedDate, " +
                "WHERE product_id = :productId";
        Map<String,Object> map = new HashMap<>();
        map.put("stock", stock);
        map.put("lastModifiedDate",new Date());
        map.put("productId", productId);
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public Product getProductById(Integer productId) {
        String sql = "SELECT product_id, product_name, category, " +
                "image_url, price, stock, description, " +
                "created_date, last_modified_date FROM product " +
                "WHERE product_id =:productId";
        Map<String, Object> map = new HashMap<>();
        map.put("productId",productId);
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        if (productList.size()>0){
            return productList.get(0);
        }else return null;
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "INSERT INTO product(product_name, category, image_url, price, stock, " +
                "description, created_date, last_modified_date) VALUES(" +
                ":productName, :category, :imageUrl, :price, :stock, :description, " +
                " :createdDate, :lastModifiedDate)";
        Map<String, Object>map =new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().name());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
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
    public void updateProductById(Integer productId, ProductRequest productRequest) {
        String sql = "UPDATE product SET product_name=:productName, category=:category, " +
                "image_url=:imageUrl, price=:price, stock=:stock, description=:description, " +
                "last_modified_date=:lastModifiedDate WHERE product_id = :productId";
        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().name());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());
        map.put("lastModifiedDate", new Date());
        map.put("productId", productId);
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void deleteProductById(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id=:productId";
        Map<String,Object> map = new HashMap<>();
        map.put("productId",productId);
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public List<Product> getProducts(ProductQueryParam productQueryParam) {
        String sql = "SELECT product_id, product_name, category, image_url, " +
                "price, stock, description, created_date, last_modified_date FROM product WHERE 1=1 ";
        Map<String ,Object> map = new HashMap<>();
        sql = addFiltering(sql,map,productQueryParam);
        sql+=" ORDER BY "+productQueryParam.getOrderBy()+" "+productQueryParam.getSort()+" ";
        sql+="LIMIT :limit OFFSET :offset ";
        map.put("limit",productQueryParam.getLimit());
        map.put("offset", productQueryParam.getOffset());
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        return productList;
    }

    @Override
    public Integer countProduct(ProductQueryParam productQueryParam) {
        String sql = "SELECT COUNT(*) FROM product WHERE 1=1 ";
        Map<String ,Object> map = new HashMap<>();
        sql = addFiltering(sql, map, productQueryParam);
        Integer count = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
        return count;
    }
    private String addFiltering(String sql ,Map map ,ProductQueryParam productQueryParam){
        if (productQueryParam.getCategory()!=null){
            sql += " AND category=:category ";
            map.put("category",productQueryParam.getCategory().name());
        }
        if (productQueryParam.getSearch()!=null){
            sql+=" AND search LIKE :search ";
            map.put("search","%" + productQueryParam.getSearch() + "%");
        }
        return sql;
    }
}
