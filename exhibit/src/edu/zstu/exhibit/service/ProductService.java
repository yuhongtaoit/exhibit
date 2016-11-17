package edu.zstu.exhibit.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import edu.zstu.exhibit.domain.Product;
import edu.zstu.exhibit.util.JdbcUtils;
import edu.zstu.exhibit.util.Printer;
import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

/**
 * Created by aning on 16/6/1.
 */
public class ProductService {
    public List<Product> getProductList() throws Exception {
        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
        String sql = "select id, productType, productBarcode, productCode, productSizeIn, productSizeOut, " +
                "productSize, productMaterial, outframeCode, creatTime, unitPrice, productNumber," +
                " productVolume, productPackage, productDescribe, sizeInUnit, sizeOutUnit, sizeUnit, priceUnit from productinfo";
        List<Product> list = jdbcUtils.findAll(sql, Product.class);
        jdbcUtils.releaseConn();
        return list;
    }

    public boolean del(int id) throws SQLException {
        boolean flag = false;
        JdbcUtils jdbcUtils = new JdbcUtils();
        Connection conn = jdbcUtils.getConnection();
        String sql = "delete from productinfo where id = ?";
        List<Object> params = new ArrayList<Object>();
        params.add(id);
        flag = jdbcUtils.updateByPreparedStatement(sql, params);
        jdbcUtils.releaseConn();
        return flag;
    }

