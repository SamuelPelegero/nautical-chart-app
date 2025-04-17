/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author jose
 */
public class PoiUPVApp extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/poiupv/FXMLRegister.fxml"));
    stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));
    Scene scene = new Scene(root);

    // Fijar el tamaño de la ventana
    stage.setWidth(800);    // Ancho fijo
    stage.setHeight(600);   // Alto fijo

    // Deshabilitar el cambio de tamaño
    stage.setResizable(false);

    stage.setTitle("Registrate");
    stage.setScene(scene);
    stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
