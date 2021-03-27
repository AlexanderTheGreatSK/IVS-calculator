package com.company;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main  extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("UNI(x)calc");
        primaryStage.setMaxHeight(445);
        primaryStage.setMaxWidth(286);
        primaryStage.setMinHeight(445);
        primaryStage.setMinWidth(286);

        //primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("logo.png")));

        primaryStage.setScene(new Scene(root, 286, 417));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
