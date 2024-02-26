package sample;

import java.io.IOException;
import java.sql.SQLException;

/**
 In this class, we call the only method to check whether it is possible to connect to the database and
 to create a table inside the database, if there is no such table. Also, the method inside this class
 calls another method of the Window class to create a window for our application.
 */
public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        Conn.TryConn();
        Conn.createTable();
        Window.createWindow(args);
    }
}
