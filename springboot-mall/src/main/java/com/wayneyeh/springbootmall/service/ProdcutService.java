package com.wayneyeh.springbootmall.service;

import com.wayneyeh.springbootmall.constant.ProductCategory;
import com.wayneyeh.springbootmall.dto.ProductQueryParams;
import com.wayneyeh.springbootmall.dto.ProductRequest;
import com.wayneyeh.springbootmall.model.Product;

import java.util.List;

public interface ProdcutService {

    Integer countProduct(ProductQueryParams productQueryParams);
    List<Product> getProducts(ProductQueryParams productQueryParams);
    Product getProductById(Integer prodctId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);


}
