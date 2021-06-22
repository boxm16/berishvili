package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class DataBaseConnection {

    public static Connection getInitConnection() throws SQLException {
        //this connection is only for creating schema
        Connection connection = null;
        try {

            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306?useSSL=false";
            String username = "root";
            String password = "athina2004";
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Initial connection created at: " + LocalDateTime.now());
        return connection;
    }

    public static Connection getConnection() throws SQLException {
        //this is regular connection 
        Connection connection = null;
        try {

            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/berishvili_db?useSSL=false";
            String username = "root";
            String password = "athina2004";
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("Initial connection created at: " + LocalDateTime.now());
        return connection;
    }
}
