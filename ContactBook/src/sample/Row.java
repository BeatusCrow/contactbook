package sample;


import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Row {
    private int id;
    private String name = null;
    private String telephoneNumber = null;
    private String emailAddress = null;

    public Row(int id, String name, String telephoneNumber, String emailAddress) {
        this.id = id;
        this.name = name;
        this.telephoneNumber = telephoneNumber;
        this.emailAddress = emailAddress;
    }

    public int getId() {return id;}
    public String getName() {return name;}
    public String getTelephoneNumber() {return telephoneNumber;}
    public String getEmailAddress() {return emailAddress;}

    public static Pane createBodyRow(int id, String name, String telephoneNumber, String emailAddress) {
        Pane pane = new Pane();
        pane.setOnMouseClicked(e -> {
            System.out.println(pane.getLayoutY());
        });
        pane.setPrefWidth(542);
        pane.setPrefHeight(36);
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

        Button buttonEdit = new Button();
        buttonEdit.setOnMouseClicked(e -> {
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editing");
            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(EditWindow.createAddWindow(id, name, telephoneNumber, emailAddress));
            dialogStage.show();
        });
        buttonEdit.setText("Edit");
        buttonEdit.setPrefWidth(36.1);
        buttonEdit.setPrefHeight(25);
        buttonEdit.setLayoutX(498);
        buttonEdit.setLayoutY(5);

        pane.getChildren().addAll(text_name, text_telephone, text_email, buttonEdit);
        return pane;
    }
}
