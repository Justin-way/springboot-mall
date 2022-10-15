package com.wayneyeh.springbootmall.main;

import com.wayneyeh.springbootmall.dao.impl.ProductDaoImpl;

public class Main {

    public static void main(String[] args) {

        ProductDaoImpl productDao = new ProductDaoImpl();

        productDao.getProductById(1);
    }
}
