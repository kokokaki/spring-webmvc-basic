package com.spring.mvc.web;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DataBaseConnectTest {

    private String userId = "hr";
    private String userPw = "hr";
    private String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private String driverName = "oracle.jdbc.driver.OracleDriver";

    @Test
    void connectTest() {
        try {
            Class.forName(driverName);
            Connection conn = DriverManager.getConnection(url, userId, userPw);

            String sql = "SELECT first_name FROM employees";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String firstName = rs.getString("first_name");
                System.out.println("firstName = " + firstName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
