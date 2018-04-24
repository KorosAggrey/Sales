package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Aggrey on 10/29/2017.
 */
public class DbHelper {

    protected java.sql.Connection dbconnection;

    public Connection getConnection()
    {
        String host = "localhost";
        String port = "3306";
        String dbname = "adv";
        String user = "root";
        String password = "";
        final String ConnectionString = "jdbc:mysql://" + host + ":" + port + "/" + dbname;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.out.println("could not connect");
        }
        try {
            dbconnection = DriverManager.getConnection(ConnectionString, user, password);
            System.out.println("connected successfully");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return dbconnection;
    }
}
