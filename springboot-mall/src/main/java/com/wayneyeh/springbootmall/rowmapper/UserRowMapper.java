package com.wayneyeh.springbootmall.rowmapper;

import com.wayneyeh.springbootmall.model.User;
import org.springframework.jdbc.core.RowMapper;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    public User mapRow(ResultSet resultSet, int i) throws SQLException{
        User user = new User();
        user.setUserId(resultSet.getInt("user_id"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setCreateDate(resultSet.getTimestamp("created_date"));
        user.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));

        return user;
    }
}
