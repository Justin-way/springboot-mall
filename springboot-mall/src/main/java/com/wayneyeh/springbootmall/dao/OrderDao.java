package com.wayneyeh.springbootmall.dao;

import com.wayneyeh.springbootmall.model.Order;
import com.wayneyeh.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Order gerOrderById(Integer orderId);

    List<OrderItem> getOrderItemById(Integer orderId);

    Integer createOrder(Integer userId, Integer totaolAmout);

    void createOrderItems(Integer oredrId, List<OrderItem> orderItemList);
}

