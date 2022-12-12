package com.wayneyeh.springbootmall.service.impl;

import com.wayneyeh.springbootmall.dao.OrderDao;
import com.wayneyeh.springbootmall.dao.ProductDao;
import com.wayneyeh.springbootmall.dto.BuyItem;
import com.wayneyeh.springbootmall.dto.CreateOrderRequest;
import com.wayneyeh.springbootmall.model.Order;
import com.wayneyeh.springbootmall.model.Product;
import com.wayneyeh.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.wayneyeh.springbootmall.model.OrderItem;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;



    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.gerOrderById(orderId);

        List<OrderItem> orderItemList = orderDao.getOrderItemById(orderId);

        order.setOrderItemList(orderItemList);
        return order;
    }

    @Transactional
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {



        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {

            Product product = productDao.getProductById((buyItem.getProductId()));

            int amount = buyItem.getQuantity() * product.getPrice();

            //calculate total

            totalAmount = totalAmount + amount;

            //buyItme to orderItem

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);

        }


        //create order
        Integer orderId = orderDao.createOrder(userId, totalAmount);

        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }



}
