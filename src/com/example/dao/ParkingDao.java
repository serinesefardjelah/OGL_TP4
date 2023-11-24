package com.example.dao;

import com.example.entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParkingDao {
    private static final Logger logger = Logger.getLogger(ParkingDao.class.getName());
    private Connection conn;


    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }



    public void insertParking(Parking parking) {

        PreparedStatement pstmt = null;
        try {

            String sql = "INSERT INTO parkings(name,address,capacity) VALUES (?,?,?); " ;
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, parking.getName());
            pstmt.setString(2, parking.getAddress());
            pstmt.setInt(3, parking.getCapacity());
            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException se) {
            logger.log(Level.SEVERE,"SQLException",se);
        }  finally {
            try {
                if (pstmt != null) pstmt.close();

            } catch (SQLException se2) {

            }
        }

    }




}
