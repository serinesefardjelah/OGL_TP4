package com.example.dao;

import com.example.entity.Parking;
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
        try (PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO parkings(name,address,capacity) VALUES (?,?,?)")) {
            pstmt.setString(1, parking.getName());
            pstmt.setString(2, parking.getAddress());
            pstmt.setInt(3, parking.getCapacity());
            pstmt.executeUpdate();
        } catch (SQLException se) {
            logger.log(Level.SEVERE, "Insertion SQLException", se);
        }
    }
}
