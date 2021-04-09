package com.company;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    String textToShow = "Enter your input using your keyboard or the on-screen keyboard. Press Enter or the \"=\" button when you're done. \n" +
            "The \"AC\" button clears the calculator. Press the left arrow \"<-\" button or backspace to delete one character.\n" +
            "You can write sqrt on your keyboard to find the square root, the symbol \"%\" is used for modulo (remainder), not to be confused with percentage.\n" +
            "Note that this calculator uses a decimal point, not a comma.\n" +
            "Use parentheses, if necessary, for example \"a/(b+c)\". / Parentheses can't be used in this app.\n" +
            "See the user guide for more information.";

    @FXML
    private TextField display;

    @FXML
    private ImageView imageView;

    @FXML
    public void buttonPress(ActionEvent event) {
        String value = ((Button) event.getSource()).getText();

        switch (value) {
            case "AC":
                display.setText("");
                break;
            case "√":
                display.setText(display.getText() + "root(");

                break;
            case "DEL":
                if (display.getText().length() != 0) {

                    char[] nDisplay = new char[display.getText().length()];

                    display.getText().getChars(0, display.getText().length() - 1, nDisplay, 0);

                    display.setText(String.valueOf(nDisplay));
                }

                break;
            case "mod":
                display.setText(display.getText() + "%");
                break;
            case "x!":
                display.setText(display.getText() + "!");
                break;
            case "=":
                String finalValue = display.getText();
                display.setText(display.getText() + "=");

                System.out.println("FinalValue: " + finalValue);

                break;
            default:
                display.setText(display.getText() + value);
                break;
        }
    }

        @FXML
        public void aboutBtn (ActionEvent event) {
            Stage stage = new Stage();
            String authors = "";


            Text text3 = new Text(textToShow);
            text3.setWrappingWidth(450);

            HBox root = new HBox();
            root.getChildren().addAll(text3);
            root.setSpacing(20);

            root.setStyle("-fx-padding: 10;" +
                    "-fx-border-style: solid inside;" +
                    "-fx-border-width: 2;" +
                    "-fx-border-insets: 5;" +
                    "-fx-border-radius: 5;" +
                    "-fx-border-color: blue;");

            Scene scene = new Scene(root, 450, 450);
            stage.setScene(scene);
            stage.setTitle("User Guide");
            stage.setMinWidth(500);
            stage.setMinHeight(300);
            stage.setMaxWidth(500);
            stage.setMaxHeight(300);

            stage.show();
        }

    @FXML
    public void helpBtn (ActionEvent event){
        Stage stage = new Stage();
        String textToShow = "Enter your input using your keyboard or the on-screen keyboard. Press Enter or the \"=\" button when you're done. \n" +
                "The \"AC\" button clears the calculator. Press the left arrow \"<-\" button or backspace to delete one character.\n" +
                "You can write sqrt on your keyboard to find the square root, the symbol \"%\" is used for modulo (remainder), not to be confused with percentage.\n" +
                "Note that this calculator uses a decimal point, not a comma.\n" +
                "Use parentheses, if necessary, for example \"a/(b+c)\". / Parentheses can't be used in this app.\n" +
                "See the user guide for more information.";


        Text text3 = new Text(textToShow);
        text3.setWrappingWidth(450);

        HBox root = new HBox();
        root.getChildren().addAll(text3);
        root.setSpacing(20);

        root.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");

        Scene scene = new Scene(root, 450, 450);
        stage.setScene(scene);
        stage.setTitle("User Guide");
        stage.setMinWidth(500);
        stage.setMinHeight(300);
        stage.setMaxWidth(500);
        stage.setMaxHeight(300);

        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

