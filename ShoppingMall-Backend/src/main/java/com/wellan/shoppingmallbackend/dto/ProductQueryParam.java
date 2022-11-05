package com.wellan.shoppingmallbackend.dto;

import com.wellan.shoppingmallbackend.constant.ProductCategory;

public class ProductQueryParam {
    private ProductCategory productCategory;
    private String search;

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
