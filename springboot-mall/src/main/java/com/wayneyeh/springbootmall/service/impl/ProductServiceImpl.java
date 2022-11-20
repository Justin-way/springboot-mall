package com.wayneyeh.springbootmall.service.impl;

import com.wayneyeh.springbootmall.constant.ProductCategory;
import com.wayneyeh.springbootmall.dao.ProductDao;
import com.wayneyeh.springbootmall.dto.ProductRequest;
import com.wayneyeh.springbootmall.model.Product;
import com.wayneyeh.springbootmall.service.ProdcutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProdcutService {

    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> getProducts(ProductCategory category, String search) {

        return productDao.getProducts(category, search);
    }

    @Override
    public Product getProductById(Integer prodctId) {
        return productDao.getProductById(prodctId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        productDao.updateProduct(productId, productRequest);
    }

    @Override
    public void deleteProductById(Integer productId) {
        productDao.deleteProductById(productId);
    }
}
