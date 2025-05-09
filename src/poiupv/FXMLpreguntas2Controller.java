/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;

import javafx.scene.text.Font;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.YEARS;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.converter.LocalDateStringConverter;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Problem;
import model.Answer;
import model.NavDAOException;
import model.Navigation;

public class FXMLpreguntas2Controller implements Initializable {

    @FXML
    private Label labelPregunta;
    @FXML
    private RadioButton opcionA;
    @FXML
    private RadioButton opcionB;
    @FXML
    private RadioButton opcionC;
    @FXML
    private RadioButton opcionD;

    private List<Problem> todasLasPreguntas;
    private List<Problem> preguntasPendientes;
    private List<Answer> respuestasAleatorias;
    private int preguntasMostradas = 0;
    private int indicePreguntaActual = 0;
    private FXMLMenuController menuController;
    @FXML
    private Button botoncarta;
    @FXML
    private Button botonmenu;
    @FXML
    private Button botonverif;
    @FXML
    private HBox hboxPregunta;
    
    Problem problema = null;

    public void setMenuController(FXMLMenuController controller) {
        this.menuController = controller;
    }
    public void setProblem(Problem a){
        this.problema = a;
    }

    private void cargarPreguntas() throws NavDAOException{
        Navigation navegacion = Navigation.getInstance();
        todasLasPreguntas = navegacion.getProblems();
        preguntasPendientes = new ArrayList<>(todasLasPreguntas);
    }
    private void mostrarPregunta() {    
    List<Answer> respuestas = problema.getAnswers();
    respuestasAleatorias = new ArrayList<>(respuestas);
    Collections.shuffle(respuestasAleatorias); // Aleatoriza el orden
    labelPregunta.setText(problema.getText());
    opcionA.setText(respuestasAleatorias.get(0).getText());
    opcionB.setText(respuestasAleatorias.get(1).getText());
    opcionC.setText(respuestasAleatorias.get(2).getText());
    opcionD.setText(respuestasAleatorias.get(3).getText());

    // Deseleccionar todo
    opcionA.setSelected(false);
    opcionB.setSelected(false);
    opcionC.setSelected(false);
    opcionD.setSelected(false);

    // Restaurar selección previa

}

    private void configurarRadioButtons() {
        opcionA.setOnAction(e -> {
            opcionB.setSelected(false);
            opcionC.setSelected(false);
            opcionD.setSelected(false);
        });
        opcionB.setOnAction(e -> {
            opcionA.setSelected(false);
            opcionC.setSelected(false);
            opcionD.setSelected(false);
        });
        opcionC.setOnAction(e -> {
            opcionA.setSelected(false);
            opcionB.setSelected(false);
            opcionD.setSelected(false);
        });
        opcionD.setOnAction(e -> {
            opcionA.setSelected(false);
            opcionB.setSelected(false);
            opcionC.setSelected(false);
        });
    }

        @Override
    public void initialize(URL url, ResourceBundle rb){
        VBox.setVgrow(hboxPregunta, Priority.ALWAYS);
        HBox.setHgrow(labelPregunta, Priority.ALWAYS);
        labelPregunta.setMaxWidth(Double.MAX_VALUE);
        labelPregunta.setMaxHeight(Double.MAX_VALUE);
        labelPregunta.sceneProperty().addListener((obs, oldScene, scene) -> {
        if (scene != null) {
            scene.widthProperty().addListener((obsWidth, oldWidth, newWidth) -> {
                double fontSize = newWidth.doubleValue() / 75; // Ajusta el divisor a tu gusto
                labelPregunta.setFont(new Font("Arial", fontSize));
            });
        }
        });
    

        try {
            cargarPreguntas();
        } catch (NavDAOException ex) {
            Logger.getLogger(FXMLpreguntasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        configurarRadioButtons();
        Collections.shuffle(preguntasPendientes);
        mostrarPregunta();
    }

    @FXML
    private void abrircarta(ActionEvent event) {
                try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/poiupv/FXMLDocument.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));
            stage.setTitle("Carta Nautica");
            stage.setMinWidth(600);
            stage.setMinHeight(600);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   




    @FXML
    private void salirmenu(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
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

    @FXML
    private void verificarrespuesta(ActionEvent event) {
        Answer respuestaSeleccionada = null;

    // Verificamos cuál opción ha sido seleccionada
    if (opcionA.isSelected()) {
        respuestaSeleccionada = respuestasAleatorias.get(0);
    } else if (opcionB.isSelected()) {
        respuestaSeleccionada = respuestasAleatorias.get(1);
    } else if (opcionC.isSelected()) {
        respuestaSeleccionada = respuestasAleatorias.get(2);
    } else if (opcionD.isSelected()) {
        respuestaSeleccionada = respuestasAleatorias.get(3);
    }

    // Si hay una respuesta seleccionada, la verificamos
    if (respuestaSeleccionada != null) {
        // Comprobamos si la respuesta seleccionada es correcta
        if (respuestaSeleccionada.getValidity()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Respuesta Correcta");
            alert.setHeaderText(null);
            alert.setContentText("¡La respuesta es correcta!");
            alert.showAndWait();
            if (menuController != null) {
            menuController.acertar();
            }
            Stage currentStage = (Stage) botonmenu.getScene().getWindow();
                currentStage.close();
                try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/poiupv/FXMLMenu.fxml"));
                Parent root;
                root = loader.load();
                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
                newStage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));
                newStage.setTitle("Menú Principal");
                newStage.setMinWidth(600);
                newStage.setMinHeight(600);
                newStage.show();
            } catch (IOException ex) {
                Logger.getLogger(FXMLpreguntasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Respuesta Incorrecta");
            alert.setHeaderText(null);
            alert.setContentText("La respuesta es incorrecta.");
            alert.showAndWait();
            if (menuController != null) {
            menuController.fallar();
            }
            Stage currentStage = (Stage) botonmenu.getScene().getWindow();
                currentStage.close();
                try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/poiupv/FXMLMenu.fxml"));
                Parent root;
                root = loader.load();
                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
                newStage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));
                newStage.setTitle("Menú Principal");
                newStage.setMinWidth(600);
                newStage.setMinHeight(600);
                newStage.show();
            } catch (IOException ex) {
                Logger.getLogger(FXMLpreguntasController.class.getName()).log(Level.SEVERE, null, ex);
            }
                
        }
    } else {
        // Si no se ha seleccionado ninguna respuesta, mostramos una alerta de error
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Sin respuesta seleccionada");
        alert.setHeaderText(null);
        alert.setContentText("Por favor, selecciona una respuesta.");
        alert.showAndWait();
    }
    }

    
}

