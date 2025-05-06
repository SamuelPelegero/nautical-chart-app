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

private List<Pregunta> todasLasPreguntas;
    private List<Pregunta> preguntasPendientes;
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
            new Pregunta("¿Cuál es la posición del barco?",
                "A) 41°N 2°E", "B) 42°N 3°E", "C) 43°N 4°E", "D) 40°N 1°E"),
            new Pregunta("¿Qué significa esta boya?",
                "A) Peligro aislado", "B) Canal principal", "C) Aguas seguras", "D) Prohibido el paso"),
            new Pregunta("¿Qué indica una luz blanca intermitente?",
                "A) Faro", "B) Boya cardinal norte", "C) Canal este", "D) Punto de recalada"),
            new Pregunta("¿Cuál es la escala habitual de una carta náutica?",
                "A) 1:50000", "B) 1:25000", "C) 1:10000", "D) 1:200000"),
            new Pregunta("¿Qué carta usarías para navegación costera?",
                "A) General", "B) De recalada", "C) De aproximación", "D) Costera")
        );

        preguntasPendientes = new ArrayList<>(todasLasPreguntas);
    }
    private void mostrarPregunta(int indice) {
    Pregunta pregunta = preguntasPendientes.get(indice);
    labelPregunta.setText(pregunta.getTexto());
    opcionA.setText(pregunta.getOpcionA());
    opcionB.setText(pregunta.getOpcionB());
    opcionC.setText(pregunta.getOpcionC());
    opcionD.setText(pregunta.getOpcionD());

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
        mostrarPregunta(indicePreguntaActual);
    } else {
        mostrarFin();
    }
    }

    private void mostrarSiguientePregunta() {
        Pregunta pregunta = preguntasPendientes.remove(0);
        preguntasMostradas++;

        labelPregunta.setText(pregunta.getTexto());
        opcionA.setText(pregunta.getOpcionA());
        opcionB.setText(pregunta.getOpcionB());
        opcionC.setText(pregunta.getOpcionC());
        opcionD.setText(pregunta.getOpcionD());

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
        mostrarPregunta(indicePreguntaActual);
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
    Pregunta pregunta = preguntasPendientes.get(indicePreguntaActual);
    if (opcionA.isSelected()) {
        pregunta.setRespuestaSeleccionada("A");
    } else if (opcionB.isSelected()) {
        pregunta.setRespuestaSeleccionada("B");
    } else if (opcionC.isSelected()) {
        pregunta.setRespuestaSeleccionada("C");
    } else if (opcionD.isSelected()) {
        pregunta.setRespuestaSeleccionada("D");
    } else {
        pregunta.setRespuestaSeleccionada(null);
    }
}

    @FXML
    private void handleAnteriorPregunta(ActionEvent event) {
        guardarRespuestaSeleccionada();
    if (indicePreguntaActual > 0) {
        indicePreguntaActual--;
        preguntasMostradas--;
        mostrarPregunta(indicePreguntaActual);
    }
    }

    private static class Pregunta {
        private final String texto, opcionA, opcionB, opcionC, opcionD;
        
        private String respuestaSeleccionada = null;

        public String getRespuestaSeleccionada() {
            return respuestaSeleccionada;
        }

        public void setRespuestaSeleccionada(String respuestaSeleccionada) {
            this.respuestaSeleccionada = respuestaSeleccionada;
        }


        public Pregunta(String texto, String a, String b, String c, String d) {
            this.texto = texto;
            this.opcionA = a;
            this.opcionB = b;
            this.opcionC = c;
            this.opcionD = d;
        }

        public String getTexto() { return texto; }
        public String getOpcionA() { return opcionA; }
        public String getOpcionB() { return opcionB; }
        public String getOpcionC() { return opcionC; }
        public String getOpcionD() { return opcionD; }
    }
}

