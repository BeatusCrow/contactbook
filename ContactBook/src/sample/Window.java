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

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Window extends Application {
    private static Pane pane;
    private static Scene scene;
    private static ScrollBar scrollBar;

    private static ArrayList<Pane> row_list = new ArrayList<>();

    private static int count_row = 0;
    private static double old_value_scroll = 0.0;

    @Override
    public void start(Stage stage) throws Exception {
        pane = new Pane();
        createAddContactButton();
        createSearchField();
        createScrollBar();
        scene = new Scene(pane, 574,415);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Сontact book");
        stage.show();
        update();
    }

    public static void createWindow(String[] args) {
        launch(args);
    }

    private void createSearchField() {
        TextField searchField = new TextField();
        searchField.setPromptText("search");
        searchField.setLayoutX(14);
        searchField.setLayoutY(14);

        pane.getChildren().add(searchField);
    }

    private void createAddContactButton() {
        Button addContactButton = new Button();
        addContactButton.setOnMouseClicked(e -> {
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

    private void createScrollBar() {
        scrollBar = new ScrollBar();
        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollBar.valueProperty().addListener(observable -> {
            System.out.println("Новое значение -> " + scrollBar.getValue());
            double new_value_scroll = scrollBar.getValue();
            double d_value_scroll = new_value_scroll - old_value_scroll;
            old_value_scroll = new_value_scroll;
            for(Pane row : row_list){
                row.setLayoutY(row.getLayoutY() - d_value_scroll);
                row.setVisible(!(row.getLayoutY() < 54));
            }
        });
        scrollBar.setMax(0);
        scrollBar.setValue(0);
        scrollBar.setVisibleAmount(0);
        scrollBar.setPrefWidth(14);
        scrollBar.setPrefHeight(415);
        scrollBar.setLayoutX(560);

        pane.getChildren().addAll(scrollBar);
    }


    public static void createRow(Row newrow) {
        Pane row = newrow.createBodyRow(newrow.getId(), newrow.getName(),
                                        newrow.getTelephoneNumber(), newrow.getEmailAddress(),
                                        newrow.getTgLink(), newrow.getVkLink());
        row.setLayoutX(11);
        if(row_list.isEmpty()) {
            row.setLayoutY(55-scrollBar.getValue());
            row.setVisible(!(row.getLayoutY() < 54));
        }
        else {
            double max_y = row_list.get(row_list.size() - 1).getLayoutY();
            row.setLayoutY(max_y + 72);
            row.setVisible(!(row.getLayoutY() < 54));
        }
        count_row++;
        if(count_row > 5) {
            scrollBar.setMax(scrollBar.getMax() + 72);
            scrollBar.setVisibleAmount(scrollBar.getMax()/count_row*5);
        }

        row_list.add(row);
        pane.getChildren().add(row);
    }

    public static void update() throws SQLException {
        ArrayList<Row> rowList = Conn.ReadDB();
        if(row_list.size() > rowList.size()){
            if(count_row > 5){
                scrollBar.setValue(scrollBar.getMax() - (row_list.size() - rowList.size())*72);
            }
        }

        for(Pane row: row_list){
            pane.getChildren().remove(row);
            count_row--;
        }

        scrollBar.setMax(0);
        scrollBar.setVisibleAmount(0);
        row_list.clear();

        for(Row row: rowList) {
            createRow(row);
        }
    }

}
