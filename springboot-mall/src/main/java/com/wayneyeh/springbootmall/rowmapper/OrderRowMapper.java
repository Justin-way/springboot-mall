package com.wayneyeh.springbootmall.rowmapper;

import com.wayneyeh.springbootmall.model.Order;
import org.apache.coyote.Request;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<Order> {

    public Order mapRow(ResultSet resultSet, int i) throws SQLException {
        Order order = new Order();

        order.setOrderId(resultSet.getInt("order_id"));
        order.setUserId(resultSet.getInt("user_id"));
        order.setTotalAmount(resultSet.getInt("total_amount"));
        order.setLastModifiedDate(resultSet.getDate("created_date"));
        order.setCreatedDate(resultSet.getDate("created_date"));

        return order;
    }
}
