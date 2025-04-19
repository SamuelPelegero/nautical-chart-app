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
import javafx.stage.Stage;

public class FXMLMenuController {

    @FXML
    private Button handleRealizarProblema;

    @FXML
    private Button handleModificarPerfil;

    @FXML
    private Button handleMostrarResultados;

    @FXML
    private Button handleCerrarSesion;

    @FXML
    private void handleRealizarProblema(ActionEvent event) {
        System.out.println("Realizar Problema seleccionado");
        // Lógica para cambiar de escena o mostrar la vista correspondiente
    }

    @FXML
    private void handleModificarPerfil(ActionEvent event) {
        try {
            Parent modificarPerfilRoot = FXMLLoader.load(getClass().getResource("FXMLModificarPerfil.fxml"));
            Scene modificarPerfilScene = new Scene(modificarPerfilRoot);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(modificarPerfilScene);
            window.setTitle("Modificar Perfil");

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
