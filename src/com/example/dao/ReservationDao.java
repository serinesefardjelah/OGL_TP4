package com.example.dao;

import com.example.entity.ParkingPlace;
import com.example.entity.Reservation;
import com.example.entity.ReservationStatus;
import com.example.entity.User;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservationDao {
    private static final Logger logger = Logger.getLogger(ReservationDao.class.getName());

    private Connection conn;

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void insertReservation(Reservation reservation) {
        // we used try-with-resources bloc to  ensures that the resources are automatically closed when the try block is exited.
        try (PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO reservations (place_id, user_id, status, start_time, end_time) VALUES (?, ?, ?, ?, ?);")) {
            pstmt.setInt(1, reservation.getParkingPlace().getIdPlace());
            pstmt.setInt(2, reservation.getUser().getUserId());
            pstmt.setString(3, reservation.getStatus().name());
            java.sql.Date startTime = new java.sql.Date(reservation.getStartTime().getTime());
            java.sql.Date endTime = new java.sql.Date(reservation.getEndTime().getTime());
            pstmt.setDate(4, startTime);
            pstmt.setDate(5, endTime);
            pstmt.executeUpdate();
        } catch (SQLException se) {
            logger.log(Level.SEVERE,"Insertion SQLException",se);
        }
    }

    public Reservation getReservationById(int idReservation) {
        Reservation reservation = null;
        // using ? and setInt for params binding
        String sql = "SELECT * FROM reservations WHERE id_reservation = ?";
        // we used try-with-resources bloc to  ensures that the resources are automatically closed when the try block is exited.
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idReservation);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    reservation = new Reservation();
                    reservation.setReservationId(rs.getInt("id_reservation"));
                    ParkingPlace parkingPlace = new ParkingPlace();
                    parkingPlace.setIdPlace(rs.getInt("place_id"));
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    reservation.setParkingPlace(parkingPlace);
                    reservation.setUser(user);
                    reservation.setStatus(ReservationStatus.valueOf(rs.getString("status")));
                    reservation.setStartTime(rs.getDate("start_time"));
                    reservation.setEndTime(rs.getDate("end_time"));
                }
            }
        } catch (SQLException se) {
            logger.log(Level.SEVERE,"GetUserByID SQLException",se);
        } catch (Exception e) {
            logger.log(Level.SEVERE,"GetUserByID Exception",e);
        }
        return reservation;
    }

    public void updateReservationStatus(int idReservation, ReservationStatus reservationStatus) {
        // we used try-with-resources bloc to  ensures that the resources are automatically closed when the try block is exited.
        try (PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE reservations SET status=? WHERE id_reservation=?")) {
            pstmt.setString(1, reservationStatus.name());
            pstmt.setInt(2, idReservation);
            pstmt.executeUpdate();
        } catch (SQLException se) {
            logger.log(Level.SEVERE,"updateReservationStatus SQLException",se);
        } catch (Exception e) {
            logger.log(Level.SEVERE,"updateReservationStatus Exception",e);
        }
    }
}
