package UserClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    final static String url = "jdbc:mysql://localhost:3306/db" ;
    final static String user = "root" ;
    final static String password = "********" ;

    public static Connection getConnection() {
        Connection myConn = null;
        try {
            myConn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("Error");
        }
        return myConn;
    }
}
