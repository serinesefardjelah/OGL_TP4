package com.example.dao;


import com.example.config.AppConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {
    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());
    private String USER;
    private String PASSWORD;
    private String JDBC_DRIVER;
    private String DB_URL;


    public DatabaseConnection(String USER, String PASSWORD, String JDBC_DRIVER, String DB_URL) {
        this.USER = USER;
        this.PASSWORD = PASSWORD;
        this.JDBC_DRIVER = JDBC_DRIVER;
        this.DB_URL = DB_URL;
    }

    public  Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Class.forName(JDBC_DRIVER);
        } catch (SQLException e) {
            logger.log(Level.SEVERE,"SQLException",e);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"ClassNotFoundException",e);
        }
        return conn;
    }

    public  void disconnect(Connection conn ) {
        try {
            conn.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE,"SQLException",e);
        }
    }

    public void createDb(Connection conn) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(Scripts.sql);
            stmt.close();
        } catch(SQLException se) {
            logger.log(Level.SEVERE,"SQLException",se);
        } catch(Exception e) {
            logger.log(Level.SEVERE,"Exception",e);
        } finally {
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            }
        }
    }

}
