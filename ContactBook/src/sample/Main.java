package sample;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Conn.TryConn();
        Conn.createTable();
        Window.createWindow(args);
    }
}
