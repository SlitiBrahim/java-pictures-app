package com.supinfo.suppictures.dao;

import java.sql.*;

class ConnexionDB {
    static Connection Connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/suppicturesdb";
            String user = "root";
            String passwd = "";

            return DriverManager.getConnection(url, user, passwd);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
