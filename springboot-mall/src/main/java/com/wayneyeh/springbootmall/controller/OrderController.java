package com.wayneyeh.springbootmall.controller;

import com.wayneyeh.springbootmall.dto.CreateOrderRequest;
import com.wayneyeh.springbootmall.dto.OrderQueryParams;
import com.wayneyeh.springbootmall.service.OrderService;
import com.wayneyeh.springbootmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.wayneyeh.springbootmall.model.Order;
import org.springframework.http.HttpHeaders;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrders(
        @PathVariable Integer userId,
        @RequestParam(defaultValue = "10") @Max(1000) @Min(0) Integer limit,
        @RequestParam(defaultValue = "0") @Min(0) Integer offset
    ){
        OrderQueryParams queryParams = new OrderQueryParams();

        queryParams.setUserId(userId);
        queryParams.setLimit(limit);
        queryParams.setOffset(offset);

        //get Order List

        List<Order> orderList = orderService.getOrders(queryParams);

        //get Order total count

        Integer count = orderService.countOrder(queryParams);

        //paging

        Page<Order> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(count);
        page.setResults(orderList);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "http://localhost"); // 這裡的 "http://localhost" 要改成您前端網頁的網址

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers).body(page);

    }




    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest){
        Integer orderId = orderService.createOrder(userId, createOrderRequest);

        Order order = orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
