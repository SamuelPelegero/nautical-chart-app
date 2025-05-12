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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Navigation;
import model.User;

public class FXMLMenuController {

    @FXML
    private Button botprobl;
    private int hits;
    private int faults;
    private User user;
    @FXML
    private Button modperf;
    @FXML
    private Button mostarse;
    @FXML
    private Button cerrar;
    

    public void setUser(User user) {
        this.user = user;
    }
    @FXML
    private void handleRealizarProblema(ActionEvent event) {
        System.out.println("Realizar Problema seleccionado");
        Stage currentStage = (Stage) botprobl.getScene().getWindow();
        currentStage.hide();
        System.out.println(getClass().getResource("/poiupv/FXMLlistapreguntas.fxml"));
        // Lógica para cambiar de escena o mostrar la vista correspondiente
        try {
            // Cargar FXML y obtener el loader
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/poiupv/FXMLlistapreguntas.fxml"));
        Parent root = loader.load();

        // Obtener el controlador del FXML ya cargado
        FXMLlistapreguntasController preguntasController = loader.getController();

        // Pasar referencia del controlador actual
        preguntasController.setMenuController(this);
        preguntasController.setStage((Stage) ((Node) event.getSource()).getScene().getWindow());

        // Crear nueva escena
        Scene problemascene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(problemascene);
        stage.setTitle("Preguntas");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));
        stage.setMinWidth(600);
        stage.setMinHeight(600);
        stage.setMaximized(true);
        stage.show();
        



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleModificarPerfil(ActionEvent event) {
        botprobl.disableProperty().set(true);
        mostarse.disableProperty().set(true);
        modperf.disableProperty().set(true);
        cerrar.disableProperty().set(true);
        
        try {
            Parent modificarPerfilRoot = FXMLLoader.load(getClass().getResource("FXMLModificarPerfil.fxml"));
            Scene modificarPerfilScene = new Scene(modificarPerfilRoot);

        Stage stage = new Stage();
        stage.setScene(modificarPerfilScene);
        stage.setTitle("Modificar Perfil");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));
        stage.setResizable(false);
        stage.setMinWidth(500);
        stage.setMinHeight(625);
        stage.showAndWait();
        botprobl.disableProperty().set(false);
        mostarse.disableProperty().set(false);
        modperf.disableProperty().set(false);
        cerrar.disableProperty().set(false);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("No se pudo cargar FXMLModificarPerfil.fxml");
        }
    }

    @FXML
    private void handleMostrarResultados(ActionEvent event) {
       try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/poiupv/FXMLMostrarResultados.fxml"));
        Parent root = loader.load();

        FXMLMostrarResultadosController mostrarController = loader.getController();
        mostrarController.setStage((Stage) botprobl.getScene().getWindow()); // Pasar el menú para luego mostrarlo
        mostrarController.setMenuController(this); // opcional, si lo usas

        Stage resultadosStage = new Stage();
        resultadosStage.setScene(new Scene(root));
        resultadosStage.setTitle("Mostrar Resultados");
        resultadosStage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));
        resultadosStage.setMinWidth(700);
        resultadosStage.setMinHeight(700);
        resultadosStage.setMaximized(true);
        resultadosStage.show();

        // Oculta el menú mientras está abierta la ventana de resultados
        ((Stage) botprobl.getScene().getWindow()).hide();

    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("No se pudo cargar FXMLMostrarResultados.fxml");
    }
    }

    @FXML
    private void handleCerrarSesion(ActionEvent event) {
        System.out.println(hits);
        System.out.println(faults);
        user.addSession(hits, faults);
        try {
        // Cargar la pantalla de inicio de sesión
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/poiupv/FXMLLogIn.fxml"));
        Parent root = loader.load();
        
        // Obtener la ventana actual y cerrarla (si es necesario)
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close(); // Cerrar ventana de registro
        
        // Crear el nuevo Stage para la ventana de inicio de sesión
        Stage newStage = new Stage();
        newStage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));
        newStage.setScene(new Scene(root));
        newStage.setResizable(false);
        newStage.setTitle("Iniciar Sesión");
        newStage.show();
        
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    public void acertar() {
        hits++;
    }
    public void fallar() {
        faults++;
    }

}
