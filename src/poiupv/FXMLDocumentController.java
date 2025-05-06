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
    private ImageView imageTransportador;
    private double originalImageWidth;
    private double originalImageHeight;
    @FXML private ImageView imageview;
    @FXML private ToggleButton botonLinea;
    private double startX, startY;
    @FXML private ColorPicker colorPicker;
    @FXML private Spinner<Integer> spinnerTamanoTexto;
    @FXML private Slider sliderGrosorLinea;

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
        data.add(new Poi("1F", "Edificion del DSIC", 275, 250));
        data.add(new Poi("Agora", "Agora", 575, 350));
        data.add(new Poi("Pista", "Pista de atletismo y campo de futbol", 950, 350));
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

            textField.setOnAction(evt -> {
                Text text = new Text(e.getX(), e.getY() + 15, textField.getText());
                text.setFill(colorPicker.getValue()); // color
                text.setStyle("-fx-font-size: " + spinnerTamanoTexto.getValue() + "px;");
                paneImagen.getChildren().remove(textField);
                paneImagen.getChildren().add(text);
            });

            textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                if (!isNowFocused) {
                    Text text = new Text(e.getX(), e.getY() + 15, textField.getText());
                    text.setFill(colorPicker.getValue()); // color
                    text.setStyle("-fx-font-size: " + spinnerTamanoTexto.getValue() + "px;");
                    paneImagen.getChildren().remove(textField);
                    paneImagen.getChildren().add(text);
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
                    if (node instanceof Text || node instanceof Line) {
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
        } else {
            paneImagen.getChildren().remove(imageTransportador);
        }
    }

    private EventHandler<MouseEvent> pressedHandler = e -> {
        startX = e.getX();
        startY = e.getY();
    };

    private EventHandler<MouseEvent> releasedHandler = e -> {
    Line finalLine = new Line(startX, startY, e.getX(), e.getY());
    finalLine.setStroke(colorPicker.getValue());
    finalLine.setStrokeWidth(sliderGrosorLinea.getValue());
    paneImagen.getChildren().add(finalLine);
};

    @FXML
    private void handleBotonLineaOnAction(ActionEvent event) {
        if (botonLinea.isSelected()) {
            paneImagen.setOnMousePressed(pressedHandler);
            paneImagen.setOnMouseReleased(releasedHandler);
        } else {
            paneImagen.setOnMousePressed(null);
            paneImagen.setOnMouseReleased(null);
        }
    }
    
}
