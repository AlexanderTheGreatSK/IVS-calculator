package com.company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private TextField display;

    @FXML
    public void buttonPress(ActionEvent event) {
        String value = ((Button) event.getSource()).getText();

        System.out.println(value);

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

}

