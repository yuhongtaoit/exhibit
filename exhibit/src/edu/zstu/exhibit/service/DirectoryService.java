package edu.zstu.exhibit.service;

import edu.zstu.exhibit.domain.Directory;
import edu.zstu.exhibit.util.JdbcUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by aning on 16/6/2.
 */
public class DirectoryService {
    public List<Directory> getDirectoryByType(int directoryType) throws Exception {
        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
        String sql2 = "select directoryName from directoryinfo where directoryType = ?";
        List<Object> params = new ArrayList<>();
        params.add(directoryType);
        List<Directory> directories = jdbcUtils.findMoreRefResult(sql2, params, Directory.class);
        jdbcUtils.releaseConn();
        return directories;
    }

    public List<Directory> getAllDirectorys() throws Exception {
        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
        String sql = "select directoryName,directoryType from directoryinfo";
        List<Directory> directories = jdbcUtils.findMoreRefResult(sql, null, Directory.class);
        jdbcUtils.releaseConn();
        return directories;
    }

    public boolean save(Directory directory) {
        boolean flag = false;
        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
        String sql = "insert into directoryinfo (directoryName, directoryType) values (?, ?)";
        List<Object> params = new ArrayList<Object>();
        params.add(directory.getDirectoryName());
        params.add(directory.getDirectoryType());
        try {
            flag = jdbcUtils.updateByPreparedStatement(sql, params);
        } catch (SQLException e) {
            System.out.print("该字典类型已存在");
        }
        jdbcUtils.releaseConn();
        return flag;
    }

    public static void main(String[] args) throws Exception {
        System.out.print(new DirectoryService().getAllDirectorys());
    }
}
