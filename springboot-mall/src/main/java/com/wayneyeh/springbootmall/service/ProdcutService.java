package com.wayneyeh.springbootmall.service;

import com.wayneyeh.springbootmall.dto.ProductRequest;
import com.wayneyeh.springbootmall.model.Product;

public interface ProdcutService {

    Product getProductById(Integer prodctId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);
}
