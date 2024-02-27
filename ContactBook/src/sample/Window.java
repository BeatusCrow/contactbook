package sample;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 This class contains methods for working with the main application window.
 */
public class Window extends Application {
    private static Pane pane; // These fields are necessary for the convenience of working with window elements
    private static Scene scene;
    private static ScrollBar scrollBar;

    private static ArrayList<Pane> row_list = new ArrayList<>(); // This list is needed to display the contacts in the database

    private static int count_row = 0; // These 2 fields are needed for the scrollBar to work properly
    private static double old_value_scroll = 0.0;

    @Override
    public void start(Stage stage) throws Exception {
        // We are creating the basis of our window to place elements on it
        pane = new Pane();
        createAddContactButton();
        createSearchField();
        createScrollBar();
        // We create a scene and set such parameters based on the calculations that I made in my head
        scene = new Scene(pane, 574,415);
        // We perform standard actions with the stage. I don't want to remove the title bar :\
        // TODO Remove the title bar
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Сontact book");
        stage.show();
        update(); // This method is needed to display the items in the database
    }

    /* The start method is not called directly from the only method of the Main class,
    so that there are no problems with the static modifier. */
    public static void createWindow(String[] args) {
        launch(args);
    }

     // This method is needed to create a search field.
     // TODO add functionality to this piece
    private void createSearchField() {
        TextField searchField = new TextField();
        searchField.setPromptText("search");
        searchField.setLayoutX(14);
        searchField.setLayoutY(14);

        pane.getChildren().add(searchField);
    }

    // A method for creating a button that is responsible for adding new contacts
    private void createAddContactButton() {
        Button addContactButton = new Button();
        addContactButton.setOnMouseClicked(e -> { // TODO put it in a separate method
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Adding");
            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(AddWindow.createAddWindow());
            dialogStage.show();
        });
        addContactButton.setText("Add contact");
        addContactButton.setLayoutX(178);
        addContactButton.setLayoutY(14);

        pane.getChildren().add(addContactButton);
    }

    // The method responsible for creating the scrollBar and for all its logic
    private void createScrollBar() {
        scrollBar = new ScrollBar();
        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollBar.valueProperty().addListener(observable -> { // TODO put it in a separate method
            double new_value_scroll = scrollBar.getValue();
            double d_value_scroll = new_value_scroll - old_value_scroll;
            old_value_scroll = new_value_scroll;
            for(Pane row : row_list){
                row.setLayoutY(row.getLayoutY() - d_value_scroll);
                row.setVisible(!(row.getLayoutY() < 54)); // Logically, less than 55 should work, but for some reason it doesn't work that way :(
            }                                             // I checked rounding and stuff, but it didn't bring success
        });
        scrollBar.setMax(0);
        scrollBar.setValue(0);
        scrollBar.setVisibleAmount(0);
        scrollBar.setPrefWidth(14);
        scrollBar.setPrefHeight(415);
        scrollBar.setLayoutX(560);

        pane.getChildren().addAll(scrollBar);
    }

    // The method responsible for creating objects on the Pane that contain contact information from the database
    public static void createRow(Row newrow) {
        Pane row = newrow.createBodyRow(newrow.getId(), newrow.getName(),
                                            newrow.getTelephoneNumber(), newrow.getEmailAddress(),
                                        newrow.getTgLink(), newrow.getVkLink());
        row.setLayoutX(11);
        if(row_list.isEmpty()) {
            row.setLayoutY(55-scrollBar.getValue());  // The first displayed element should disappear after crossing the band at 55 along the OY axis.
            row.setVisible(!(row.getLayoutY() < 54)); // Otherwise, it will run into the search field
        }
        else {
            double max_y = row_list.get(row_list.size() - 1).getLayoutY();
            row.setLayoutY(max_y + 72);
            row.setVisible(!(row.getLayoutY() < 54));
        }
        count_row++;

        // After creating the elements, we count them to set the desired scrollBar values
        if(count_row > 5) {
            scrollBar.setMax(scrollBar.getMax() + 72);
            scrollBar.setVisibleAmount(scrollBar.getMax()/count_row*5);
        }

        // Adding items to the list to know which ones to access to remove them from the Pane
        row_list.add(row);
        pane.getChildren().add(row);
    }

    // This method is needed to update the displayed elements on the Pane
    public static void update() throws SQLException {
        ArrayList<Row> rowList = Conn.ReadDB(); // Getting a list of items from the database

        /*
        We compare the values of the lists that contain information about the current items and those that need to be displayed.
        If the list has become smaller, it means that some element has been deleted, and we need to change the current scrollBar value.
        If this is not done, then when deleting the last element (provided that we scrolled the strip down as much as possible)
        we will remain at the same screen location as if it had not been deleted, but at the same time the ability to scroll will decrease
        (due to setting the maximum scrollBar value in the createRow method). Because of this, a situation may arise when the elements begin
        to move out of their positions.
         */
        if(row_list.size() > rowList.size()) {
            if(count_row > 5) {
                scrollBar.setValue(scrollBar.getMax() - (row_list.size() - rowList.size())*72);
            }
        }

        // Deleting all currently displayed items
        for(Pane row: row_list) {
            pane.getChildren().remove(row);
            count_row--;
        }

        scrollBar.setMax(0); // Setting the values to 0 to recalculate it in the createRow method.
        scrollBar.setVisibleAmount(0); // And here...
        row_list.clear(); // We clean the list for further filling with relevant contacts

        for(Row row: rowList) { // We go through the list of contacts from the database and create the appropriate objects for them on the Pane
            createRow(row);
        }
    }

}
