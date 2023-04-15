package com.wayneyeh.springbootmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
