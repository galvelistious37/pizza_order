package com.johnny.pack.age;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class PizzaOrder extends Application {
    private final String REGEX_PHONE = "\\(?\\d{3}\\)?\\s?-?\\d{3}-?\\s?\\d{4}";

    public static void main(String[] args){
        launch(args);
    }

    // Declare Stage node
    private Stage stage;

    // Text Boxes
    private TextField txtName;
    private TextField txtPhone;
    private TextField txtAddress;

    // Radio Buttons
    private RadioButton rdoSmall;
    private RadioButton rdoMedium;
    private RadioButton rdoLarge;
    private RadioButton rdoThin;
    private RadioButton rdoThick;

    // Check Boxes
    private CheckBox chkPepperoni;
    private CheckBox chkAnchovies;
    private CheckBox chkSausage;
    private CheckBox chkChicken;
    private CheckBox chkBacon;
    private CheckBox chkMushrooms;
    private CheckBox chkJalapenos;
    private CheckBox chkOlives;
    private CheckBox chkTomatoes;
    private CheckBox chkPineapple;

    private LinkedList<String> toppingList;
    private Label lblSelectedToppings;

    @Override
    public void start(Stage primaryStage){

        // Set the stage
        stage = primaryStage;

        //---------- Create the top pane --------------------//
        Text txtHeading = new Text("Welcome to the Pizza Orderer....rer!");
        txtHeading.setFont(new Font(20));
        HBox paneTop = new HBox(txtHeading);
        paneTop.setPadding(new Insets(20, 10, 20, 10));

        //---------- Create the customer pane ---------------//
        // Create name label and text box
        Label lblName = new Label("Name:");
        lblName.setPrefWidth(100);
        txtName = new TextField();
        txtName.setPrefColumnCount(20);
        txtName.setPromptText("Enter the customer's name here!");
        txtName.setMaxHeight(Double.MAX_VALUE);
        HBox paneName = new HBox(lblName, txtName);

        // Create phone label and text box
        Label lblPhone = new Label("Phone:");
        lblPhone.setPrefWidth(100);
        txtPhone = new TextField();
        txtPhone.setPrefColumnCount(20);
        txtPhone.setPromptText("Enter the customer's phone here!");
        txtPhone.setMaxWidth(Double.MAX_VALUE);
        HBox panePhone = new HBox(lblPhone, txtPhone);

        // Create address label and text box
        Label lblAddress = new Label("Address:");
        lblAddress.setPrefWidth(100);
        txtAddress = new TextField();
        txtAddress.setPrefColumnCount(20);
        txtAddress.setPromptText("Enter the customer's address here!");
        txtAddress.setMaxWidth(Double.MAX_VALUE);
        HBox paneAddress = new HBox(lblAddress, txtAddress);

        // Create customer pane
        VBox paneCustomer = new VBox(10, paneName, panePhone, paneAddress);

        //---------- Create the order pane ---------------//
        // Create size pane
        ToggleGroup groupSize = new ToggleGroup();
        Label lblSize = new Label("Size");
        rdoSmall = new RadioButton("Small");
        rdoMedium = new RadioButton("Medium");
        rdoLarge = new RadioButton("Large");
        rdoMedium.setSelected(true);
        rdoSmall.setToggleGroup(groupSize);
        rdoMedium.setToggleGroup(groupSize);
        rdoLarge.setToggleGroup(groupSize);
        VBox paneSize = new VBox(lblSize, rdoSmall, rdoMedium, rdoLarge);
        paneSize.setSpacing(10);

        // Create crust pane
        ToggleGroup groupCrust = new ToggleGroup();
        Label lblCrust = new Label("Crust");
        rdoThin = new RadioButton("Thin");
        rdoThick = new RadioButton("Thick");
        rdoThin.setSelected(true);
        rdoThin.setToggleGroup(groupCrust);
        rdoThick.setToggleGroup(groupCrust);
        VBox paneCrust = new VBox(lblCrust, rdoThin, rdoThick);
        paneCrust.setSpacing(10);

        // Create toppings pane
        Label lblToppings = new Label("Toppings");
        chkPepperoni = new CheckBox("Pepperoni");
        chkAnchovies = new CheckBox("Anchovies");
        chkSausage = new CheckBox("Sausage");
        chkChicken = new CheckBox("Chicken");
        chkBacon = new CheckBox("Bacon");
        chkMushrooms = new CheckBox("Mushrooms");
        chkJalapenos = new CheckBox("Jalapenos");
        chkOlives = new CheckBox("Olives");
        chkTomatoes = new CheckBox("Tomatoes");
        chkPineapple = new CheckBox("Pineapple");

        chkPepperoni.setOnAction(e -> chkBox_Clicked(chkPepperoni));
        chkAnchovies.setOnAction(e -> chkBox_Clicked(chkAnchovies));
        chkSausage.setOnAction(e -> chkBox_Clicked(chkSausage));
        chkChicken.setOnAction(e -> chkBox_Clicked(chkChicken));
        chkBacon.setOnAction(e -> chkBox_Clicked(chkBacon));
        chkMushrooms.setOnAction(e -> chkBox_Clicked(chkMushrooms));
        chkJalapenos.setOnAction(e -> chkBox_Clicked(chkJalapenos));
        chkOlives.setOnAction(e -> chkBox_Clicked(chkOlives));
        chkTomatoes.setOnAction(e -> chkBox_Clicked(chkTomatoes));
        chkPineapple.setOnAction(e -> chkBox_Clicked(chkPineapple));

        FlowPane paneToppings = new FlowPane(Orientation.VERTICAL,
                chkPepperoni, chkAnchovies, chkSausage, chkChicken,
                chkBacon, chkMushrooms, chkJalapenos, chkOlives,
                chkTomatoes, chkPineapple);
        paneToppings.setPadding(new Insets(10, 0, 10, 0));
        paneToppings.setHgap(20);
        paneToppings.setVgap(10);
        paneToppings.setPrefWrapLength(200);

        VBox paneTopping = new VBox(lblToppings, paneToppings);

        HBox paneOrder = new HBox(50, paneSize, paneCrust, paneTopping);

        // Create the center pane
        VBox paneCenter = new VBox(20, paneCustomer, paneOrder);
        paneCenter.setPadding(new Insets(0, 10, 0, 10));

        //---------- Create right pane --------------------//
        // Create toppings display
        toppingList = new LinkedList<>();
        Label lblSelectHeader = new Label("You have selected:");
        lblSelectedToppings = new Label();
        lblSelectedToppings.setText("");
        showSelectedToppings();
        lblSelectedToppings.setMinWidth(150);
        VBox paneRight = new VBox(10);
        paneRight.getChildren().addAll(lblSelectHeader, lblSelectedToppings);

        //---------- Create the bottom pane ---------------//
        Button btnOk = new Button("OK");
        btnOk.setPrefWidth(80);
        btnOk.setOnAction(e -> btnOk_Click());

        Button btnCancel = new Button("Cancel");
        btnCancel.setPrefWidth(80);
        btnCancel.setOnAction(e -> btnCancel_Click());

        Region spacer = new Region();

        // Create button pane
        HBox paneBottom = new HBox(10, spacer, btnOk, btnCancel);
        paneBottom.setHgrow(spacer, Priority.ALWAYS);
        paneBottom.setPadding(new Insets(20, 10, 20, 10));

        //---------- Finish the scene ---------------//
        BorderPane paneMain = new BorderPane();
        paneMain.setPrefWidth(600);
        paneMain.setPrefHeight(420);
        paneMain.setTop(paneTop);
        paneMain.setCenter(paneCenter);
        paneMain.setRight(paneRight);
        paneMain.setBottom(paneBottom);

        // Commented code uses a grid pane instead of a border pane
//        // Create the grid layout
//        GridPane grid = new GridPane();
//        grid.setPadding(new Insets(10));
//        grid.setHgap(10);
//        grid.setVgap(10);
//        grid.setMinWidth(500);
//        grid.setPrefWidth(500);
//        grid.setMaxWidth(600);
//
//        // Add nodes to the grid pane
//        grid.addRow(0, lblName, txtName);
//        grid.addRow(1, lblPhone, txtPhone);
//        grid.addRow(2, lblAddress, txtAddress);
//        grid.addRow(3, paneSize, paneCrust, paneToppings);
//        grid.add(paneBottom, 2, 4);
//
//        // Set alignments and spanning
//        grid.setHalignment(lblName, HPos.RIGHT);
//        grid.setHalignment(lblPhone, HPos.RIGHT);
//        grid.setHalignment(lblAddress, HPos.RIGHT);
//        grid.setColumnSpan(txtName, 2);
//        grid.setColumnSpan(txtPhone, 2);
//        grid.setColumnSpan(txtAddress, 2);
//
//        // Set column widths
//        ColumnConstraints col1 = new ColumnConstraints();
//        col1.setPercentWidth(33);
//        ColumnConstraints col2 = new ColumnConstraints();
//        col2.setPercentWidth(33);
//        ColumnConstraints col3 = new ColumnConstraints();
//        col3.setPercentWidth(33);
//        grid.getColumnConstraints().addAll(col1, col2, col3);

        // Create scene and stage
        Scene scene = new Scene(paneMain);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pizza Order");
//        primaryStage.setMinWidth(500);
//        primaryStage.setMaxWidth(900);
        primaryStage.show();

    }

    private void showSelectedToppings() {
        lblSelectedToppings.setText("");
        if(toppingList.size() > 0){
            for(String topping : toppingList){
                String temp = lblSelectedToppings.getText();
                temp += "\t" + topping + "\n";
                lblSelectedToppings.setText(temp);
            }
        } else {
            String temp = lblSelectedToppings.getText();
            temp += "\tNo Toppings";
            lblSelectedToppings.setText(temp);
        }
    }

    private void chkBox_Clicked(CheckBox box) {
        if(box.isSelected()){
            toppingList.add(box.getText());
        }
        if(!box.isSelected()){
            if(toppingList.contains(box.getText())){
                toppingList.remove(box.getText());
            }
        }
        showSelectedToppings();
    }

    private void btnOk_Click() {
        if(isAcceptablePhone(txtPhone.getText())){
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
        } else {
            Alert b = new Alert(Alert.AlertType.ERROR, "Invalid phone number");
            b.setTitle("Invalid Phone Format");
            b.showAndWait();
        }
    }

    private String getFormattedPhone() {
        return formattedPhone(getDigitsOnly());
    }

    private String formattedPhone(String digitsOnly){
        return "(" + digitsOnly.substring(0,3) + ") "
                + digitsOnly.substring(3,6) + "-"
                + digitsOnly.substring(6);
    }

    private String getDigitsOnly(){
        StringBuilder newNumber = new StringBuilder();
        for (int i = 0; i < txtPhone.getText().length(); i++) {
            String oneDigit = txtPhone.getText().substring(i, i + 1);
            if (Pattern.matches("\\d", oneDigit)) {
                newNumber.append(oneDigit);
            }
        }
        return newNumber.toString();
    }

    private boolean isAcceptablePhone(String text){
        return Pattern.matches(REGEX_PHONE, text);
    }

    private String displayToppings() {
        String toppings = "";
        toppings = buildToppings(chkPepperoni, toppings);
        toppings = buildToppings(chkAnchovies, toppings);
        toppings = buildToppings(chkSausage, toppings);
        toppings = buildToppings(chkChicken, toppings);
        toppings = buildToppings(chkBacon, toppings);
        toppings = buildToppings(chkMushrooms, toppings);
        toppings = buildToppings(chkJalapenos, toppings);
        toppings = buildToppings(chkOlives, toppings);
        toppings = buildToppings(chkTomatoes, toppings);
        toppings = buildToppings(chkPineapple, toppings);
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
