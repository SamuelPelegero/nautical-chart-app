/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package poiupv;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class FXMLMenuController {

    @FXML
    private Button botprobl;


    @FXML
    private void handleRealizarProblema(ActionEvent event) {
        System.out.println("Realizar Problema seleccionado");
        Stage currentStage = (Stage) botprobl.getScene().getWindow();
        currentStage.close();
        System.out.println(getClass().getResource("/poiupv/FXMLpreguntas.fxml"));
        // Lógica para cambiar de escena o mostrar la vista correspondiente
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/poiupv/FXMLpreguntas.fxml"));
            Scene problemascene = new Scene(root);
            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));
            stage.setScene(problemascene);
            stage.setTitle("Preguntas");
            stage.setMinWidth(600);
            stage.setMinHeight(600);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleModificarPerfil(ActionEvent event) {
        try {
            Parent modificarPerfilRoot = FXMLLoader.load(getClass().getResource("FXMLModificarPerfil.fxml"));
            Scene modificarPerfilScene = new Scene(modificarPerfilRoot);
            

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));
            window.setScene(modificarPerfilScene);
            window.setTitle("Modificar Perfil");
            window.setMinWidth(700);
            window.setMinHeight(500);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("No se pudo cargar FXMLModificarPerfil.fxml");
        }
    }

    @FXML
    private void handleMostrarResultados(ActionEvent event) {
        System.out.println("Mostrar Resultados seleccionado");
        // Lógica para mostrar los resultados del usuario
    }

    @FXML
    private void handleCerrarSesion(ActionEvent event) {
        System.out.println("Cerrar Sesión seleccionado");
        // Lógica para cerrar sesión (volver a login, limpiar sesión, etc.)
    }
}
