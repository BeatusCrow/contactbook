module ContactBook {
    requires javafx.controls;
    requires javafx.graphics;
    requires java.sql.rowset;
    requires java.desktop;
    requires sqlite.jdbc;

    opens sample;
}