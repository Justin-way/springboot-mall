package com.wayneyeh.springbootmall.service.impl;

import com.wayneyeh.springbootmall.dao.OrderDao;
import com.wayneyeh.springbootmall.dao.ProductDao;
import com.wayneyeh.springbootmall.dao.UserDao;
import com.wayneyeh.springbootmall.dto.BuyItem;
import com.wayneyeh.springbootmall.dto.CreateOrderRequest;
import com.wayneyeh.springbootmall.dto.OrderQueryParams;
import com.wayneyeh.springbootmall.model.Order;
import com.wayneyeh.springbootmall.model.Product;
import com.wayneyeh.springbootmall.model.User;
import com.wayneyeh.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.wayneyeh.springbootmall.model.OrderItem;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Override
    public Integer countOrder(OrderQueryParams orderQuertParams) {

        return orderDao.countOrder(orderQuertParams);
    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {

        List<Order> orderList = orderDao.getOrders(orderQueryParams);

        for (Order order : orderList) {

            List<OrderItem> orderItemList = orderDao.getOrderItemById(order.getOrderId());

            order.setOrderItemList(orderItemList);
        }

        return orderList;

    }

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.gerOrderById(orderId);

        List<OrderItem> orderItemList = orderDao.getOrderItemById(orderId);

        order.setOrderItemList(orderItemList);

        return order;
    }

    @Transactional
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {


        //check user
        User user = userDao.getUserById(userId);

        if (user == null) {
            log.warn("userId {} not exists", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //check stcok
        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {

            Product product = productDao.getProductById(buyItem.getProductId());

            if (product == null) {
                log.warn("product{} does not exist", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

            } else if (product.getStock() < buyItem.getQuantity()) {

                log.warn("Product{} stcok not enough remain {}, but{}", buyItem.getProductId(), product.getStock(), buyItem.getQuantity());

                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

            }

            //deduct stock

            productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());

        }

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
