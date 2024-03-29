package com.wayneyeh.springbootmall.controller;

import com.wayneyeh.springbootmall.constant.ProductCategory;
import com.wayneyeh.springbootmall.dto.ProductQueryParams;
import com.wayneyeh.springbootmall.dto.ProductRequest;
import com.wayneyeh.springbootmall.model.Product;
import com.wayneyeh.springbootmall.service.ProdcutService;
import com.wayneyeh.springbootmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
public class ProductController {

    @Autowired
    ProdcutService prodcutService;


    @GetMapping("/products") //一定要加s
    public ResponseEntity<Page<Product>> getProducts(
            //Filtering
            @RequestParam(required = false) ProductCategory category, //如果只使用@RequestParam 會是必選
            @RequestParam(required = false) String search,
            //Sorting
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,
            //Pagination
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset
    ){
        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search); // 統一整理到productQueryParams 作為變數, 如果之後新增查詢條件直接從這裡修改即可
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);
        //get product list
        List<Product> productList = prodcutService.getProducts(productQueryParams);

        //get total product
        Integer total = prodcutService.countProduct(productQueryParams);

        //pagination
        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(productList);


        return ResponseEntity.status(HttpStatus.OK).body(page);
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

        Product product = prodcutService.getProductById(productId);

        if (product == null) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        prodcutService.deleteProductById(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
