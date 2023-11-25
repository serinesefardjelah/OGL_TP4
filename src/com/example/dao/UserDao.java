package com.example.dao;

import com.example.entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UserDao {
    private static final Logger logger = Logger.getLogger(UserDao.class.getName());

    private Connection conn;


    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }




    public void insertUser(User user) {

        PreparedStatement pstmt = null;

        try {
            String sql = "INSERT INTO users(name,email,phone) VALUES (?,?,?); " ;
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPhone());
            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException se) {
            logger.log(Level.SEVERE,"SQLException",se);
        }  finally {
            try {
                if (pstmt != null) pstmt.close();

            } catch (SQLException se2) {
                logger.log(Level.FINE,"Exception",se2);
            }
        }

    }



    public User getUserById(int userId) {
        User user = null;
        // try-with-resources bloc to  ensures that the pstmt is automatically closed when the try block is exited.
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Users WHERE user_id  = ?")) {
            pstmt.setInt(1, userId);
            //try-with-resources  to  ensures that the rs is automatically closed at the end of try-block
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                }
            }
        } catch (SQLException se) {
            logger.log(Level.SEVERE,"SQLException",se);
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Exception",e);
        }
        return user;
    }

}
