package db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static Connection conn = null;

    public static Connection getconnection(){
        if (conn == null){
            try {
                Properties props = loadProperties();
                String url = props.getProperty("dburl");

                conn = DriverManager.getConnection(url,props);

                if (conn != null){
                    System.out.println("Connection sucess!");
                }
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return conn;
    }

    private static Properties loadProperties(){
        try (FileInputStream fs = new FileInputStream("db.properties")){

            Properties props = new Properties();
            props.load(fs);
            return props;

        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }

    public static void closeConnection(){
        if (conn != null){
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeStatement(Statement st){

        if (st != null){
            try {
                st.close();
                st = null;
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs){

        if (rs != null){
            try {
                rs.close();

                rs = null;
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}
