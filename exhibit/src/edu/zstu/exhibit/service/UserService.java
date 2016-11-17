package edu.zstu.exhibit.service;

import edu.zstu.exhibit.domain.User;
import edu.zstu.exhibit.util.JdbcUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aning on 16/6/1.
 */
public class UserService {
    public User getUserByLogInName(String logInName) {
        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
        String sql = "select * from userinfo where LogInName = ?";
        List<Object> params = new ArrayList<Object>();
        params.add(logInName);
        User user = new User();
        try {
            user = jdbcUtils.findSimpleRefResult(sql, params, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        jdbcUtils.releaseConn();
        return user;
    }

    public List<User> getUserList() throws Exception {
        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
        String sql = "select * from userinfo";
        List<User> list = jdbcUtils.findAll(sql, User.class);
        jdbcUtils.releaseConn();
        return list;
    }

    public boolean save(User user) {
        boolean flag = false;
        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
        String sql ="INSERT INTO userinfo(UserName, UserDepartment, UserType, LogInName, UserPassword, Status)" +
                "VALUES (?, ?, ?, ?, ?, ?)";
        List<Object> params = new ArrayList<Object>();
        params.add(user.getUserName());
        params.add(user.getUserDepartment());
        params.add(user.getUserType());
        params.add(user.getLogInName());
        params.add(user.getUserPassword());
        params.add(user.getStatus());
        try {
            flag = jdbcUtils.updateByPreparedStatement(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtils.releaseConn();
        return flag;
    }

    public boolean del(int id) throws SQLException {
        boolean flag = false;
        JdbcUtils jdbcUtils = new JdbcUtils();
        Connection conn = jdbcUtils.getConnection();
        String sql = "delete from userinfo where id = ?";
        List<Object> params = new ArrayList<Object>();
        params.add(id);
        flag = jdbcUtils.updateByPreparedStatement(sql, params);
        jdbcUtils.releaseConn();
        return  flag;
    }

    public User getUserById(int id) {

        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
        String sql = "select Id,UserName,UserDepartment,UserType,LogInName,UserPassword,Status from userinfo where Id = ?";
        List<Object> params = new ArrayList<Object>();
        params.add(id);
        User user = new User();
        try {
            user = jdbcUtils.findSimpleRefResult(sql, params, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        jdbcUtils.releaseConn();
        return user;
    }

    public void update(User user) throws SQLException {
        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
        String sql = "UPDATE userinfo SET UserName=?, UserDepartment=?, " +
                "UserType=?, LogInName=?, UserPassword=?, Status=? WHERE Id=?";
        List<Object> params = new ArrayList<Object>();
        params.add(user.getUserName());
        params.add(user.getUserDepartment());
        params.add(user.getUserType());
        params.add(user.getLogInName());
        params.add(user.getUserPassword());
        params.add(user.getStatus());
        params.add(user.getId());
        jdbcUtils.updateByPreparedStatement(sql, params);
        jdbcUtils.releaseConn();
    }
}
