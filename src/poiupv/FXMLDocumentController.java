/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;

import javafx.scene.paint.Color;
import static java.awt.PageAttributes.ColorType.COLOR;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import poiupv.Poi;

public class FXMLDocumentController implements Initializable {
    private final HashMap<String, Poi> hm = new HashMap<>();
    private ObservableList<Poi> data;
    private Group zoomGroup;

    @FXML private ListView<Poi> map_listview;
    @FXML private ScrollPane map_scrollpane;
    @FXML private Slider zoom_slider;
    @FXML private MenuButton map_pin;
    @FXML private MenuItem pin_info;
    private Label mousePosistion;
    @FXML private SplitPane splitPane;
    @FXML private Label mousePosition;
    @FXML private ToggleButton botonTexto;
    @FXML private Pane paneImagen;
    @FXML private ToggleButton toggleBotonBorrar;
    @FXML private ToggleGroup grupo;
    @FXML private ToggleButton botonTransportador;
    @FXML
    private ToggleButton botonCirculo;
    private ImageView imageTransportador;
    private double originalImageWidth;
    private double originalImageHeight;
    @FXML private ImageView imageview;
    @FXML private ToggleButton botonLinea;
    private double startX, startY;
    @FXML private ColorPicker colorPicker;
    @FXML private Spinner<Integer> spinnerTamanoTexto;
    @FXML private Slider sliderGrosorLinea;
    private Line linePainting;
    private Circle circlePainting;
    private double inicioXArc;
    private Line lineaDesdeTransportador;
    private double centroXTransportador, centroYTransportador;
    @FXML
    void zoomIn(ActionEvent event) {
        double sliderVal = zoom_slider.getValue();
        zoom_slider.setValue(sliderVal += 0.1);
    }

    @FXML
    void zoomOut(ActionEvent event) {
        double sliderVal = zoom_slider.getValue();
        zoom_slider.setValue(sliderVal + -0.1);
    }

    private void zoom(double scaleValue) {
        double scrollH = map_scrollpane.getHvalue();
        double scrollV = map_scrollpane.getVvalue();
        zoomGroup.setScaleX(scaleValue);
        zoomGroup.setScaleY(scaleValue);
        map_scrollpane.setHvalue(scrollH);
        map_scrollpane.setVvalue(scrollV);
    }

    @FXML
    void listClicked(MouseEvent event) {
        Poi itemSelected = map_listview.getSelectionModel().getSelectedItem();
        double mapWidth = zoomGroup.getBoundsInLocal().getWidth();
        double mapHeight = zoomGroup.getBoundsInLocal().getHeight();
        double scrollH = itemSelected.getPosition().getX() / mapWidth;
        double scrollV = itemSelected.getPosition().getY() / mapHeight;
        final Timeline timeline = new Timeline();
        final KeyValue kv1 = new KeyValue(map_scrollpane.hvalueProperty(), scrollH);
        final KeyValue kv2 = new KeyValue(map_scrollpane.vvalueProperty(), scrollV);
        final KeyFrame kf = new KeyFrame(Duration.millis(500), kv1, kv2);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        map_pin.setLayoutX(itemSelected.getPosition().getX());
        map_pin.setLayoutY(itemSelected.getPosition().getY());
        pin_info.setText(itemSelected.getDescription());
        map_pin.setVisible(true);
    }

