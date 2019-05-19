package com.hanium.AguideR;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectDB {
    // 싱글톤 패턴으로 사용 하기위 한 코드들
    private static ConnectDB instance = new ConnectDB();

    public static ConnectDB getInstance() {
        return instance;
    }

    public ConnectDB() {

    }


    private String jdbcUrl = "jdbc:mysql://localhost/aguider";
    private String dbId = "han";
    private String dbPass = "toor";
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private PreparedStatement pstmt2 = null;
    private ResultSet rs = null;
    private String sql = "";
    private String sql2 = "";
    String returns = "";
    String returns2 = "";



    public String registerdb(String userID, String userPassword, String userName, String userAge) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
            sql = "select userID from USER where userID=";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getString("userID").equals(userID)) { // 이미 아이디가 있는 경우
                    returns = "userID";
                }
            } else { // 입력한 아이디가 없는 경우
                sql2 = "insert into USER values(?,?,?,?)";
                pstmt2 = conn.prepareStatement(sql2);
                pstmt2.setString(1, userID);
                pstmt2.setString(2, userPassword);
                pstmt2.setString(3, userName);
                pstmt2.setString(4, userAge);
                pstmt2.executeUpdate();

                returns = "ok";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
            if (conn != null)try {conn.close();} catch (SQLException ex) {}
            if (pstmt2 != null)try {pstmt2.close();} catch (SQLException ex) {}
            if (rs != null)try {rs.close();} catch (SQLException ex) {}
        }
        return returns;
    }


    public String logindb(String userID, String userPassword) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
            sql = "select userID, userPassword from USER where userID=? and userPassword=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userID);
            pstmt.setString(2, userPassword);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getString("userID").equals(userID) && rs.getString("userPassword").equals(userPassword)) {
                    returns2 = "true";// 로그인 가능
                } else {
                    returns2 = "false"; // 로그인 실패
                }
            } else {
                returns2 = "noId"; // 아이디 또는 비밀번호 존재 X
            }

        } catch (Exception e) {

        } finally {if (rs != null)try {rs.close();} catch (SQLException ex) {}
            if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
            if (conn != null)try {conn.close();} catch (SQLException ex) {}
        }
        return returns2;
    }
}
