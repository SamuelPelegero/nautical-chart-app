/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package poiupv;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
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
    
    private List<Problem> todasLasPreguntas;
    
    private ObservableList<Problem> Preguntas;
    @FXML
    private Button botonmenu;

    /**
     * Initializes the controller class.
     */
    public void setMenuController(FXMLMenuController controller) {
        this.menuController = controller;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Navigation navegacion;
        try {
            navegacion = Navigation.getInstance();
            todasLasPreguntas = navegacion.getProblems();
        } catch (NavDAOException ex) {
            Logger.getLogger(FXMLlistapreguntasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        elegirpre.disableProperty().bind(Bindings.equal(listaview.getSelectionModel().selectedIndexProperty(), -1));
        Preguntas = FXCollections.observableArrayList(todasLasPreguntas);
        listaview.setItems(Preguntas);    
        listaview.setCellFactory(c-> new ProblemListCell());
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

    @FXML
    private void salirmenu(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Seguro que deseas salir al menu?");
        alert.setContentText("La repuesta no se guardará.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                System.out.println("¡Acción Confirmada!");
                Stage currentStage = (Stage) botonmenu.getScene().getWindow();
                currentStage.close();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/poiupv/FXMLMenu.fxml"));
                Parent root = loader.load();
                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
                newStage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));
                newStage.setTitle("Menú Principal");
                newStage.setMinWidth(600);
                newStage.setMinHeight(600);
                newStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Acción Cancelada");
        }
    }
        public class ProblemListCell extends ListCell<Problem>{
    @Override
    protected void updateItem(Problem item, boolean empty)
    { 
    if (item==null || empty) setText(null);
    else setText(item.getText());
    }
}
    
}
