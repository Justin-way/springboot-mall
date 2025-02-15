package com.example.docker.controller;

import com.example.docker.model.Product;
import com.example.docker.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.docker.service.ProductService;


import java.sql.SQLException;
import java.util.List;

import static javax.management.remote.JMXConnectorFactory.connect;
@RestController
public class ProductController {


    @Autowired
    private ProductService productService;  // 使用 @Autowired 注入 Dao 實例
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/test")
    public String test() throws SQLException {
        productService.connect();
        return "222";
    }
    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts() {
        System.out.println("getAllProducts");
        return productService.fetchProducts();
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.findProductById(id);
    }


    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product) {

        return productService.addProduct(product);
    }

    @PutMapping("/product/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product existingProduct = productService.findProductById(id);
        if (existingProduct == null) {
            throw new IllegalArgumentException("Product not found with ID: " + id);
        }

        // 更新產品資料
        existingProduct.setProductName(product.getProductName());
        existingProduct.setPrice(product.getPrice());

        return productService.addProduct(existingProduct);
    }

    @DeleteMapping("/product/{id}")
    public String deleteProduct(@PathVariable Long id) {
        Product existingProduct = productService.findProductById(id);
        if (existingProduct == null) {
            throw new IllegalArgumentException("Product not found with ID: " + id);
        }

        productService.deleteProduct(id);  // 使用 service 層刪除產品
        return "Product with ID " + id + " has been deleted successfully.";
    }



}
