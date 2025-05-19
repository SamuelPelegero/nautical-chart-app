/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package poiupv;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Session;
import model.User;

/**
 * FXML Controller class
 *
 * @author spele
 */
public class FXMLMostrarResultadosController implements Initializable {

    @FXML
    private DatePicker dateField;
    @FXML
    private Button bFiltrar;
    @FXML
    private TableView<Session> tabla;
    
    private ObservableList<Session> sesionesObservable;  // Lista observable para la TableView
    @FXML
    private VBox vbox2;
    @FXML
    private Button bSalirMenu;
    @FXML
    private VBox Vbox;
    @FXML
    private TableColumn<Session, String> fecha;

    
    private User user;
    
    private  List<Session> sesiones;

    private Stage menustage;
    
    private FXMLMenuController menuController;
    @FXML
    private TableColumn<Session, String> columnaAciertos;
    @FXML
    private TableColumn<Session, String> columnaFallos;
    /**
     * Initializes the controller class.
     */
    
    public void setStage(Stage stage){
        this.menustage = stage;
    }
    public void setMenuController(FXMLMenuController controller) {
        this.menuController = controller;
    }
    @Override
public void initialize(URL url, ResourceBundle rb) {
    user = Persona.getInstance().getUser();
    sesiones = user.getSessions();
    
    if(user == null || sesiones == null) {
    tabla.setItems(FXCollections.observableArrayList());
    return;
    }
    
    fecha.setCellValueFactory(cellData -> {
        LocalDateTime timestamp = cellData.getValue().getTimeStamp();
        String fechaFormateada = timestamp.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        return new SimpleStringProperty(fechaFormateada);
    });
    
    columnaAciertos.setCellValueFactory(cellData -> {
    Session sesion = cellData.getValue();
    return new SimpleStringProperty(sesion.getHits() + "");
});

columnaFallos.setCellValueFactory(cellData -> {
    Session sesion = cellData.getValue();
    return new SimpleStringProperty(sesion.getFaults() + "");
});
       
    
    
    tabla.setItems(FXCollections.observableArrayList(sesiones));
    tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 
} 

    @FXML
private void handleBfiltrarOnAction(ActionEvent event) {
    if(dateField.getValue() != null){
        
    LocalDate tiempoFiltro = dateField.getValue();
    List<Session> sesionesFiltradas = new ArrayList<>();
    
    for (Session sesion : sesiones) {
    // Puedes acceder a los métodos de cada sesión
    LocalDateTime tiempoSesionPrimitivo = sesion.getTimeStamp();
    LocalDate tiempoSesion = tiempoSesionPrimitivo.toLocalDate();
    int aciertos = sesion.getHits();
    int fallos = sesion.getFaults();
    if(!tiempoSesion.isBefore(tiempoFiltro)){
        sesionesFiltradas.add(sesion);
    
    }
    
    }
    tabla.setItems(FXCollections.observableArrayList(sesionesFiltradas));
        
    
    
}
     else {
        // Si no hay fecha seleccionada, muestra todas las sesiones
        tabla.setItems(FXCollections.observableArrayList(sesiones));
    }
}

    @FXML
    private void handleBSalirMenuOnAction(ActionEvent event) {
        
               
                Stage currentStage = (Stage) bSalirMenu.getScene().getWindow();
                currentStage.close();
                menustage.setMaximized(true);
                menustage.show();

        
       
        
    }
       
    
}
