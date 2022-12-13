package com.wayneyeh.springbootmall.service;

import com.wayneyeh.springbootmall.dto.CreateOrderRequest;
import com.wayneyeh.springbootmall.model.Order;
import com.wayneyeh.springbootmall.dto.OrderQueryParams;

import java.util.List;

public interface OrderService {

    Integer countOrder(OrderQueryParams orderQuertParams);
    List<Order> getOrders(OrderQueryParams queryParams);

    Order getOrderById(Integer orderId);
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

}
