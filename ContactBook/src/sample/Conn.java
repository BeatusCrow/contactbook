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
        statmt.execute("CREATE TABLE if not exists 'contacts' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' TEXT, 'telephone' TEXT, 'email' TEXT, 'tglink' TEXT, 'vklink' TEXT);");

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
            String tgLink = resSet.getString("tglink");
            String vkLing = resSet.getString("vklink");
            Row row = new Row(id, name, telephone, email, tgLink, vkLing);
            row_list.add(row);
        }

        return row_list;
    }

    public static void WriteDB(String name, String telephone, String email, String tglink, String vklink) throws SQLException
    {
        String insert = "INSERT INTO " + "contacts" + "(" +
                "name" + "," + "telephone" + "," + "email" + "," + "tglink"+ "," + "vklink" +")" +
                "VALUES(?,?,?,?,?)";

        preparedStatement = conn.prepareStatement(insert);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, telephone);
        preparedStatement.setString(3, email);
        preparedStatement.setString(4, tglink);
        preparedStatement.setString(5, vklink);

        preparedStatement.executeUpdate();
    }

    public static void UpdateDB(int id, String name, String telephone, String email, String tglink, String vklink) throws SQLException
    {
        String insert = "UPDATE contacts\n" +
                        "   SET name = '" + name + "',\n" +
                        "       telephone = '" + telephone +"',\n" +
                        "       email = '" + email + "',\n" +
                        "       tglink = '" + tglink + "',\n" +
                        "       vklink = '" + vklink + "'"+
                        "WHERE id = " + id;

        preparedStatement = conn.prepareStatement(insert);

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