    private void initData() {
        data = map_listview.getItems();
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spinnerTamanoTexto.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 72, 14)); // min=10, max=72, default=14
sliderGrosorLinea.setMin(1);
sliderGrosorLinea.setMax(10); // grosor máximo para líneas
sliderGrosorLinea.setValue(2); // valor por defecto
sliderGrosorLinea.setMaxWidth(160);
colorPicker.setValue(Color.BLACK); // valor por defecto

        
        
        initData();
        zoom_slider.setMin(0.2);
        zoom_slider.setMax(1.2);
        zoom_slider.setValue(0.7);
        zoom_slider.valueProperty().addListener((o, oldVal, newVal) -> zoom((Double) newVal));
        Group contentGroup = new Group();
        zoomGroup = new Group();
        contentGroup.getChildren().add(zoomGroup);
        zoomGroup.getChildren().add(map_scrollpane.getContent());
        map_scrollpane.setContent(contentGroup);
        grupo.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            paneImagen.setOnMousePressed(null);
            paneImagen.setOnMouseReleased(null);
            paneImagen.setOnMouseClicked(null);
        });
    }

    @FXML
    private void showPosition(MouseEvent event) {
        mousePosistion.setText("sceneX: " + (int) event.getSceneX() + ", sceneY: " + (int) event.getSceneY() + "\n"
                + "         X: " + (int) event.getX() + ",          Y: " + (int) event.getY());
    }

    private void closeApp(ActionEvent event) {
        ((Stage) zoom_slider.getScene().getWindow()).close();
    }

    @FXML
    private void about(ActionEvent event) {
        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        Stage dialogStage = (Stage) mensaje.getDialogPane().getScene().getWindow();
        dialogStage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));
        mensaje.setTitle("Acerca de");
        mensaje.setHeaderText("IPC - 2025");
        mensaje.showAndWait();
    }

    @FXML
    private void addPoi(MouseEvent event) {
        if (event.isControlDown()) {
            Dialog<Poi> poiDialog = new Dialog<>();
            poiDialog.setTitle("Nuevo POI");
            poiDialog.setHeaderText("Introduce un nuevo POI");
            Stage dialogStage = (Stage) poiDialog.getDialogPane().getScene().getWindow();
            dialogStage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));
            ButtonType okButton = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
            poiDialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);
            TextField nameField = new TextField();
            nameField.setPromptText("Nombre del POI");
            TextArea descArea = new TextArea();
            descArea.setPromptText("Descripción...");
            descArea.setWrapText(true);
            descArea.setPrefRowCount(5);
            VBox vbox = new VBox(10, new Label("Nombre:"), nameField, new Label("Descripción:"), descArea);
            poiDialog.getDialogPane().setContent(vbox);
            poiDialog.setResultConverter(dialogButton -> {
                if (dialogButton == okButton) {
                    return new Poi(nameField.getText().trim(), descArea.getText().trim(), 0, 0);
                }
                return null;
            });
            Optional<Poi> result = poiDialog.showAndWait();
            if (result.isPresent()) {
                Point2D localPoint = zoomGroup.sceneToLocal(event.getSceneX(), event.getSceneY());
                Poi poi = result.get();
                poi.setPosition(localPoint);
                map_listview.getItems().add(poi);
            }
        }
    }

    @FXML
private void handleBotonTextoOnAction(ActionEvent event) {
    if (botonTexto.isSelected()) {
        paneImagen.setOnMouseClicked(e -> {
            TextField textField = new TextField();
            textField.setLayoutX(e.getX());
            textField.setLayoutY(e.getY());
            paneImagen.getChildren().add(textField);
            textField.requestFocus();

             // Método para crear y reemplazar con un nodo Text
            EventHandler<ActionEvent> commitText = evt -> {
                String input = textField.getText().trim();
                if (!input.isEmpty()) {
                    Text text = new Text(e.getX(), e.getY() + 15, input);
                    text.setFill(colorPicker.getValue());
                    text.setStyle("-fx-font-size: " + spinnerTamanoTexto.getValue() + "px;");
                    paneImagen.getChildren().add(text);
                }
                paneImagen.getChildren().remove(textField);
            };

            textField.setOnAction(commitText);

            textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                if (!isNowFocused) {
                    commitText.handle(null);
                }
            });
        });
    } else {
        paneImagen.setOnMouseClicked(null);
    }
}

    

    @FXML
    private void handleToggleBotonBorrar(ActionEvent event) {
        if (toggleBotonBorrar.isSelected()) {
            paneImagen.setOnMouseClicked(e -> {
                for (Node node : new ArrayList<>(paneImagen.getChildren())) {
                    if (node instanceof Text || node instanceof Line || node instanceof Circle) {
                        if (node.getBoundsInParent().contains(e.getX(), e.getY())) {
                            paneImagen.getChildren().remove(node);
                            break;
                        }
                    }
                }
            });
        } else {
            paneImagen.setOnMouseClicked(null);
        }
    }

    @FXML
