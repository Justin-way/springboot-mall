package com.wayneyeh.springbootmall.dao;

import com.wayneyeh.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer prodctId);
}
