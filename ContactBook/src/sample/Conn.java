package sample;

import java.sql.*;
import java.util.ArrayList;

public class Conn {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;
    public static PreparedStatement preparedStatement;

    public static void TryConn() throws ClassNotFoundException, SQLException
    {
        conn = null;

        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:contacts.db");
            System.out.println("Успешно подключились");
        } catch (SQLException e) {
            System.out.println("Возникла ошибка во время подключения");
        }

    }

    public static void createTable() throws SQLException {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'contacts' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' TEXT, 'telephone' TEXT, 'email' TEXT);");

        System.out.println("Таблица создана или уже существует");
    }

    public static ArrayList<Row> ReadDB() throws SQLException
    {
        resSet = statmt.executeQuery("SELECT * FROM contacts");

        ArrayList<Row> row_list = new ArrayList<>();
        while(resSet.next())
        {
            int id = resSet.getInt("id");
            String name = resSet.getString("name");
            String telephone = resSet.getString("telephone");
            String email = resSet.getString("email");
            Row row = new Row(id, name, telephone, email);
            row_list.add(row);
        }

        return row_list;
    }

    public static void WriteDB(String name, String telephone, String email) throws SQLException
    {
        String insert = "INSERT INTO " + "contacts" + "(" +
                "name" + "," + "telephone" + "," + "email" + ")" +
                "VALUES(?,?,?)";

        preparedStatement = conn.prepareStatement(insert);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, telephone);
        preparedStatement.setString(3, email);

        preparedStatement.executeUpdate();
    }

    public static void DeleteFromDB(String id) throws SQLException
    {
        String insert = "delete from contacts\n" + "WHERE id=" + id;
        statmt = conn.createStatement();
        statmt.execute(insert);

        System.out.println("удалил данные для пользователя с id = " + id);
    }
}
