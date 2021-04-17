package com.company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

// TODO documentation
public class Controller {

    @FXML
    private TextField display;

    @FXML
    private TextField resultDisplay;

    @FXML
    public void buttonPress(ActionEvent event) {
        String value = ((Button) event.getSource()).getText();

        switch (value) {
            case "AC":
                display.setText("");
                resultDisplay.setText("");
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
            case "MOD":
                display.setText(display.getText() + "%");
                break;
            case "x!":
                display.setText(display.getText() + "!");
                break;
            case "=":
                String finalValue = display.getText();
                //display.setText(display.getText() + "=");
                finalValue = callLibrary(finalValue);
                System.out.println("FinalValue: " + finalValue);
                resultDisplay.setText(finalValue);

                break;
            default:
                display.setText(display.getText() + value);
                break;
        }
    }

    /**
     * Calls the CalcLib main function to calculate the result of an operation.
     * Handles any exception by returning the exception message.
     *
     * @param input an expression to be calculated
     * @return result or an error message
     */
    public String callLibrary(String input) {
        try {
            input = CalcLib.main(input);
        }
        catch (Exception e){
            input = e.getMessage();
        }
        return input;
    }

        @FXML
        public void aboutBtn (ActionEvent event) {
            Stage stage = new Stage();
            String authors = "Jiřina Frýbortová (xfrybo01)\n" + "Jana Kováčiková (xkovac59)\n" + "Patrik Skaloš (xskalo01)\n" + "Alexander Okrucký (xokruc00)";
            String license = "This project is licensed under the GNU GPL v.2 License.";
            String team = "My dve a my dvaja";
            String name = "UNI(x)calc";

                Image image = new Image(Main.class.getResourceAsStream("logo.png"));
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);

                Text nameText = new Text(name);
                nameText.setTextAlignment(TextAlignment.CENTER);
                nameText.setFont(Font.font("",FontWeight.BOLD, 25));

                Text teamText = new Text(team);
                teamText.setTextAlignment(TextAlignment.CENTER);

                Text authorsText = new Text(authors);
                teamText.setWrappingWidth(100);
                nameText.setFont(Font.font("",FontWeight.BOLD, 20));

                Text licenseText = new Text(license);
                teamText.setWrappingWidth(50);

                VBox root = new VBox();
                root.getChildren().addAll(imageView, nameText, teamText, authorsText, licenseText);
                root.setSpacing(10);
                root.setAlignment(Pos.CENTER);

                root.setStyle("-fx-padding: 10;" +
                        "-fx-border-style: solid inside;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-insets: 5;" +
                        "-fx-border-radius: 5;" +
                        "-fx-border-color: blue;");

                Scene scene = new Scene(root, 400, 400);
                stage.getIcons().add(new Image(Main.class.getResourceAsStream("logo.png")));
                stage.setScene(scene);
                stage.setTitle("About");
                stage.setMinWidth(400);
                stage.setMinHeight(400);
                stage.setMaxWidth(400);
                stage.setMaxHeight(400);

                stage.show();

        }

    @FXML
    public void helpBtn (ActionEvent event){
        Stage stage = new Stage();
        // TODO help guide
        String textToShow = "Enter your input using your keyboard or the on-screen keyboard. Press Enter or the \"=\" button when you're done. \n" +
                "The \"AC\" button clears the calculator. Press the left arrow \"<-\" button or backspace to delete one character.\n" +
                "You can write sqrt on your keyboard to find the square root, the symbol \"%\" is used for modulo (remainder), not to be confused with percentage.\n" +
                "Note that this calculator uses a decimal point, not a comma.\n" +
                "Use parentheses, if necessary, for example \"a/(b+c)\". / Parentheses can't be used in this app.\n" +
                "See the user guide for more information.";


        Text text3 = new Text(textToShow);
        text3.setWrappingWidth(450);
        text3.setFont(Font.font("", 15));

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
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("logo.png")));
        stage.setTitle("User Guide");
        stage.setMinWidth(500);
        stage.setMinHeight(300);
        stage.setMaxWidth(500);
        stage.setMaxHeight(300);

        stage.show();
    }
}

