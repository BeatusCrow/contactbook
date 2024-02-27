package sample;

import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.sql.SQLException;

public class AddWindow {
    private static Pane pane;
    private static Scene scene;

    public static Scene createAddWindow(){
        pane = new Pane();
        createButton();
        createFields();
        scene = new Scene(pane,240,245);

        return scene;
    }

    private static void createFields() {
        // --- name ---
        TextField name = new TextField();
        name.setId("nameField");
        // --- telephone ---
        TextField telephone = new TextField();
        telephone.setId("telephoneNumberField");
        // --- email ---
        TextField email = new TextField();
        email.setId("emailAddressField");
        // --- tgLink ---
        TextField tgLink = new TextField();
        tgLink.setId("tgLinkField");
        // --- vkLink ---
        TextField vkLink = new TextField();
        vkLink.setId("vkLinkField");

        // --- name ---
        name.setPromptText("name");
        name.setAlignment(Pos.CENTER);
        // --- telephone ---
        telephone.setPromptText("telephone number");
        telephone.setAlignment(Pos.CENTER);
        // --- email ---
        email.setPromptText("email address");
        email.setAlignment(Pos.CENTER);
        // --- tgLink ---
        tgLink.setPromptText("tg link");
        tgLink.setAlignment(Pos.CENTER);
        // --- vkLink ---
        vkLink.setPromptText("vk link");
        vkLink.setAlignment(Pos.CENTER);

        // --- name ---
        name.setLayoutX(44);
        name.setLayoutY(14);
        // --- telephone ---
        telephone.setLayoutX(44);
        telephone.setLayoutY(53);
        // --- email ---
        email.setLayoutX(44);
        email.setLayoutY(92);
        // --- tgLink ---
        tgLink.setLayoutX(44);
        tgLink.setLayoutY(131);
        // --- vkLink ---
        vkLink.setLayoutX(44);
        vkLink.setLayoutY(170);

        pane.getChildren().addAll(name, telephone, email, tgLink, vkLink);
    }

    private static void createButton() {
        Button save = new Button();
        save.setOnMouseClicked(AddWindow::onClickSave);
        save.setText("Save");
        save.setPrefWidth(80);
        save.setPrefHeight(25);
        save.setLayoutX(78);
        save.setLayoutY(206);

        pane.getChildren().add(save);
    }

    private static void onClickSave(Event e) {
        // --- name ---
        TextField nameField = (TextField) scene.lookup("#nameField");
        String text_nameField = nameField.getText();
        // --- telephone ---
        TextField telephoneNumberField = (TextField) scene.lookup("#telephoneNumberField");
        String text_telephoneNumberField = telephoneNumberField.getText();
        // --- email ---
        TextField emailAddressField = (TextField) scene.lookup("#emailAddressField");
        String text_emailAddressField = emailAddressField.getText();
        // --- tgLink ---
        TextField tgLinkField = (TextField) scene.lookup("#tgLinkField");
        String text_tgLinkField = tgLinkField.getText();
        // --- vkLink ---
        TextField vkLinkField = (TextField) scene.lookup("#vkLinkField");
        String text_vkLinkField = vkLinkField.getText();

        if(!text_nameField.isEmpty()) {
            try {
                Conn.WriteDB(text_nameField, text_telephoneNumberField, text_emailAddressField, text_tgLinkField, text_vkLinkField);
                Window.update();
            } catch (SQLException ex) { /* TODO some kind of mistake. In principle, it is not particularly important :) */}

            Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
            stage.close();
        }
    }
}
