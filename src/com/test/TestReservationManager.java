package com.test;

import com.example.dao.*;
import com.example.entity.*;
import com.example.service.IParkingPlaceManager;
import com.example.service.ReservationManager;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestReservationManager {


    static DatabaseConnection databaseConnection;
    static Connection connection;




    // Integration Test Cancel Reservation
    @Test
    public void testIntegrationCancelReservation() throws ParseException {
        databaseConnection = new DatabaseConnection("root","","org.h2.Driver","jdbc:h2:mem:test");
        connection = databaseConnection.connect();
        databaseConnection.createDb(connection);
        // insert Parking
        Parking parking = new Parking();
        parking.setName("Parking 1");
        parking.setAddress("Alger");
        parking.setCapacity(30);
        ParkingDao parkingDao = new ParkingDao();
        parkingDao.setConn(connection);
        parkingDao.insertParking(parking);
        // insert Parking Place
        ParkingPlaceDao parkingPlaceDao = new ParkingPlaceDao();
        parkingPlaceDao.setConn(connection);
        ParkingPlace parkingPlace = new ParkingPlace();
        parkingPlace.setIdPlace(1);
        parking.setParkingId(1);
        parkingPlace.setParking(parking);
        parkingPlace.setPlaceName("F5");
        parkingPlace.setPlaceStatus(PlaceStatus.AVAILABLE);
        parkingPlaceDao.insertParkingPlace(parkingPlace);
        // insert user
        UserDao userDao = new UserDao();
        userDao.setConn(connection);
        User user = new User();
        user.setUserId(1);
        user.setName("Ali");
        user.setPhone("055555555");
        user.setEmail("ali@gmail.com");
        userDao.insertUser(user);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        // insert Reservation
        ReservationDao reservationDao = new ReservationDao();
        reservationDao.setConn(connection);
        Reservation reservation = new Reservation();
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setUser(user);
        reservation.setParkingPlace(parkingPlace);
        reservation.setStartTime(dateFormat.parse("22-10-2023 10:00"));
        reservation.setEndTime(dateFormat.parse("22-10-2023 11:45"));
        reservationDao.insertReservation(reservation);
        // get Reservation to delete
        ReservationManager reservationManager = new ReservationManager();
        reservationManager.setReservationDao(reservationDao);
        reservationManager.cancelReservation(1);
        // get canceled reservation
        Reservation reservationCanceled = reservationDao.getReservationById(1);
        assertEquals("CANCELLED",reservationCanceled.getStatus().name());

    }




}
