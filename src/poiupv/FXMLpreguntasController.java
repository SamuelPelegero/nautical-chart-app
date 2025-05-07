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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Problem;
import model.Answer;
import model.NavDAOException;
import model.Navigation;

public class FXMLpreguntasController implements Initializable {

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
    private int preguntasMostradas = 0;
    private int indicePreguntaActual = 0;
    @FXML
    private Button botoncarta;
    @FXML
    private Button botonmenu;
    @FXML
    private Button botonverif;
    @FXML
    private HBox hboxPregunta;

   

    private void cargarPreguntas() throws NavDAOException{
        Navigation navegacion = Navigation.getInstance();
        todasLasPreguntas = navegacion.getProblems();
        preguntasPendientes = new ArrayList<>(todasLasPreguntas);
    }
    private void mostrarPregunta() {
    Problem pregunta = todasLasPreguntas.get(0);
    List<Answer> respuestas = pregunta.getAnswers();
    labelPregunta.setText(pregunta.getText());
    opcionA.setText(respuestas.get(0).getText());
    opcionB.setText(respuestas.get(1).getText());
    opcionC.setText(respuestas.get(2).getText());
    opcionD.setText(respuestas.get(3).getText());

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

    private void handleSiguientePregunta() {
    if (indicePreguntaActual < preguntasPendientes.size() - 1 && preguntasMostradas < 4) {
        indicePreguntaActual++;
        preguntasMostradas++;
        mostrarPregunta();
    } else {
        mostrarFin();
    }
    }

    private void mostrarSiguientePregunta() {
       

        // Desseleccionar todas las opciones
        opcionA.setSelected(false);
        opcionB.setSelected(false);
        opcionC.setSelected(false);
        opcionD.setSelected(false);
    }

    private void mostrarFin() {
        labelPregunta.setText("Has completado las 4 preguntas.");
        opcionA.setVisible(false);
        opcionB.setVisible(false);
        opcionC.setVisible(false);
        opcionD.setVisible(false);
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
    private void guardarRespuestaSeleccionada() {
    
}

    private void handleAnteriorPregunta(ActionEvent event) {
        guardarRespuestaSeleccionada();
    if (indicePreguntaActual > 0) {
        indicePreguntaActual--;
        preguntasMostradas--;
        mostrarPregunta();
    }
    }

    @FXML
    private void salirmenu(ActionEvent event) {
    }

    @FXML
    private void verificarrespuesta(ActionEvent event) {
    }

    
}

