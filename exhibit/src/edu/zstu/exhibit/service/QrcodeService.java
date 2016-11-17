package edu.zstu.exhibit.service;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import edu.zstu.exhibit.domain.Product;
import edu.zstu.exhibit.domain.Qrcode;
import edu.zstu.exhibit.domain.QrcodeForm;
import edu.zstu.exhibit.util.JdbcUtils;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by aning on 16/6/4.
 */
public class QrcodeService {
    public List<Qrcode> getQrcodeList() throws Exception {
        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
        String sql = "select * from qrcodeinfo";
        List<Qrcode> list = jdbcUtils.findAll(sql, Qrcode.class);
        jdbcUtils.releaseConn();
        return list;
    }


    public boolean del(int id) throws SQLException {
        boolean flag = false;
        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
        String sql = "delete from qrcodeinfo where qrcodeId = ?";
        List<Object> params = new ArrayList<Object>();
        params.add(id);
        flag = jdbcUtils.updateByPreparedStatement(sql, params);
        jdbcUtils.releaseConn();
        return flag;
    }

    public List<Qrcode> getQrcodeListByCustomName(String customName) throws Exception {
        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
        String sql = "select * from qrcodeinfo where customName = ?";
        List<Object> params = new ArrayList<Object>();
        params.add(customName);
        List<Qrcode> list = jdbcUtils.findMoreRefResult(sql, params, Qrcode.class);
        jdbcUtils.releaseConn();
        return list;
    }

    public List<Qrcode> getQrcodeListBySaleName(String saleName) throws Exception {
        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
        String sql = "select * from qrcodeinfo where saleName = ?";
        List<Object> params = new ArrayList<Object>();
        params.add(saleName);
        List<Qrcode> list = jdbcUtils.findMoreRefResult(sql, params, Qrcode.class);
        jdbcUtils.releaseConn();
        return list;
    }

    public boolean delByCustomName(String customName) throws Exception {
        boolean flag = false;
        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
        String sql = "delete from qrcodeinfo where customName = ?";
        List<Object> params = new ArrayList<Object>();
        params.add(customName);
        jdbcUtils.updateByPreparedStatement(sql, params);
        flag = true;
        jdbcUtils.releaseConn();
        return flag;
    }

    public int save(String customName, String saleName, String productBarcodes,String comment) {
        boolean flag = false;
        int result = 0;
        String productBarcodess[] = productBarcodes.split(",");
        String comments[] = new String[productBarcodess.length];
        if(comment != null)
            comments = comment.split(",");
        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
        String sql = "INSERT INTO qrcodeinfo(customName, productBarcode, saleName, addTime,comment)" +
                "VALUES (?, ?, ?, ?,?)";
        for (int i = 0; i < productBarcodess.length; i++) {
            List<Object> params = new ArrayList<Object>();
            params.add(customName);
            params.add(productBarcodess[i]);
            params.add(saleName);
            params.add(System.currentTimeMillis() / 1000);
            if(comment == null) {
                params.add("无");
            } else
                params.add(comments[i]);
            try {
                jdbcUtils.updateByPreparedStatement(sql, params);
                flag = true;
            } catch (MySQLIntegrityConstraintViolationException e) {
                result = -1;
                System.out.println("该用户重复扫描");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(flag) result = 1;
        jdbcUtils.releaseConn();
        return result;
    }

    /*public int save(String customName, String saleName, QrcodeForm qrcodeForm) {
        int result = 0;
        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
        String sql = "INSERT INTO qrcodeinfo(customName, productBarcode, saleName, addTime,comment)" +
                "VALUES (?, ?, ?, ?,?)";
        List<Qrcode> qrcodes = qrcodeForm.getQrcodes();
        for (int i = 0; i < qrcodes.size(); i++) {
            List<Object> params = new ArrayList<Object>();
            params.add(customName);
            params.add(qrcodes.get(i).getProductBarcode());
            params.add(saleName);
            params.add(System.currentTimeMillis() / 1000);
            params.add(qrcodes.get(i).getComment());
            try {
                jdbcUtils.updateByPreparedStatement(sql, params);
                result = 1;
            } catch (MySQLIntegrityConstraintViolationException e) {
                result = -1;
                System.out.println("该用户重复扫描");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        jdbcUtils.releaseConn();
        return result;
    }*/

}
