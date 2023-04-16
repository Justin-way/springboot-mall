package com.wayneyeh.springbootmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String gethome(){
        return "index";
    }
    @GetMapping("/query_order")
    public String queryOrder(){
        return "query_order";
    }

    @GetMapping("/create_order")
    public String createOrder(){
        return "create_order";
    }

    @GetMapping("/query_products")
    public String getProducts(){
        return "query_products";
    }


    @GetMapping("/query_product")
    public String getProduct(){
        return "query_product";
    }

    @GetMapping("/create_product")
    public String createProduct(){
        return "create_product";
    }
    @GetMapping("/update_product")
    public String updateProduct(){
        return "update_product";
    }

    @GetMapping("/delete_product")
    public String deleteProduct(){
        return "delete_product";
    }
}
