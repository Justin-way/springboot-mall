package com.wayneyeh.springbootmall.controller;

import com.wayneyeh.springbootmall.dto.ProductRequest;
import com.wayneyeh.springbootmall.model.Product;
import com.wayneyeh.springbootmall.service.ProdcutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProdcutService prodcutService;

    @GetMapping("/products") //一定要加s
    public ResponseEntity<List<Product>> getProducts(){
        List<Product> productList = prodcutService.getProducts();

        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProdct(@PathVariable Integer productId) {
        Product product = prodcutService.getProductById(productId);

        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Validated ProductRequest productRequest){
    //  一定要記得加validated 註解 NotNull 才有用
        Integer productId =  prodcutService.createProduct(productRequest);

        Product product = prodcutService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,@RequestBody @Validated ProductRequest productRequest){

        //check product exists
        Product product = prodcutService.getProductById(productId);

        if (product == null) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        //revise data
        prodcutService.updateProduct(productId, productRequest);

        Product updatedProduct = prodcutService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId){
        prodcutService.deleteProductById(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
