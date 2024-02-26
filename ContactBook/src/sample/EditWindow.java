package sample;

import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.SQLException;

public class EditWindow {
    private static Pane pane;
    private static Scene scene;

    public static Scene createAddWindow(int id, String name, String telephoneNumber, String emailAddress){
        pane = new Pane();
        createButton(id);
        createFields(name, telephoneNumber, emailAddress);
        scene = new Scene(pane,240,170);

        return scene;
    }

    private static void createFields(String name_, String telephoneNumber_, String emailAddress_) {
        TextField name = new TextField();
        name.setId("nameField");
        TextField telephone = new TextField();
        telephone.setId("telephoneNumberField");
        TextField email = new TextField();
        email.setId("emailAddressField");

        name.setPromptText("name");
        name.setAlignment(Pos.CENTER);
        name.setText(name_);
        telephone.setPromptText("telephone number");
        telephone.setAlignment(Pos.CENTER);
        telephone.setText(telephoneNumber_);
        email.setPromptText("email address");
        email.setAlignment(Pos.CENTER);
        email.setText(emailAddress_);

        name.setLayoutX(44);
        name.setLayoutY(14);
        telephone.setLayoutX(44);
        telephone.setLayoutY(53);
        email.setLayoutX(44);
        email.setLayoutY(92);

        pane.getChildren().addAll(name, telephone, email);
    }

    private static void createButton(int id) {
        Button save = new Button();
        save.setOnMouseClicked(EditWindow::onClickSave);
        save.setText("Save");
        save.setPrefWidth(80);
        save.setPrefHeight(25);
        save.setLayoutX(124);
        save.setLayoutY(131);

        Button delete = new Button();
        delete.setOnMouseClicked(e -> {
            try {
                onClickDelete(id, e);
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        });
        delete.setText("Delete");
        delete.setPrefWidth(80);
        delete.setPrefHeight(25);
        delete.setLayoutX(39);
        delete.setLayoutY(131);

        pane.getChildren().addAll(save, delete);
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

    private static void onClickDelete(int id, Event e) throws SQLException {
        String id_ = String.valueOf(id);
        Conn.DeleteFromDB(id_);
        Window.update();
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.close();
    }
}
