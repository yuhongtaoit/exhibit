package edu.zstu.exhibit.util;

import edu.zstu.exhibit.domain.Directory;
import edu.zstu.exhibit.domain.Product;
import edu.zstu.exhibit.domain.User;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * Created by aning on 16/6/1.
 */
public class JdbcUtils {
    private Connection connection;
    private PreparedStatement pstmt;
    private ResultSet resultSet;
    /**
     * 获得数据库的连接
     * @return
     */
    public Connection getConnection() {
        try {
            Properties properties = new Properties();
            try {
                properties.load(JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties"));
            } catch (IOException e) {
                System.out.println("未找到配置文件");
            }
            String driver = properties.getProperty("driver");
            String url = properties.getProperty("url");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }


    /**
     * 增加、删除、改
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public boolean updateByPreparedStatement(String sql, List<Object> params)throws SQLException{
        boolean flag = false;
        int result = -1;
        pstmt = connection.prepareStatement(sql);
        int index = 1;
        if(params != null && !params.isEmpty()){
            for(int i=0; i<params.size(); i++){
                pstmt.setObject(index++, params.get(i));
            }
        }
        result = pstmt.executeUpdate();
        flag = result > 0 ? true : false;
        return flag;
    }

    /**
     * 查询单条记录
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public Map<String, Object> findSimpleResult(String sql, List<Object> params) throws SQLException{
        Map<String, Object> map = new HashMap<String, Object>();
        int index  = 1;
        pstmt = connection.prepareStatement(sql);
        if(params != null && !params.isEmpty()){
            for(int i=0; i<params.size(); i++){
                pstmt.setObject(index++, params.get(i));
            }
        }
        resultSet = pstmt.executeQuery();//返回查询结果
        ResultSetMetaData metaData = resultSet.getMetaData();
        int col_len = metaData.getColumnCount();
        while(resultSet.next()){
            for(int i=0; i<col_len; i++ ){
                String cols_name = metaData.getColumnName(i+1);
                Object cols_value = resultSet.getObject(cols_name);
                if(cols_value == null){
                    cols_value = "";
                }
                map.put(cols_name, cols_value);
            }
        }
        return map;
    }

    /**查询多条记录
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public List<Map<String, Object>> findModeResult(String sql, List<Object> params) throws SQLException{
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int index = 1;
        pstmt = connection.prepareStatement(sql);
        if(params != null && !params.isEmpty()){
            for(int i = 0; i<params.size(); i++){
                pstmt.setObject(index++, params.get(i));
            }
        }
        resultSet = pstmt.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();
        while(resultSet.next()){
            Map<String, Object> map = new HashMap<String, Object>();
            for(int i=0; i<cols_len; i++){
                String cols_name = metaData.getColumnName(i+1);
                Object cols_value = resultSet.getObject(cols_name);
                if(cols_value == null){
                    cols_value = "";
                }
                map.put(cols_name, cols_value);
            }
            list.add(map);
        }

        return list;
    }

    /**通过反射机制查询单条记录
     * @param sql
     * @param params
     * @param cls
     * @return
     * @throws Exception
     */
    public <T> T findSimpleRefResult(String sql, List<Object> params,
                                     Class<T> cls )throws Exception{
        T resultObject = null;
        int index = 1;
        pstmt = connection.prepareStatement(sql);
        if(params != null && !params.isEmpty()){
            for(int i = 0; i<params.size(); i++){
                pstmt.setObject(index++, params.get(i));
            }
        }
        resultSet = pstmt.executeQuery();
        ResultSetMetaData metaData  = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();
        while(resultSet.next()){
            //通过反射机制创建一个实例
            resultObject = cls.newInstance();
            for(int i = 0; i<cols_len; i++){
                String cols_name = metaData.getColumnName(i+1);
                Object cols_value = resultSet.getObject(cols_name);
                if(cols_value == null){
                    cols_value = "";
                }
                Field field = cls.getDeclaredField(cols_name);
                field.setAccessible(true); //打开javabean的访问权限
                field.set(resultObject, cols_value);
            }
        }
        return resultObject;

    }

    /**
     * 查询全部记录
     */
    public <T> List<T> findAll(String sql , Class<T> cls) throws Exception {
        T resultObject = null;
        List<T> list = new ArrayList<T>();
        pstmt = connection.prepareStatement(sql);
        resultSet = pstmt.executeQuery();
        ResultSetMetaData metaData  = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();
        while (resultSet.next()) {
            resultObject = cls.newInstance();
            for(int i = 0; i<cols_len; i++){
                String cols_name = metaData.getColumnName(i+1);
                Object cols_value = resultSet.getObject(cols_name);

                Field field = cls.getDeclaredField(cols_name);
                field.setAccessible(true); //打开javabean的访问权限
                field.set(resultObject, cols_value);
            }
            list.add(resultObject);
        }
        return list;
    }

    /**通过反射机制查询多条记录
     * @param sql
     * @param params
     * @param cls
     * @return
     * @throws Exception
     */
    public <T> List<T> findMoreRefResult(String sql, List<Object> params,
                                         Class<T> cls )throws Exception {
        List<T> list = new ArrayList<T>();
        int index = 1;
        pstmt = connection.prepareStatement(sql);
        if(params != null && !params.isEmpty()){
            for(int i = 0; i<params.size(); i++){
                pstmt.setObject(index++, params.get(i));
            }
        }
        resultSet = pstmt.executeQuery();
        ResultSetMetaData metaData  = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();
        while(resultSet.next()){
            //通过反射机制创建一个实例
            T resultObject = cls.newInstance();
            for(int i = 0; i<cols_len; i++){
                String cols_name = metaData.getColumnName(i+1);
                Object cols_value = resultSet.getObject(cols_name);
                if(cols_value == null){
                    cols_value = "";
                }
                Field field = cls.getDeclaredField(cols_name);
                field.setAccessible(true); //打开javabean的访问权限
                field.set(resultObject, cols_value);
            }
            list.add(resultObject);
        }
        return list;
    }

    public Map<String, Integer> getDiffCustomName(String sql, List<Object> params) {
        Map<String, Integer> map = new HashMap<>();
        try {
            pstmt = connection.prepareStatement(sql);
            if(params != null && !params.isEmpty()){
                pstmt.setString(1, params.get(0).toString());
                pstmt.setLong(2, (Long) params.get(1));
            }
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                map.put(resultSet.getString(1), resultSet.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 释放数据库连接
     */
    public void releaseConn(){
        if(resultSet != null){
            try{
                resultSet.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();

        /*******************增*********************/
      //  String sql = "insert into productinfo (username, pswd) values (?, ?)";
//        String sql = "INSERT INTO userinfo (UserName,LogInName) values(?,?)";
//        List<Object> params = new ArrayList<Object>();
//        params.add("xi1xi");
//        params.add("hah1a");
//        try {
//            boolean flag = jdbcUtils.updateByPreparedStatement(sql, params);
//            System.out.println(flag);
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }


        /*******************删*********************/
        //删除名字为张三的记录
//        String sql = "delete from productinfo where id = ?";
//        List<Object> params = new ArrayList<Object>();
//        params.add(13);
//        boolean flag = jdbcUtils.updateByPreparedStatement(sql, params);

        /*******************改*********************/
        //将名字为李四的密码改了
        String sql = "update productinfo set pswd = ? where username = ? ";
        String sql1 = "UPDATE exhibitdb.productinfo SET productType=?, productBarcode=?, " +
                "productCode=?, productSizeIn=?, productSizeOut=?, productSize=?, productMaterial=?," +
                " outframeCode=?, creatTime=?, unitPrice=?, productNumber=?, productVolume=?, productPackage=?, productDescribe=? WHERE Id=?";
        List<Object> params = new ArrayList<Object>();
        params.add("lisi88888");
        params.add("李四");
        boolean flag = jdbcUtils.updateByPreparedStatement(sql, params);
        System.out.println(flag);

        /*******************查*********************/
        //不利用反射查询多个记录
//        String sql2 = "select directoryName from directoryinfo where directoryType = ?";
//        List<Object> params = new ArrayList<Object>();
//        params.add(1);
//        List<Directory> moreRefResult = jdbcUtils.findMoreRefResult(sql2, params, Directory.class);
//        System.out.println(moreRefResult);

        //利用反射查询 单条记录
//        String sql = "select * from userinfo where username = ? ";
//        List<Object> params = new ArrayList<Object>();
//        params.add("李四");
//        UserInfo userInfo;
//        try {
//            userInfo = jdbcUtils.findSimpleRefResult(sql, params, UserInfo.class);
//            System.out.print(userInfo);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//         String sql = "select * from productinfo";
//        try {
//            List<Product> users = jdbcUtils.findAll(sql, Product.class);
//            for(Product u : users) {
//                System.out.println(u);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}
