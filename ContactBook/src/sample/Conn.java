package sample;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 This class contains methods for interacting with the database: establishing a connection,
 creating a table (if the old one is suddenly deleted) adding new data, updating existing data,
 deleting data from the table.
 */
public class Conn {
    private static Connection conn;
    private static Statement statmt;
    private static ResultSet resSet;
    private static PreparedStatement preparedStatement;
    private static String language = "ru"; // Change the value of this field to "en" so that all outputs of methods of this class to the console are in English.

    // The method for establishing a connection to the database.
    public static void TryConn() throws ClassNotFoundException, IOException {
        conn = null;

        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:contacts.db");
            System.out.println(Translate.translate("successfully-connected", language));
        } catch (SQLException e) {
            System.out.println(Translate.translate("unsuccessfully-connected", language));
        } catch (IOException ignored) { /*TODO you can add text here for a translation error.*/}

    }

    // This method is responsible for creating a table inside the database.
    public static void createTable() throws IOException {
        try {
            statmt = conn.createStatement();
            statmt.execute("CREATE TABLE if not exists 'contacts' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' TEXT, 'telephone' TEXT, 'email' TEXT, 'tglink' TEXT, 'vklink' TEXT);");

            System.out.println(Translate.translate("table-created", language));
        }
        catch (SQLException e) {
            System.out.println(Translate.translate("table-not-created", language));
        } catch (IOException ignored) { /*TODO you can add text here for a translation error.*/ }
    }

    // This method is needed to get contact data from the database
    public static ArrayList<Row> ReadDB() {
        try {
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

            System.out.println(Translate.translate("successfully-read", language));
            return row_list;
        }
        catch (SQLException | IOException e) {
            try {
                System.out.println(Translate.translate("unsuccessfully-read", language));
            } catch (IOException ignored) { /*TODO you can add text here for a translation error.*/ }
            return null;
        }
    }

    // This method is responsible for adding a new contact to the database table.
    public static void WriteDB(String name, String telephone, String email, String tglink, String vklink) {
        try {
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
            System.out.println(Translate.translate("successfully-write", language));
        }
        catch (SQLException e) {
            try {
                System.out.println(Translate.translate("unsuccessfully-write", language));
            } catch (IOException ignored) { /*TODO you can add text here for a translation error.*/ }
        } catch (IOException ignored) { /*TODO you can add text here for a translation error.*/ }
    }

    // This method is responsible for updating the data already existing in the table.
    public static void UpdateDB(int id, String name, String telephone, String email, String tglink, String vklink) {
        try {
            String insert = "UPDATE contacts\n" +
                    "   SET name = '" + name + "',\n" +
                    "       telephone = '" + telephone +"',\n" +
                    "       email = '" + email + "',\n" +
                    "       tglink = '" + tglink + "',\n" +
                    "       vklink = '" + vklink + "'"+
                    "WHERE id = " + id;
            preparedStatement = conn.prepareStatement(insert);

            preparedStatement.executeUpdate();
            System.out.println(Translate.translate("successfully-update", language));
        }
        catch (SQLException e) {
            try {
                System.out.println(Translate.translate("unsuccessfully-update", language));
            } catch (IOException ignored) { /*TODO you can add text here for a translation error.*/ }
        } catch (IOException ignored) { /*TODO you can add text here for a translation error.*/ }
    }

    // This method is responsible for deleting contacts from the database.
    public static void DeleteFromDB(String id) {
        try {
            String insert = "delete from contacts\n" + "WHERE id=" + id;
            statmt = conn.createStatement();
            statmt.execute(insert);

            System.out.println(Translate.translate("successfully-delete", language));
        }
        catch (SQLException e) {
            try {
                System.out.println(Translate.translate("unsuccessfully-delete", language));
            } catch (IOException ignored) { /*TODO you can add text here for a translation error.*/ }
        } catch (IOException ignored) { /*TODO you can add text here for a translation error.*/ }
    }
}
