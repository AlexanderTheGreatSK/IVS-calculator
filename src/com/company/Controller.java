package com.company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class Controller {

    @FXML
    private Text outputText;

    @FXML
    public void buttonPress(ActionEvent event) {
        String value = ((Button) event.getSource()).getText();
        outputText.setText(outputText.getText() + value);
    }

    @FXML
    private void finishEq(ActionEvent event) {

    }

}

