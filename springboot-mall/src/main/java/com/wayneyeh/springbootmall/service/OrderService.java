package com.wayneyeh.springbootmall.service;

import com.wayneyeh.springbootmall.dto.CreateOrderRequest;
import com.wayneyeh.springbootmall.model.Order;

public interface OrderService {

    Order getOrderById(Integer orderId);
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);



}
