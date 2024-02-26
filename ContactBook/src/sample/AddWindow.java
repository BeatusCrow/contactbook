package sample;

import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.SQLException;

public class AddWindow {
    private static Pane pane;
    private static Scene scene;

    public static Scene createAddWindow(){
        pane = new Pane();
        createButton();
        createFields();
        scene = new Scene(pane,240,170);

        return scene;
    }

    private static void createFields() {
        TextField name = new TextField();
        name.setId("nameField");
        TextField telephone = new TextField();
        telephone.setId("telephoneNumberField");
        TextField email = new TextField();
        email.setId("emailAddressField");

        name.setPromptText("name");
        name.setAlignment(Pos.CENTER);
        telephone.setPromptText("telephone number");
        telephone.setAlignment(Pos.CENTER);
        email.setPromptText("email address");
        email.setAlignment(Pos.CENTER);

        name.setLayoutX(44);
        name.setLayoutY(14);
        telephone.setLayoutX(44);
        telephone.setLayoutY(53);
        email.setLayoutX(44);
        email.setLayoutY(92);

        pane.getChildren().addAll(name, telephone, email);
    }

    private static void createButton() {
        Button save = new Button();
        save.setOnMouseClicked(AddWindow::onClickSave);
        save.setText("Save");
        save.setPrefWidth(80);
        save.setPrefHeight(25);
        save.setLayoutX(78);
        save.setLayoutY(130);

        pane.getChildren().add(save);
    }

    private static void onClickSave(Event e) {
        TextField nameField = (TextField) scene.lookup("#nameField");
        String text_nameField = nameField.getText();
        TextField telephoneNumberField = (TextField) scene.lookup("#telephoneNumberField");
        String text_telephoneNumberField = telephoneNumberField.getText();
        TextField emailAddressField = (TextField) scene.lookup("#emailAddressField");
        String text_emailAddressField = emailAddressField.getText();

        if(!text_nameField.isEmpty()) {
            try {
                Conn.WriteDB(text_nameField, text_telephoneNumberField, text_emailAddressField);
                Window.update();
                System.out.println("записал в таблицу данные");
            } catch (SQLException ex) {
                System.out.println(ex);
            }
            Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
            stage.close();
        }
    }
}
