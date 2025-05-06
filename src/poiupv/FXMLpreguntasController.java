/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;

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
import javafx.scene.control.*;
import javafx.fxml.FXML;
import model.Problem;
import model.Answer;

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
    private Button botonAnterior;
    @FXML
    private Button botonSiguiente;

   

    private void cargarPreguntas() {
        todasLasPreguntas = Arrays.asList(
            new Problem("¿Cuál es la posición del barco?",
                new Answer("A) 41°N 2°E", true), new Answer("B) 42°N 3°E",false), new Answer("C) 43°N 4°E", false), new Answer("D) 40°N 1°E",false)));

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

    @FXML
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
    public void initialize(URL url, ResourceBundle rb) {
        cargarPreguntas();
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

    @FXML
    private void handleAnteriorPregunta(ActionEvent event) {
        guardarRespuestaSeleccionada();
    if (indicePreguntaActual > 0) {
        indicePreguntaActual--;
        preguntasMostradas--;
        mostrarPregunta();
    }
    }

    
}