private void handleBotonTransportadorOnAction(ActionEvent event) {
    if (botonTransportador.isSelected()) {
        if (imageTransportador == null) {
            Image imagen = new Image(getClass().getResource("/resources/transportador.png").toExternalForm());
            originalImageWidth = imagen.getWidth();
            originalImageHeight = imagen.getHeight();
            imageTransportador = new ImageView(imagen);
            imageTransportador.setFitWidth(200);
            imageTransportador.setPreserveRatio(true);

            // Solo mover el transportador
            imageTransportador.setOnMousePressed(e -> {
                imageTransportador.setUserData(new double[]{e.getSceneX(), e.getSceneY(), imageTransportador.getLayoutX(), imageTransportador.getLayoutY()});
            });
            imageTransportador.setOnMouseDragged(e -> {
                double[] datos = (double[]) imageTransportador.getUserData();
                double deltaX = e.getSceneX() - datos[0];
                double deltaY = e.getSceneY() - datos[1];
                imageTransportador.setLayoutX(datos[2] + deltaX);
                imageTransportador.setLayoutY(datos[3] + deltaY);
            });
        }

        if (!paneImagen.getChildren().contains(imageTransportador)) {
            paneImagen.getChildren().add(imageTransportador);
            imageTransportador.setLayoutX(100);
            imageTransportador.setLayoutY(100);
        }

        // Escala proporcional al zoom de la carta
        imageview.fitWidthProperty().addListener((obs, oldWidth, newWidth) -> {
            double scaleFactor = newWidth.doubleValue() / originalImageWidth;
            imageTransportador.setScaleX(scaleFactor);
            imageTransportador.setScaleY(scaleFactor);
        });
        imageview.fitHeightProperty().addListener((obs, oldHeight, newHeight) -> {
            double scaleFactor = newHeight.doubleValue() / originalImageHeight;
            imageTransportador.setScaleX(scaleFactor);
            imageTransportador.setScaleY(scaleFactor);
        });

        // Sin dibujo ni líneas
        paneImagen.setOnMousePressed(null);
        paneImagen.setOnMouseDragged(null);

    } else {
        paneImagen.getChildren().remove(imageTransportador);
        imageTransportador = null;

        // Limpiar eventos si estaban presentes
        paneImagen.setOnMousePressed(null);
        paneImagen.setOnMouseDragged(null);
    }
}

    @FXML
private void handleBotonLineaOnAction(ActionEvent event) {
    // Verificamos si el botón de "Línea" está seleccionado en el ToggleGroup
    if (botonLinea.isSelected()) {
        // Manejador de MousePressed para crear la línea cuando el ratón es presionado
        paneImagen.setOnMousePressed(e -> {
            startX = e.getX();
            startY = e.getY();
            
            // Creamos la línea y la añadimos al paneImagen
            linePainting = new Line(startX, startY, startX, startY);
            linePainting.setStroke(colorPicker.getValue());  // Usamos el color seleccionado en el ColorPicker
            linePainting.setStrokeWidth(sliderGrosorLinea.getValue());  // Usamos el grosor seleccionado en el slider
            paneImagen.getChildren().add(linePainting);  // Añadimos la línea al contenedor
            
            // Configuración del menú contextual para eliminar la línea
            linePainting.setOnContextMenuRequested(ctx -> {
                ContextMenu menuContext = new ContextMenu();
                MenuItem borrarItem = new MenuItem("Eliminar");
                menuContext.getItems().add(borrarItem);
                
                // Acción para eliminar la línea
                borrarItem.setOnAction(ev -> {
                    paneImagen.getChildren().remove(linePainting);
                    ev.consume();
                });
                
                // Mostrar el menú contextual en la posición del ratón
                menuContext.show(linePainting, ctx.getScreenX(), ctx.getScreenY());
                ctx.consume();
            });
            
            e.consume();  // Consumimos el evento para evitar otros manejadores
        });

        // Manejador de MouseDragged para actualizar el punto final de la línea mientras se arrastra el ratón
        paneImagen.setOnMouseDragged(e -> {
            if (linePainting != null) {
                linePainting.setEndX(e.getX());
                linePainting.setEndY(e.getY());
            }
            e.consume();  // Consumimos el evento para evitar otros manejadores
        });

        // Manejador de MouseReleased para fijar la línea al soltar el ratón
        paneImagen.setOnMouseReleased(e -> {
            if (linePainting != null) {
                linePainting.setEndX(e.getX());
                linePainting.setEndY(e.getY());
            }
            e.consume();  // Consumimos el evento para evitar otros manejadores
        });

    } else {
        // Si el ToggleButton "Línea" está desactivado, eliminamos los manejadores de eventos
        paneImagen.setOnMousePressed(null);
        paneImagen.setOnMouseDragged(null);
        paneImagen.setOnMouseReleased(null);
    }
}
    // Manejador de eventos para el botón de Círculo
