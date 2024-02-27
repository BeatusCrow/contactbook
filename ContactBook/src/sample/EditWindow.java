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

/**
 The class responsible for creating a window for editing contact data.
 */
public class EditWindow {
    private static Pane pane;
    private static Scene scene;

    public static Scene createAddWindow(int id, String name, String telephoneNumber, String emailAddress, String tgLink, String vkLink){
        pane = new Pane();
        createButtons(id);
        createFields(name, telephoneNumber, emailAddress, tgLink, vkLink);
        scene = new Scene(pane,240,245);

        return scene;
    }

    private static void createFields(String name_, String telephoneNumber_, String emailAddress_, String tgLink_, String vkLink_) {
        TextField name = new TextField();
        name.setId("nameField");
        TextField telephone = new TextField();
        telephone.setId("telephoneNumberField");
        TextField email = new TextField();
        email.setId("emailAddressField");
        TextField tgLink = new TextField();
        tgLink.setId("tgLinkField");
        TextField vkLink = new TextField();
        vkLink.setId("vkLinkField");

        // --------name field-----
        name.setPromptText("name");
        name.setAlignment(Pos.CENTER);
        name.setText(name_);
        // --------telephone field-----
        telephone.setPromptText("telephone number");
        telephone.setAlignment(Pos.CENTER);
        telephone.setText(telephoneNumber_);
        // --------email field-----
        email.setPromptText("email address");
        email.setAlignment(Pos.CENTER);
        email.setText(emailAddress_);
        // -------tgLink Field-----
        tgLink.setPromptText("tg link");
        tgLink.setAlignment(Pos.CENTER);
        tgLink.setText(tgLink_);
        // -------vkLink Field-----
        vkLink.setPromptText("vk link");
        vkLink.setAlignment(Pos.CENTER);
        vkLink.setText(vkLink_);

        // --------name field-----
        name.setLayoutX(44);
        name.setLayoutY(14);
        // --------telephone field-----
        telephone.setLayoutX(44);
        telephone.setLayoutY(53);
        // --------email field-----
        email.setLayoutX(44);
        email.setLayoutY(92);
        // -------tgLink Field-----
        tgLink.setLayoutX(44);
        tgLink.setLayoutY(131);
        // -------vkLink Field-----
        vkLink.setLayoutX(44);
        vkLink.setLayoutY(170);

        pane.getChildren().addAll(name, telephone, email, tgLink, vkLink);
    }

    private static void createButtons(int id) {
        Button save = new Button();
        save.setOnMouseClicked(e -> {
            onClickSave(id, e);
        });
        save.setText("Save");
        save.setPrefWidth(80);
        save.setPrefHeight(25);
        save.setLayoutX(124);
        save.setLayoutY(206);

        Button delete = new Button();
        delete.setOnMouseClicked(e -> {
            try {
                onClickDelete(id, e);
            } catch (SQLException ex) { /* TODO your description of the problem should be here :) */}
        });
        delete.setText("Delete");
        delete.setPrefWidth(80);
        delete.setPrefHeight(25);
        delete.setLayoutX(39);
        delete.setLayoutY(206);

        pane.getChildren().addAll(save, delete);
    }

    private static void onClickSave(int id, Event e) {
        // ----- name
        TextField nameField = (TextField) scene.lookup("#nameField");
        String text_nameField = nameField.getText();
        // ----- telephone
        TextField telephoneNumberField = (TextField) scene.lookup("#telephoneNumberField");
        String text_telephoneNumberField = telephoneNumberField.getText();
        // ----- email
        TextField emailAddressField = (TextField) scene.lookup("#emailAddressField");
        String text_emailAddressField = emailAddressField.getText();
        // ----- tgLink
        TextField tgLinkField = (TextField) scene.lookup("#tgLinkField");
        String text_tgLinkField = tgLinkField.getText();
        // ------ vkLink
        TextField vkLinkField = (TextField) scene.lookup("#vkLinkField");
        String text_vkLinkField = vkLinkField.getText();

        if(!text_nameField.isEmpty()) { // We only check the name field, because there is no point in creating a contact without it
            try {
                Conn.UpdateDB(id, text_nameField, text_telephoneNumberField, text_emailAddressField, text_tgLinkField, text_vkLinkField);
                Window.update(); // We update the contact data in the database and call the update() method to draw the current data in the application
            } catch (SQLException ex) { /* TODO It would be cool to write something here */ }

            Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
            stage.close(); // After saving the changes, the window should be closed
        }
    }

    private static void onClickDelete(int id, Event e) throws SQLException {
        String id_ = String.valueOf(id);
        Conn.DeleteFromDB(id_);
        Window.update();

        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.close();  // After saving 'the changes', the window should be closed
    }
}
