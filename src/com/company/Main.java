/******************************************************************************
 * Name: UNI(x)calc
 * Package: com.company
 *
 * This is our calculator application in JavaFX.
 *
 * Authors: Jiřina Frýbortová (xfybo01)
 *          Jana Kováčiková (xkovac59)
 *          Patrik Skaloš (xskalo01)
 *          Alexander Okrucký (xokruc00)
 *****************************************************************************/

package com.company;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * @brief Main class
     *
     * Main class starts our application and sets basic properties
     * of PrimaryStage.
     *
     * @author xfrybo01
     * @author xokruc00
     * @version 1.0
     */


    /**
     * Sets basic configuration of our Application.
     * It will load calc.fxml as a default GUI Scene, setMaxHeight,
     * setMinHeight,setMaxWidth and setMinWidth to same pixel resolution
     * so application window will have same resolution. Our application
     * can't be resized and maximised to fullscreen. Our logo is set
     * to primary stage
     *
     * @param primaryStage the parent Stage
     *
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("calc.fxml"));
        primaryStage.setTitle("UNI(x)calc");
        primaryStage.setMaxHeight(460);
        primaryStage.setMaxWidth(286);
        primaryStage.setMinHeight(460);
        primaryStage.setMinWidth(286);

        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("logo.png")));

        primaryStage.setScene(new Scene(root, 286, 460));
        primaryStage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts
     *
     * @param args the command line arguments
     */
    public static void main(String[] args)
            throws InterruptedException{
        launch(args);
    }
}