    public int save(Product product) throws SQLException {
        int result = -1;
        JdbcUtils jdbcUtils = new JdbcUtils();
        Connection conn = jdbcUtils.getConnection();
        String sql = "INSERT INTO productinfo (productType,productBarcode, productCode, productSizeIn, " +
                "productSizeOut, productSize, productMaterial, outframeCode, creatTime, " +
                "unitPrice, productNumber,productVolume,productPackage,productDescribe, sizeInUnit, sizeOutUnit, sizeUnit, priceUnit,productPicture) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ResultSet resultSet = null;
        if (product != null) {
            pstmt.setString(1, product.getProductType());
            pstmt.setString(2, product.getProductBarcode());
            pstmt.setString(3, product.getProductCode());
            pstmt.setString(4, product.getProductSizeIn());
            pstmt.setString(5, product.getProductSizeOut());
            pstmt.setString(6, product.getProductSize());
            pstmt.setString(7, product.getProductMaterial());
            pstmt.setString(8, product.getOutframeCode());
            pstmt.setLong(9, product.getCreatTime());
            pstmt.setBigDecimal(10, product.getUnitPrice());
            pstmt.setInt(11, product.getProductNumber());
            pstmt.setBigDecimal(12, product.getProductVolume());
            pstmt.setString(13, product.getProductPackage());
            pstmt.setString(14, product.getProductDescribe());
            pstmt.setString(15, product.getSizeInUnit());
            pstmt.setString(16, product.getSizeOutUnit());
            pstmt.setString(17, product.getSizeUnit());
            pstmt.setString(18, product.getPriceUnit());
            pstmt.setBinaryStream(19, product.getProductPicture());
            pstmt.executeUpdate();
            resultSet = pstmt.getGeneratedKeys();
            while (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        }
        if (resultSet != null)
            resultSet.close();

        if (pstmt != null)
            pstmt.close();

        if (conn != null)
            conn.close();
        return result;
    }

    public void saveProductBarcode(String productBarcode, int id) throws SQLException {
        JdbcUtils jdbcUtils = new JdbcUtils();
        Connection conn = jdbcUtils.getConnection();
        String sql = "update productinfo set productBarcode = ? where Id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, productBarcode);
        pstmt.setInt(2, id);
        pstmt.executeUpdate();
        if (pstmt != null)
            pstmt.close();
        if (conn != null) {
            conn.close();
        }
    }

    public Map<String, Integer> getDiffCustomName(String saleName) {
        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
        String sql = "select customName,count(customName) from qrcodeinfo,productinfo where qrcodeinfo.productBarcode = productinfo.productBarcode and saleName=? and addTime > ? GROUP BY customName";
        List<Object> params = new ArrayList<Object>();
        params.add(saleName);
        params.add(getDayBegin());
        return jdbcUtils.getDiffCustomName(sql, params);
    }

    public List<Map<String, Object>> getProductByProductSaleName(String saleName) {
        List<Map<String, Object>> objectMap = new ArrayList<>();
        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
       /* String sql = "select a.id, a.productType, a.productBarcode, a.productCode, a.productSizeIn, a.productSizeOut, " +
                "a.productSize, a.productMaterial, a.outframeCode, a.creatTime, a.unitPrice, a.productNumber," +
                "a.productVolume, a.productPackage, a.productDescribe, a.sizeInUnit, a.sizeOutUnit, a.sizeUnit, a.priceUnit, " +
                "b.id qrcodeId,b.customName from productinfo a, qrcodeinfo b where a.productBarcode = b.productBarcode and b.saleName=? and b.addTime > ? order by b.customName";
       */
        String sql = "select a.id, a.productType, a.productBarcode, a.productCode, a.productSizeIn, a.productSizeOut, " +
                "a.productSize, a.productMaterial, a.outframeCode, a.creatTime, a.unitPrice, a.productNumber," +
                "a.productVolume, a.productPackage, a.productDescribe, a.sizeInUnit, a.sizeOutUnit, a.sizeUnit, a.priceUnit, " +
                "b.qrcodeId,b.customName,b.comment from productinfo a, qrcodeinfo b where a.productBarcode = b.productBarcode and b.saleName=? and b.addTime > ? order by b.customName";

        List<Object> params = new ArrayList<Object>();
        params.add(saleName);
        params.add(getDayBegin());

        try {
            objectMap = jdbcUtils.findModeResult(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtils.releaseConn();
        return objectMap;
    }


    public Product getProductByProductBarcode(String productBarcode) {
        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
        String sql = "select id, productType, productBarcode, productCode, productSizeIn, productSizeOut, " +
                "productSize, productMaterial, outframeCode, creatTime, unitPrice, productNumber," +
                "productVolume, productPackage, productDescribe, sizeInUnit, sizeOutUnit, sizeUnit, priceUnit from productinfo" +
                " where productBarcode = ?";
        List<Object> params = new ArrayList<Object>();
        params.add(productBarcode);
        Product product = new Product();
        try {
            product = jdbcUtils.findSimpleRefResult(sql, params, Product.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        jdbcUtils.releaseConn();
        return product;
    }

    public String getProductCodeByProductBarcodes(String productBarcode) throws SQLException {

        String sql = "select productCode from productinfo where productBarcode = ?";
        JdbcUtils jdbcUtils = new JdbcUtils();
        Connection conn = jdbcUtils.getConnection();
        ResultSet resultSet = null;
        String productCode = "";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, productBarcode);
        resultSet = pstmt.executeQuery();
        resultSet.next();
        productCode = resultSet.getString(1);
        resultSet.close();
        pstmt.close();
        conn.close();
        return productCode;
    }

    public Map<String, Blob> getPhotoById(int id) throws SQLException {
        Map<String, Blob> map = new HashMap<String, Blob>();
        JdbcUtils jdbcUtils = new JdbcUtils();
        Connection conn = jdbcUtils.getConnection();
        String sql = "select productPicture from productinfo where Id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet resultSet = pstmt.executeQuery();
        while (resultSet.next()) {
            map.put("picture", resultSet.getBlob(1));
        }
        if (resultSet != null)
            resultSet.close();
        if (pstmt != null)
            pstmt.close();
        if (conn != null) {
            conn.close();
        }
        return map;
    }

    public Product getProductById(int id) {
        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
        String sql = "select id, productType, productBarcode, productCode, productSizeIn, productSizeOut," +
                "productSize, productMaterial, outframeCode, creatTime, unitPrice, productNumber," +
                "productVolume, productPackage, productDescribe, sizeInUnit, sizeOutUnit, sizeUnit, priceUnit from productinfo where id = ?";
        List<Object> params = new ArrayList<Object>();
        params.add(id);
        Product product = new Product();
        try {
            product = jdbcUtils.findSimpleRefResult(sql, params, Product.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        jdbcUtils.releaseConn();
        return product;
    }


    public void update(Product product) throws SQLException {
        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
        String sql = "";
        List<Object> params = new ArrayList<Object>();
        params.add(product.getProductType());
        params.add(product.getProductBarcode());
        params.add(product.getProductCode());
        params.add(product.getProductSizeIn());
        params.add(product.getProductSizeOut());
        params.add(product.getProductSize());
        params.add(product.getProductMaterial());
        params.add(product.getOutframeCode());
        params.add(product.getCreatTime());
        params.add(product.getUnitPrice());
        params.add(product.getProductNumber());
        params.add(product.getProductVolume());
        params.add(product.getProductPackage());
        params.add(product.getProductDescribe());
        params.add(product.getSizeInUnit());
        params.add(product.getSizeOutUnit());
        params.add(product.getSizeUnit());
        params.add(product.getPriceUnit());
        InputStream inputStream = product.getProductPicture();
        if (inputStream == null) {
            sql = "UPDATE productinfo SET productType=?, productBarcode=?, " +
                    "productCode=?, productSizeIn=?, productSizeOut=?, productSize=?, productMaterial=?," +
                    " outframeCode=?, creatTime=?, unitPrice=?, productNumber=?, productVolume=?, productPackage=?," +
                    " productDescribe=?, sizeInUnit=?, sizeOutUnit=?, sizeUnit=?, priceUnit=? WHERE Id=?";
        } else {
            sql = "UPDATE productinfo SET productType=?, productBarcode=?, " +
                    "productCode=?, productSizeIn=?, productSizeOut=?, productSize=?, productMaterial=?," +
                    " outframeCode=?, creatTime=?, unitPrice=?, productNumber=?, productVolume=?, productPackage=?," +
                    " productDescribe=?, sizeInUnit=?, sizeOutUnit=?, sizeUnit=?, priceUnit=?, productPicture=? WHERE Id=?";
            params.add(inputStream);
        }

        params.add(product.getId());
        jdbcUtils.updateByPreparedStatement(sql, params);
        jdbcUtils.releaseConn();
    }

    public static long getDayBegin() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 001);
        return new Timestamp(cal.getTimeInMillis()).getTime() / 1000;
    }
}
