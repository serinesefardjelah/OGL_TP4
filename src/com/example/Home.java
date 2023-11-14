package com.example;

import com.example.dao.*;
import com.example.entity.*;
import com.example.service.ReservationManager;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Home {

    public static final String USER = "root";
    public static final String PASSWORD = "root";
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/citiesdb";

    public static void main(String args[]) throws ParseException {
        DatabaseConnection db = new DatabaseConnection(USER, PASSWORD, JDBC_DRIVER, DB_URL);
        Connection connection = db.connect();
        try {
            if (args.length > 0) {

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
                // insert Reservation
                ReservationDao reservationDao = new ReservationDao();
                reservationDao.setConn(connection);
                Reservation reservation = new Reservation();
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
                reservation.setStatus(ReservationStatus.PENDING);
                reservation.setUser(user);
                reservation.setParkingPlace(parkingPlace);
                reservation.setStartTime(dateFormat.parse("22-10-2023 10:00"));
                reservation.setEndTime(dateFormat.parse("22-10-2023 11:45"));
                reservationDao.insertReservation(reservation);
            }
        }
        catch (NumberFormatException e) {
            System.err.println("Arguments" + args[0] + args[2] + " must be an integer.");
            System.exit(1);
        }
    }
}