@FXML
private void handleBotonCirculoOnAction(ActionEvent event) {
    // Verificamos si el botón de "Círculo" está seleccionado en el ToggleGroup
    if (botonCirculo.isSelected()) {
        // Manejador de MousePressed para crear el círculo cuando el ratón es presionado
        paneImagen.setOnMousePressed(e -> {
            // Creamos un círculo transparente con radio 1 (mínimo visible)
            circlePainting = new Circle(1);
            circlePainting.setStroke(Color.RED);  // Usamos el color rojo para el borde
            circlePainting.setFill(Color.TRANSPARENT);  // Lo hacemos transparente por dentro
            paneImagen.getChildren().add(circlePainting);  // Añadimos el círculo al contenedor

            // Colocamos el centro del círculo en la posición del ratón
            circlePainting.setCenterX(e.getX());
            circlePainting.setCenterY(e.getY());

            // Guardamos la posición inicial para calcular el radio
            inicioXArc = e.getX();

            // Añadimos un menú contextual para eliminar el círculo
            circlePainting.setOnContextMenuRequested(ctx -> {
                ContextMenu menuContext = new ContextMenu();
                MenuItem borrarItem = new MenuItem("Eliminar");
                menuContext.getItems().add(borrarItem);
                
                // Acción para eliminar el círculo
                borrarItem.setOnAction(ev -> {
                    paneImagen.getChildren().remove(circlePainting);
                    ev.consume();
                });
                
                // Mostrar el menú contextual
                menuContext.show(circlePainting, ctx.getScreenX(), ctx.getScreenY());
                ctx.consume();
            });

            e.consume();  // Consumimos el evento para evitar otros manejadores
        });

        // Manejador de MouseDragged para modificar el radio del círculo mientras se arrastra el ratón
        paneImagen.setOnMouseDragged(e -> {
            if (circlePainting != null) {
                // Calculamos el radio como la distancia entre el centro y la posición del ratón
                double radio = Math.abs(e.getX() - inicioXArc);
                circlePainting.setRadius(radio);  // Establecemos el nuevo radio
            }
            e.consume();  // Consumimos el evento para evitar otros manejadores
        });

        // Manejador de MouseReleased para fijar el radio del círculo al soltar el ratón
        paneImagen.setOnMouseReleased(e -> {
            if (circlePainting != null) {
                // Calculamos y fijamos el radio definitivo al soltar el ratón
                double radio = Math.abs(e.getX() - inicioXArc);
                circlePainting.setRadius(radio);
            }
            e.consume();  // Consumimos el evento para evitar otros manejadores
        });

    } else {
        // Si el ToggleButton "Círculo" está desactivado, eliminamos los manejadores de eventos
        paneImagen.setOnMousePressed(null);
        paneImagen.setOnMouseDragged(null);
        paneImagen.setOnMouseReleased(null);
    }
}
}
