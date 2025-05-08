/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package poiupv;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.NavDAOException;
import model.Navigation;
import model.Problem;

/**
 * FXML Controller class
 *
 * @author angel
 */
public class FXMLlistapreguntasController implements Initializable {

    @FXML
    private Button elegirpre;
    @FXML
    private Button aleatoria;
    
    private FXMLMenuController menuController;
    @FXML
    private ListView<Problem> listaview;
    
    private ObservableList<Problem> todasLasPreguntas;

    /**
     * Initializes the controller class.
     */
    public void setMenuController(FXMLMenuController controller) {
        this.menuController = controller;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        elegirpre.disableProperty().bind(Bindings.equal(listaview.getSelectionModel().selectedIndexProperty(), -1));
    
            
        
    }    

    @FXML
    private void handleelegirpregunta(ActionEvent event) {
    }

    @FXML
    private void handlepreguntaaleatoria(ActionEvent event) {
        System.out.println("Realizar Problema seleccionado");
        Stage currentStage = (Stage) elegirpre.getScene().getWindow();
        currentStage.close();
        System.out.println(getClass().getResource("/poiupv/FXMLpreguntas.fxml"));
        // Lógica para cambiar de escena o mostrar la vista correspondiente
        try {
            // Cargar FXML y obtener el loader
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/poiupv/FXMLpreguntas.fxml"));
        Parent root = loader.load();

        Scene problemascene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(problemascene);
        stage.setTitle("Preguntas");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));
        stage.setMinWidth(600);
        stage.setMinHeight(600);
        stage.show();

        // Cerrar ventana actual si es necesario

        currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
