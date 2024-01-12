package com.OOP.plmares.database;
import java.sql.Connection;
import java.sql.DriverManager;
public class ConnectDB {
    public Connection Connect(){
        Connection con = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/plmaresdb";
        String uname = "root";
        String password = "password";

        try{
            Class.forName(driver);
            con = DriverManager.getConnection(url, uname, password);
            System.out.println("Connection established successfully!");
        } catch (Exception e){
            System.out.println(e);
        }
        return con;
    }
}
