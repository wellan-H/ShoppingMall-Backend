package com.wellan.shoppingmallbackend.dto;

import com.wellan.shoppingmallbackend.constant.ProductCategory;

public class ProductQueryParam {
    private ProductCategory productCategory;
    private String search;
    private String orderBy;
    private String sort;

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

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