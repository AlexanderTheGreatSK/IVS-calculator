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


public class Controller {

    /**
     * @brief GUI Controller
     *
     * This class is backend for our frontend.
     * GUI Controller includes how GUI will be build, handle user input, calculate
     * result via our math library and result show on GUI. Buttons call only one
     * method which will run specific code based on button's label. In our GUI
     * are two TextFields, the bigger one is where user writes equations with
     * keyboard or our calculator keyboard, with Enter u can solve the equation.
     * The smaller TextField is just for result, user can't type there.
     *
     * @author xokruc00
     * @version 1.0
     */

    @FXML
    private TextField display;

    @FXML
    private TextField resultDisplay;

    /**
     * If main TextField is focused this function will catch
     * pressed Enter on keyboard and get the mathematical
     * expresion and pass to MathLibrary. Result value
     * is set to resultTextField.
     *
     * @param actionEvent
     */
    @FXML
    public void onEnter(ActionEvent actionEvent){
        String finalValue = display.getText();
        finalValue = callLibrary(finalValue);
        System.out.println("Result: " + finalValue);
        resultDisplay.setText(finalValue);
    }

    /**
     * This method handles user input via calculator keyboard.
     * Every button have specific tasks.
     *
     * @param event parse button properties which was pressed
     */
    @FXML
    public void buttonPress(ActionEvent event) {
        String value = ((Button) event.getSource()).getText();

        switch (value) {
            case "AC":
                display.setText("");
                resultDisplay.setText("");
                break;
            case "√":
                if (isNumeric(resultDisplay.getText())) {
                    display.setText("root(" + resultDisplay.getText() + ";");
                    resultDisplay.setText("");
                } else {
                    resultDisplay.setText("");
                    display.setText(display.getText() + "root(");
                }
                break;
            case "DEL":
                if (display.getText().length() != 0) {
                    char[] nDisplay = new char[display.getText().length()];

                    display.getText().getChars(0, display.getText().length() - 1, nDisplay, 0);
                    display.setText(String.valueOf(nDisplay));
                }

                break;
            case "MOD":
                if (isNumeric(resultDisplay.getText())) {
                    display.setText(resultDisplay.getText() + "%");
                    resultDisplay.setText("");
                } else {
                    resultDisplay.setText("");
                    display.setText(display.getText() + "%");
                }
                break;
            case "=":
                String finalValue = display.getText();
                finalValue = callLibrary(finalValue);
                System.out.println("FinalValue: " + finalValue);
                resultDisplay.setText(finalValue);

                break;
            case "(":
                if (isNumeric(resultDisplay.getText())) {
                    display.setText("(" + resultDisplay.getText());
                    resultDisplay.setText("");
                } else {
                    resultDisplay.setText("");
                    display.setText(display.getText() + "(");
                }

                break;
            case ")":
                if (isNumeric(resultDisplay.getText())) {
                    display.setText("(" + resultDisplay.getText() + ")");
                    resultDisplay.setText("");
                } else {
                    resultDisplay.setText("");
                    display.setText(display.getText() + ")");
                }

                break;
            case "+":
            case "-":
            case "*":
            case "/":
            case "^":
            case "x!":
            case ".":
                if (value.equals("x!")) {
                    value = "!";
                }
                if (isNumeric(resultDisplay.getText())) {
                    display.setText(resultDisplay.getText() + value);
                    resultDisplay.setText("");
                } else {
                    resultDisplay.setText("");
                    display.setText(display.getText() + value);
                }

                break;
            default:
                resultDisplay.setText("");
                display.setText(display.getText() + value);
                break;
        }
    }

    /**
     * This method checks if string is number.
     *
     * @param strNum String which will be checked
     * @return boolean value, true if it is number, false if isn't
     */
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
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

    /**
     * This method is called when user will press About button which is under
     * menu button Help. New window is launched.
     *
     * @param event an expression to be calculated
     */
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

    /**
     * This method is called when user will press User guide button which is under
     * menu button Help. On this page is our user manual for our calculator.
     * New window is launched.
     *
     * @param event an expression to be calculated
     **/
    @FXML
    public void helpBtn (ActionEvent event){
        Stage stage = new Stage();
        String textToShow = "Enter your input using your keyboard or the on-screen keyboard. " +
                "Press Enter or the \"=\" button when you're done. " +
                "The \"AC\" button clears the calculator. Press the \"DEL\" button or backspace to delete one character.\n\n" +

                "You can write x^n or pow(x; n) to find the nth power of x, or root(x; n) to find the nth root of x. " +
                "Only a whole power or natural root can be calculated (for example NOT 4^0.5).\n\n" +
                "The symbol \"%\" (button \"MOD\") is used for modulo (remainder), not to be confused with percentage." +
                "Both decimal point and comma can be used on the input." +
                "Use parentheses, if necessary, for example \"a/(b+c)\".\n\n\n" +

                "ADVANCED OPTIONS\n" +
                "You can use the symbol \"|\" for root, for example x|n is the nth root of x." +
                "When writing pow() or root(), you can write for example pow(4+5; 3), but you can't use parentheses." +
                "However, when using \"^\" or \"|\", you can use parentheses to calculate a more complex expression, for example 4|(4/(1+1)).\n" +
                "\n\n" +
                "See the full user guide for more information.";


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
        stage.setMinHeight(560);
        stage.setMaxWidth(500);
        stage.setMaxHeight(560);

        stage.show();
    }
}

