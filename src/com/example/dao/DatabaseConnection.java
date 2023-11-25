package com.example.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {
    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());
    private String user;
    private String password;
    private String jdbcDriver;
    private String dbUrl;


    public DatabaseConnection(String user, String password, String jdbcDriver, String dbUrl) {
        this.user = user;
        this.password = password;
        this.jdbcDriver = jdbcDriver;
        this.dbUrl = dbUrl;
    }

    public  Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbUrl, user, password);
            Class.forName(jdbcDriver);
        } catch (SQLException e) {
            logger.log(Level.SEVERE,"Connection SQLException",e);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"ClassNotFoundException",e);
        }
        return conn;
    }

    public  void disconnect(Connection conn ) {
        try {
            conn.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE,"Disconnection SQLException",e);
        }
    }

    public void createDb(Connection conn) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(Scripts.sql);
            stmt.close();
        } catch(SQLException se) {
            logger.log(Level.SEVERE,"Db creation SQLException",se);
        } catch(Exception e) {
            logger.log(Level.SEVERE,"Exception",e);
        } finally {
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
                logger.log(Level.SEVERE,"other SQLException",se2);
            }
        }
    }

}
