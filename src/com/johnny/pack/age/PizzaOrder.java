package com.johnny.pack.age;

import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;

public class PizzaOrder extends Application {

    public static void main(String[] args){
        launch(args);
    }

    // Declare class-level nodes
    private Stage stage;
    private TextField txtName;
    private TextField txtPhone;
    private TextField txtAddress;
    private RadioButton rdoSmall;
    private RadioButton rdoMedium;
    private RadioButton rdoLarge;
    private RadioButton rdoThin;
    private RadioButton rdoThick;
    private CheckBox chkPepperoni;
    private CheckBox chkMushrooms;
    private CheckBox chkAnchovies;

    @Override
    public void start(Stage primaryStage){

        stage = primaryStage;

        // Create name label and text box
        Label lblName = new Label("Name");
        generateNameButton();

        // Create phone label and text box
        Label lblPhone = new Label("Phone");
        generatePhoneButton();

        // Create address label and text box
        Label lblAddress = new Label("Address");
        genereateAddressButton();

        // Create size radio buttons
        ToggleGroup groupSize = new ToggleGroup();
        Label lblSize = new Label("Size");
        generatePizzaSizeList(groupSize);

        // Create size pane
        VBox paneSize = new VBox(10, lblSize, rdoSmall, rdoMedium, rdoLarge);

        // Create crust radio buttons
        ToggleGroup groupCrust = new ToggleGroup();
        Label lblCrust = new Label("Crust");
        generateCrustList(groupCrust);

        // Create crust pane
        VBox paneCrust = new VBox(10, lblCrust, rdoThin, rdoThick);

        // Create toppings checkboxes
        Label lblToppings = new Label("Toppings");
        generateToppingsList();

        // Create toppings pane
        VBox paneToppings = new VBox(10, lblToppings, chkPepperoni, chkMushrooms, chkAnchovies);

        // Create the buttons
        Button btnOk = new Button("OK");
        btnOk.setPrefWidth(80);
        btnOk.setOnAction(e -> btnOk_Click());

        Button btnCancel = new Button("Cancel");
        btnCancel.setPrefWidth(80);
        btnCancel.setOnAction(e -> btnCancel_Click());

        // Create button pane
        HBox paneButtons = new HBox(10, btnOk, btnCancel);

        // Create the grid layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setMinWidth(500);
        grid.setPrefWidth(500);
        grid.setMaxWidth(600);

        // Add nodes to the grid pane
        grid.addRow(0, lblName, txtName);
        grid.addRow(1, lblPhone, txtPhone);
        grid.addRow(2, lblAddress, txtAddress);
        grid.addRow(3, paneSize, paneCrust, paneToppings);
        grid.add(paneButtons, 2, 4);

        // Set alignments and spanning
        grid.setHalignment(lblName, HPos.RIGHT);
        grid.setHalignment(lblPhone, HPos.RIGHT);
        grid.setHalignment(lblAddress, HPos.RIGHT);
        grid.setColumnSpan(txtName, 2);
        grid.setColumnSpan(txtPhone, 2);
        grid.setColumnSpan(txtAddress, 2);

        // Set column widths
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(33);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(33);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(33);
        grid.getColumnConstraints().addAll(col1, col2, col3);

        // Create scene and stage
        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pizza Order");
        primaryStage.setMinWidth(500);
        primaryStage.setMaxWidth(900);
        primaryStage.show();

    }

    private void generateToppingsList() {
        chkPepperoni = new CheckBox("Pepperoni");
        chkMushrooms = new CheckBox("Mushrooms");
        chkAnchovies = new CheckBox("Anchovies");
    }

    private void generateCrustList(ToggleGroup groupCrust) {
        rdoThin = new RadioButton("Thin");
        rdoThin.setToggleGroup(groupCrust);
        rdoThin.setSelected(true);
        rdoThick = new RadioButton("Thick");
        rdoThick.setToggleGroup(groupCrust);
    }

    private void generatePizzaSizeList(ToggleGroup groupSize) {
        rdoSmall = new RadioButton("Small");
        rdoSmall.setToggleGroup(groupSize);
        rdoMedium = new RadioButton("Medium");
        rdoMedium.setToggleGroup(groupSize);
        rdoMedium.setSelected(true);
        rdoLarge = new RadioButton("Large");
        rdoLarge.setToggleGroup(groupSize);
    }

    private void genereateAddressButton() {
        txtAddress = new TextField();
        txtAddress.setMinWidth(100);
        txtAddress.setPrefWidth(200);
        txtAddress.setMaxWidth(300);
        txtAddress.setPromptText("Enter Address Here");
    }

    private void generatePhoneButton() {
        txtPhone = new TextField();
        txtPhone.setMinWidth(60);
        txtPhone.setPrefWidth(120);
        txtPhone.setMaxWidth(180);
        txtPhone.setPromptText("Enter Phone Number Here");
    }

    private void generateNameButton() {
        txtName = new TextField();
        txtName.setMinWidth(100);
        txtName.setPrefWidth(200);
        txtName.setMaxWidth(300);
        txtName.setPromptText("Enter Name Here");
    }

    private void btnOk_Click() {
        // Create message string with the customer information
        String msg = "";
        msg += "Customer: \n";
        msg += "\t" + txtName.getText() + "\n";
        msg += "\t" + getFormattedPhone() + "\n";
        msg += "\t" + txtAddress.getText() + "\n\n";
        msg += "You have ordered a " + getPizzaSize() + " ";
        msg += getCrust() + " crust ";
        msg += "pizza with:\n";
        msg += "\t" + displayToppings();

        Alert a = new Alert(Alert.AlertType.INFORMATION, msg);
        a.setTitle("Customer Order");
        a.showAndWait();
    }

    private String getFormattedPhone() {
        String phone = txtPhone.getText();
        return "(" + phone.substring(0,3) + ") " + phone.substring(3,6) + "-" + phone.substring(6);
    }

    private String displayToppings() {
        String toppings = "";
        toppings = buildToppings(chkPepperoni, toppings);
        toppings = buildToppings(chkMushrooms, toppings);
        toppings = buildToppings(chkAnchovies, toppings);
        if(toppings.equals("")){
            toppings = "no toppings";
        }
        return toppings;
    }

    private String buildToppings(CheckBox chk, String msg){
        if(chk.isSelected()){
            if(!msg.equals("")){
                msg += ", ";
            }
            msg += chk.getText();
        }
        return msg;
    }

    private String getCrust() {
        if(rdoThin.isSelected()){
            return "thin";
        } else {
            return "thick";
        }
    }

    private String getPizzaSize(){
        if (rdoSmall.isSelected()){
            return "small";
        } else if (rdoMedium.isSelected()){
            return "medium";
        } else {
            return "large";
        }
    }

    private void btnCancel_Click() {
        stage.close();
    }
}
