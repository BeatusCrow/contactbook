package sample;


import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Row {
    private int id;
    private String name = null;
    private String telephoneNumber = null;
    private String emailAddress = null;
    private String tgLink = null;
    private String vkLink = null;

    public Row(int id, String name, String telephoneNumber, String emailAddress, String tgLink, String vkLink) {
        this.id = id;
        this.name = name;
        this.telephoneNumber = telephoneNumber;
        this.emailAddress = emailAddress;
        this.tgLink = tgLink;
        this.vkLink = vkLink;
    }

    public int getId() {return id;}
    public String getName() {return name;}
    public String getTelephoneNumber() {return telephoneNumber;}
    public String getEmailAddress() {return emailAddress;}
    public String getTgLink() {return tgLink;}
    public String getVkLink() {return vkLink;}

    public static Pane createBodyRow(int id, String name, String telephoneNumber, String emailAddress, String tgLink, String vkLink) {
        Pane pane = new Pane();
        pane.setOnMouseClicked(e -> {
            System.out.println(pane.getLayoutY());
        });
        pane.setPrefWidth(542);
        pane.setPrefHeight(72);
        pane.setStyle("-fx-background-color: #e9e9e9");

        TextField text_name = new TextField();
        text_name.setAlignment(Pos.CENTER);
        text_name.setEditable(false);
        text_name.setText(name);
        text_name.setLayoutX(11);
        text_name.setLayoutY(6);

        TextField text_telephone = new TextField();
        text_telephone.setAlignment(Pos.CENTER);
        text_telephone.setEditable(false);
        text_telephone.setPromptText("telephone number");
        text_telephone.setText(telephoneNumber);
        text_telephone.setLayoutX(176);
        text_telephone.setLayoutY(6);

        TextField text_email = new TextField();
        text_email.setAlignment(Pos.CENTER);
        text_email.setEditable(false);
        text_email.setPromptText("email address");
        text_email.setText(emailAddress);
        text_email.setLayoutX(337);
        text_email.setLayoutY(6);

        TextField text_tg = new TextField();
        text_tg.setId("tgLinkField");
        text_tg.setAlignment(Pos.CENTER);
        text_tg.setEditable(false);
        text_tg.setPromptText("tg link");
        text_tg.setText(tgLink);
        text_tg.setLayoutX(93);
        text_tg.setLayoutY(41);

        TextField text_vk = new TextField();
        text_vk.setId("vkLinkField");
        text_vk.setAlignment(Pos.CENTER);
        text_vk.setEditable(false);
        text_vk.setPromptText("vk link");
        text_vk.setText(vkLink);
        text_vk.setLayoutX(257);
        text_vk.setLayoutY(41);

        Button buttonEdit = new Button();
        buttonEdit.setOnMouseClicked(e -> {
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editing");
            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(EditWindow.createAddWindow(id, name, telephoneNumber, emailAddress, tgLink, vkLink));
            dialogStage.show();
        });
        buttonEdit.setText("Edit");
        buttonEdit.setPrefWidth(36.1);
        buttonEdit.setPrefHeight(25);
        buttonEdit.setLayoutX(498);
        buttonEdit.setLayoutY(25);

        Button buttonGoToTG = new Button();
        buttonGoToTG.setOnMouseClicked(e -> {
            try {
                URI uri = new URI(text_tg.getText());
                open(uri);
            } catch (URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });
        buttonGoToTG.setText("go to ->");
        buttonGoToTG.setPrefWidth(63);
        buttonGoToTG.setPrefHeight(25);
        buttonGoToTG.setLayoutX(11);
        buttonGoToTG.setLayoutY(41);

        Button buttonGoToVK = new Button();
        buttonGoToVK.setOnMouseClicked(e -> {
            try {
                URI uri = new URI(text_vk.getText());
                open(uri);
            } catch (URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });
        buttonGoToVK.setText("<- go to");
        buttonGoToVK.setPrefWidth(63);
        buttonGoToVK.setPrefHeight(25);
        buttonGoToVK.setLayoutX(422);
        buttonGoToVK.setLayoutY(41);

        Pane blackLine = new Pane();
        blackLine.setStyle("-fx-background-color: #000000");
        blackLine.setPrefWidth(542);
        blackLine.setPrefHeight(1);
        blackLine.setLayoutX(0);
        blackLine.setLayoutY(71);

        pane.getChildren().addAll(text_name, text_telephone, text_email, text_tg, text_vk, buttonEdit, blackLine, buttonGoToTG, buttonGoToVK);
        return pane;
    }

    private static void open(URI uri) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(uri);
            } catch (IOException e) { /* TODO: error handling :)*/ }
        } else { /* TODO: error handling :(*/ }
    }
}
