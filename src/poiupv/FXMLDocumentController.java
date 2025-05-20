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
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import poiupv.Poi;

public class FXMLDocumentController implements Initializable {
    private final HashMap<String, Poi> hm = new HashMap<>();
    private ObservableList<Poi> data;
    private Group zoomGroup;

    private ListView<Poi> map_listview;
    @FXML private ScrollPane map_scrollpane;
    @FXML private Slider zoom_slider;
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
    @FXML
    private ToggleButton botonMarcarX;
    @FXML
    private Button botonBorrarTodo;
    @FXML
    private ToggleButton toggleBotonRegla;
    @FXML
    private ToggleButton toggleBotonCoordenadas;
    @FXML
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
    
     @FXML private ImageView imageRegla;
    private Line lineaHorizontal, lineaVertical;
    @FXML private Slider sliderTransportador;
    private double initialAngleRegla = 0;
    private double initialMouseAngle = 0;
    @FXML
    private ToggleButton brocha;
    private Group grupoRegla;
    
    private void inicializarRegla() {
     if (imageRegla == null) {
        Image imagenRegla = new Image(getClass().getResource("/resources/regla.png").toExternalForm());
         double originalImageWidthRegla = imagenRegla.getWidth();
         double originalImageHeightRegla = imagenRegla.getHeight();
        
        imageRegla = new ImageView(imagenRegla);
        imageRegla.setPreserveRatio(true);
        imageRegla.setOpacity(1);
        imageRegla.setPickOnBounds(true);
        imageRegla.setVisible(false);

        paneImagen.getChildren().add(imageRegla);
        
        // Aplicar zoom inicial inmediatamente
        double zoomInicial = sliderTransportador.getValue();
        double newWidth = originalImageWidthRegla * zoomInicial;
        imageRegla.setFitWidth(newWidth);
    }
       
    
}
private void inicializarTransportador() {
   if (imageTransportador == null) {
        Image imagenTransportador = new Image(getClass().getResource("/resources/transportador.png").toExternalForm());
       double originalImageWidthTransportador = imagenTransportador.getWidth();
       double originalImageHeightTransportador = imagenTransportador.getHeight();
        
        imageTransportador = new ImageView(imagenTransportador);
        imageTransportador.setPreserveRatio(true);
        imageTransportador.setOpacity(0.5);
        imageTransportador.setPickOnBounds(true);
        imageTransportador.setOnMousePressed(e -> {
            if (e.isPrimaryButtonDown()) {
                Point2D localPoint = paneImagen.sceneToLocal(e.getSceneX(), e.getSceneY());
                imageTransportador.setUserData(localPoint);
                e.consume();
            }
        });

        imageTransportador.setOnMouseDragged(e -> {
            Point2D dragStart = (Point2D) imageTransportador.getUserData();
            if (dragStart != null) {
                Point2D currentPoint = paneImagen.sceneToLocal(e.getSceneX(), e.getSceneY());
                double deltaX = currentPoint.getX() - dragStart.getX();
                double deltaY = currentPoint.getY() - dragStart.getY();
                imageTransportador.setLayoutX(imageTransportador.getLayoutX() + deltaX);
                imageTransportador.setLayoutY(imageTransportador.getLayoutY() + deltaY);
                imageTransportador.setUserData(currentPoint);
                e.consume();
            }
        });
        
        zoomTransportador(sliderTransportador.getValue());
        // Aplicar zoom inicial inmediatamente
        double zoomInicial = sliderTransportador.getValue();
        double newWidth = originalImageWidthTransportador * zoomInicial;
        imageTransportador.setFitWidth(newWidth);
    }
}

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         Group contentGroup = new Group();
    zoomGroup = new Group();
    contentGroup.getChildren().add(zoomGroup);
    if (map_scrollpane.getContent() != null) {
        zoomGroup.getChildren().add(map_scrollpane.getContent());
    }
    map_scrollpane.setContent(contentGroup);
    
  
        spinnerTamanoTexto.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 72, 14)); // min=10, max=72, default=14
sliderGrosorLinea.setMin(1);
sliderGrosorLinea.setMax(10); // grosor máximo para líneas
sliderGrosorLinea.setValue(2); // valor por defecto
sliderGrosorLinea.setMaxWidth(160);
colorPicker.setValue(Color.BLACK); // valor por defecto

    
        
        Image icono = new Image(getClass().getResource("/resources/transportador.png").toExternalForm());
    ImageView iconoView = new ImageView(icono);
    iconoView.setFitWidth(55);
    iconoView.setFitHeight(55);
    botonTransportador.setGraphic(iconoView);  // Esta imagen es solo del botón
    
    
    Image icono2 = new Image(getClass().getResource("/resources/regla.png").toExternalForm());
ImageView iconoView2 = new ImageView(icono2);
iconoView2.setFitWidth(55);
iconoView2.setFitHeight(55);
toggleBotonRegla.setGraphic(iconoView2);

     
    

        // Nuevas configuraciones

        //initData();
        zoom_slider.setMin(0.2);
        zoom_slider.setMax(1.2);
        zoom_slider.setValue(0.7);
        zoom_slider.valueProperty().addListener((o, oldVal, newVal) -> zoom((Double) newVal));
        
       
        
   
        
        sliderTransportador.setMaxWidth(160);
        sliderTransportador.setMin(5);
        sliderTransportador.setMax(15);
        sliderTransportador.setValue(7);
       
        
        
            inicializarRegla();
    inicializarTransportador();
    
     zoomTransportador(sliderTransportador.getValue());
     
     
     
      sliderTransportador.valueProperty().addListener((o, oldVal, newVal) -> zoomTransportador((Double) newVal));
       
     
            // Listener para manejar cambios entre modos
    grupo.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
        // Limpiar todos los eventos y estados
        // Limpiar selección al cambiar de modo
   
        // Restablecer modo
    modoActual = Modo.NINGUNO;
    
    // Limpiar eventos de mouse
    paneImagen.setOnMouseClicked(null);
    paneImagen.setOnMousePressed(null);
    paneImagen.setOnMouseDragged(null);
    paneImagen.setOnMouseReleased(null);
    
    // Limpiar campo de texto si existe
    if (textFieldActual != null) {
        paneImagen.getChildren().remove(textFieldActual);
        textFieldActual = null;
    }
    
    // Configurar el modo según el botón seleccionado
    if (newToggle == botonTexto) {
        modoActual = Modo.TEXTO;
        // Configurar evento para texto...
    } 
    else if (newToggle == botonMarcarX) {
        modoActual = Modo.MARCA_X;
        // Configurar evento para marcas X...
    }
    // ... otros modos
    

});
    
    imageTransportador.setOnMousePressed(e -> {
    if (e.isPrimaryButtonDown()) {
        // Guardamos la posición del ratón relativa al nodo para mantener la diferencia
        imageTransportador.setUserData(new Point2D(e.getX(), e.getY()));
        e.consume();
    }
});

imageTransportador.setOnMouseDragged(e -> {
    Point2D dragAnchor = (Point2D) imageTransportador.getUserData();
    if (dragAnchor != null) {
        double scale = zoomGroup.getScaleX();

        double newX = (e.getSceneX() - paneImagen.localToScene(0, 0).getX() - dragAnchor.getX()) / scale;
        double newY = (e.getSceneY() - paneImagen.localToScene(0, 0).getY() - dragAnchor.getY()) / scale;

        // ❗️ No permitir mover más a la izquierda del borde izquierdo (x >= 0)
        if (newX < 0) newX = 0;
        // Limitar hacia arriba
        if (newY < 0) newY = 0;
        
        imageTransportador.setLayoutX(newX);
        imageTransportador.setLayoutY(newY);
        e.consume();
    }
});


imageRegla.setOnMousePressed(e -> {
    if (e.isSecondaryButtonDown()) {
        // Centro real de la imagen dentro del zoomGroup
        double centerX = imageRegla.getLayoutX() + imageRegla.getBoundsInLocal().getWidth() / 2;
        double centerY = imageRegla.getLayoutY() + imageRegla.getBoundsInLocal().getHeight() / 2;

        Point2D centerScene = imageRegla.localToScene(centerX - imageRegla.getLayoutX(), centerY - imageRegla.getLayoutY());
        Point2D mouseScene = new Point2D(e.getSceneX(), e.getSceneY());

        initialMouseAngle = Math.atan2(mouseScene.getY() - centerScene.getY(), mouseScene.getX() - centerScene.getX());
        initialAngleRegla = imageRegla.getRotate();
    }

    if (e.isPrimaryButtonDown()) {
        Point2D localPoint = zoomGroup.sceneToLocal(e.getSceneX(), e.getSceneY());
        imageRegla.setUserData(localPoint);
    }

    e.consume();
});

imageRegla.setOnMouseDragged(e -> {
    if (e.isSecondaryButtonDown()) {
        // Rotación con botón derecho
        double centerX = imageRegla.getLayoutX() + imageRegla.getBoundsInLocal().getWidth() / 2;
        double centerY = imageRegla.getLayoutY() + imageRegla.getBoundsInLocal().getHeight() / 2;

        Point2D centerScene = imageRegla.localToScene(centerX - imageRegla.getLayoutX(), centerY - imageRegla.getLayoutY());
        Point2D mouseScene = new Point2D(e.getSceneX(), e.getSceneY());

        double currentMouseAngle = Math.atan2(mouseScene.getY() - centerScene.getY(), mouseScene.getX() - centerScene.getX());
        double deltaAngle = Math.toDegrees(currentMouseAngle - initialMouseAngle);

        imageRegla.setRotate(initialAngleRegla + deltaAngle);
    }

    if (e.isPrimaryButtonDown()) {
        Point2D dragStart = (Point2D) imageRegla.getUserData();
        if (dragStart != null) {
            Point2D localPoint = zoomGroup.sceneToLocal(e.getSceneX(), e.getSceneY());
            double deltaX = localPoint.getX() - dragStart.getX();
            double deltaY = localPoint.getY() - dragStart.getY();
            double newLayoutX = imageRegla.getLayoutX() + deltaX;
            double newLayoutY = imageRegla.getLayoutY() + deltaY;

            if (newLayoutX < 0) newLayoutX = 0;
            if (newLayoutY < 0) newLayoutY = 0;
            imageRegla.setLayoutX(newLayoutX);
            imageRegla.setLayoutY(newLayoutY);
            imageRegla.setUserData(localPoint);
        }
    }

    e.consume();
});


    }

    
@FXML
private void handleBotonReglaOnAction(ActionEvent event) {
    inicializarRegla();

    if (toggleBotonRegla.isSelected()) {
        if (!paneImagen.getChildren().contains(imageRegla)) {
            paneImagen.getChildren().add(imageRegla);
        }
        imageRegla.setVisible(true);
        imageRegla.setLayoutX(100);
        imageRegla.setLayoutY(100);
        imageRegla.toFront();
    } else {
        imageRegla.setVisible(false);
    }
}


@FXML
private void handleBotonTransportadorOnAction(ActionEvent event) {
    inicializarTransportador();
    if (botonTransportador.isSelected()) {
        if (!paneImagen.getChildren().contains(imageTransportador)) {
            paneImagen.getChildren().add(imageTransportador);
            imageTransportador.setLayoutX(100);
            imageTransportador.setLayoutY(100);
        }
    } else {
        paneImagen.getChildren().remove(imageTransportador);
    }
}

@FXML
private void brochaOnAction(ActionEvent event) {
    if (brocha.isSelected()) {
        // Desactivar otras herramientas
       
        // Configurar el comportamiento de la brocha (cambiar color)
        paneImagen.setOnMouseClicked(e -> {
            // Buscar el elemento más arriba en la jerarquía que contiene el punto clickeado
            for (int i = paneImagen.getChildren().size() - 1; i >= 0; i--) {
                Node nodo = paneImagen.getChildren().get(i);
                
                // Verificar si el nodo contiene el punto clickeado
                if (nodo.getBoundsInParent().contains(e.getX(), e.getY())) {
                    // Cambiar color según el tipo de nodo
                    if (nodo instanceof Shape) {
                        ((Shape) nodo).setStroke(colorPicker.getValue());
                        if (((Shape) nodo).getFill() != null && !((Shape) nodo).getFill().equals(Color.TRANSPARENT)) {
                            ((Shape) nodo).setFill(colorPicker.getValue());
                        }
                    } 
                    else if (nodo instanceof Text) {
                        ((Text) nodo).setFill(colorPicker.getValue());
                    }
                    else if (nodo instanceof Group && "marcaX".equals(nodo.getUserData())) {
                        // Caso especial para las marcas X (que son Groups con líneas)
                        for (Node child : ((Group) nodo).getChildren()) {
                            if (child instanceof Line) {
                                ((Line) child).setStroke(colorPicker.getValue());
                            }
                        }
                    }
                    break; // Solo cambiar el color del elemento superior
                }
            }
        });
    } else {
        // Desactivar el comportamiento de la brocha
        paneImagen.setOnMouseClicked(null);
    }
}
    
    private enum Modo { TEXTO, MARCA_X, NINGUNO, SELECCION }
private Modo modoActual = Modo.NINGUNO;
private TextField textFieldActual = null;
private List<Node> marcasX = new ArrayList<>();

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
    
    private void zoomTransportador(double scaleValue) {
            
    double escala = Math.max(1, Math.min(scaleValue, 15));

    if (imageRegla != null ) {
        imageRegla.setScaleX(escala);
        imageRegla.setScaleY(escala);
    }

    if (imageTransportador != null ) {
        imageTransportador.setScaleX(escala);
        imageTransportador.setScaleY(escala);
    }
        
    }

  
    
    

    @FXML
    private void showPosition(MouseEvent event) {
        mousePosition.setText("sceneX: " + (int) event.getSceneX() + ", sceneY: " + (int) event.getSceneY() + "\n"
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
        modoActual = Modo.TEXTO;
        // Desactivar otros modos
        botonMarcarX.setSelected(false);
        botonLinea.setSelected(false);
        botonCirculo.setSelected(false);
        toggleBotonBorrar.setSelected(false);
        
        paneImagen.setOnMouseClicked(e -> {
            // Limpiar cualquier campo de texto existente
            if (textFieldActual != null) {
                paneImagen.getChildren().remove(textFieldActual);
            }
            
            // Crear nuevo campo de texto
            textFieldActual = new TextField();
            textFieldActual.setLayoutX(e.getX());
            textFieldActual.setLayoutY(e.getY());
            
            // Aplicar tamaño y color inicial
            actualizarEstiloTextField(textFieldActual);
            
            // Actualizar estilo dinámicamente si cambian tamaño o color (opcional)
            spinnerTamanoTexto.valueProperty().addListener((obs, oldVal, newVal) -> actualizarEstiloTextField(textFieldActual));
            colorPicker.valueProperty().addListener((obs, oldVal, newVal) -> actualizarEstiloTextField(textFieldActual));
            
            paneImagen.getChildren().add(textFieldActual);
            textFieldActual.requestFocus();

            // Configurar comportamiento al confirmar texto
            textFieldActual.setOnAction(ev -> finalizarTexto());
            textFieldActual.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                if (!isNowFocused) finalizarTexto();
            });
        });
    } else {
        modoActual = Modo.NINGUNO;
        paneImagen.setOnMouseClicked(null);
        if (textFieldActual != null) {
            paneImagen.getChildren().remove(textFieldActual);
            textFieldActual = null;
        }
    }
}

private void finalizarTexto() {
    if (textFieldActual != null && !textFieldActual.getText().isEmpty()) {
        Text text = new Text(textFieldActual.getLayoutX(), 
                           textFieldActual.getLayoutY() + 15, 
                           textFieldActual.getText());
        text.setFill(colorPicker.getValue());
        text.setStyle("-fx-font-size: " + spinnerTamanoTexto.getValue() + "px;");
        paneImagen.getChildren().add(text);
    }
    
    if (textFieldActual != null) {
        paneImagen.getChildren().remove(textFieldActual);
        textFieldActual = null;
    }
}

// Método auxiliar para actualizar tamaño y color del TextField
private void actualizarEstiloTextField(TextField tf) {
    int tamano = spinnerTamanoTexto.getValue();
    Color color = colorPicker.getValue();
    tf.setStyle("-fx-font-size: " + tamano + "px; -fx-text-fill: " + toRgbString(color) + ";");
}

// Convierte un Color a un string RGB para CSS
private String toRgbString(Color c) {
    return String.format("rgb(%d,%d,%d)", 
        (int)(c.getRed() * 255), 
        (int)(c.getGreen() * 255), 
        (int)(c.getBlue() * 255));
}



    
@FXML
private void handleToggleBotonBorrar(ActionEvent event) {
    if (toggleBotonBorrar.isSelected()) {
        // Modo borrar activado
        paneImagen.setOnMouseClicked(e -> {
            // Buscar en orden inverso (de arriba hacia abajo)
            for (int i = paneImagen.getChildren().size() - 1; i >= 0; i--) {
                Node node = paneImagen.getChildren().get(i);
                
                // Verificar si es una marca X
                if (node instanceof Group && "marcaX".equals(node.getUserData())) {
                    Point2D localCoords = node.sceneToLocal(e.getSceneX(), e.getSceneY());
                    if (node.getBoundsInLocal().contains(localCoords)) {
                        paneImagen.getChildren().remove(node);
                        marcasX.remove(node);
                        break;
                    }
                }
                // Verificar otros elementos (líneas, círculos, texto)
                else if ((node instanceof Shape || node instanceof Text) 
                         && node.getBoundsInParent().contains(e.getX(), e.getY())) {
                    paneImagen.getChildren().remove(node);
                    break;
                }
            }
        });
    } else {
        // Desactivar modo borrar
        paneImagen.setOnMouseClicked(null);
    }
}

       

    @FXML
    private void handleBotonLineaOnAction(ActionEvent event) {
    if (botonLinea.isSelected()) {
        modoActual = Modo.NINGUNO;
        

        // 🚫 Hacer temporales todos los nodos no interactuables (solo para evitar moverlos)
        for (Node node : paneImagen.getChildren()) {
            if (!(node instanceof ImageView)) {
                node.setMouseTransparent(true);
            }
        }

        paneImagen.setOnMousePressed(e -> {
            startX = e.getX();
            startY = e.getY();

            linePainting = new Line(startX, startY, startX, startY);
            linePainting.setStroke(colorPicker.getValue());
            linePainting.setStrokeWidth(sliderGrosorLinea.getValue());
            linePainting.setMouseTransparent(true); // Para que la línea en sí tampoco interfiera
            paneImagen.getChildren().add(linePainting);

            linePainting.setOnContextMenuRequested(ctx -> {
                ContextMenu menuContext = new ContextMenu();
                MenuItem borrarItem = new MenuItem("Eliminar");
                borrarItem.setOnAction(ev -> {
                    paneImagen.getChildren().remove(linePainting);
                    ev.consume();
                });
                menuContext.getItems().add(borrarItem);
                menuContext.show(linePainting, ctx.getScreenX(), ctx.getScreenY());
                ctx.consume();
            });

            e.consume();
        });

        paneImagen.setOnMouseDragged(e -> {
            if (linePainting != null) {
                double endX = Math.max(0, e.getX());
                double endY = Math.max(0, e.getY());
                linePainting.setEndX(endX);
                linePainting.setEndY(endY);
            }
            e.consume();
        });

        paneImagen.setOnMouseReleased(e -> {
            if (linePainting != null) {
                double endX = Math.max(0, e.getX());
                double endY = Math.max(0, e.getY());
                linePainting.setEndX(endX);
                linePainting.setEndY(endY);
                linePainting = null;

                // ✅ Restaurar interacción normal al terminar de pintar
                for (Node node : paneImagen.getChildren()) {
                    node.setMouseTransparent(false);
                }
            }
            e.consume();
        });

    } else {
        // ❌ Modo desactivado, restauramos todo
        paneImagen.setOnMousePressed(null);
        paneImagen.setOnMouseDragged(null);
        paneImagen.setOnMouseReleased(null);

        for (Node node : paneImagen.getChildren()) {
            node.setMouseTransparent(false);
        }
    }
}
    // Manejador de eventos para el botón de Círculo
@FXML
private void handleBotonCirculoOnAction(ActionEvent event) {
    if (botonCirculo.isSelected()) {
        // Limpiar campo de texto si existe
        if (textFieldActual != null) {
            paneImagen.getChildren().remove(textFieldActual);
            textFieldActual = null;
        }
        paneImagen.setOnMousePressed(e -> {
            double centerX = Math.max(0, e.getX());
            double centerY = Math.max(0, e.getY());
            
            circlePainting = new Circle(1);

            // Aplicar color y grosor
            circlePainting.setStroke(colorPicker.getValue());
            circlePainting.setStrokeWidth(sliderGrosorLinea.getValue());

            // El relleno lo dejamos transparente
            circlePainting.setFill(Color.TRANSPARENT);

            circlePainting.setCenterX(centerX);
            circlePainting.setCenterY(centerY);
            inicioXArc = centerX; // para calcular radio

            paneImagen.getChildren().add(circlePainting);

            // Menú contextual para borrar
            circlePainting.setOnContextMenuRequested(ctx -> {
                ContextMenu menuContext = new ContextMenu();
                MenuItem borrarItem = new MenuItem("Eliminar");
                borrarItem.setOnAction(ev -> {
                    paneImagen.getChildren().remove(circlePainting);
                    ev.consume();
                });
                menuContext.getItems().add(borrarItem);
                menuContext.show(circlePainting, ctx.getScreenX(), ctx.getScreenY());
                ctx.consume();
            });
        });

        paneImagen.setOnMouseDragged(e -> {
             if (circlePainting != null) {
                double mouseX = Math.max(0, e.getX());
                double radius = Math.abs(mouseX - inicioXArc);

                // Evitar que el círculo se dibuje hacia la izquierda del límite
                double minX = circlePainting.getCenterX() - radius;
                if (minX < 0) {
                    radius = circlePainting.getCenterX(); // Limita el radio
                }

                circlePainting.setRadius(radius);
                e.consume();
            }
        });
    } else {
        paneImagen.setOnMousePressed(null);
        paneImagen.setOnMouseDragged(null);
    }
}

@FXML
private void handleBotonMarcarXOnAction(ActionEvent event) {
    if (botonMarcarX.isSelected()) {
        modoActual = Modo.MARCA_X;
        // Limpiar campo de texto si existe
        if (textFieldActual != null) {
            paneImagen.getChildren().remove(textFieldActual);
            textFieldActual = null;
        }
        
        // Configurar el evento del pane solo una vez
        paneImagen.setOnMouseClicked(this::manejarClickEnPane);
    } else {
        modoActual = Modo.NINGUNO;
        paneImagen.setOnMouseClicked(null);
    }
}

private void manejarClickEnPane(MouseEvent e) {
    if (modoActual == Modo.MARCA_X) {
        crearMarcaX(e.getX(), e.getY());
    }
}

private void crearMarcaX(double x, double y) {
    double size = 15;
    
    // Crear las líneas de la X
    Line line1 = new Line(0, 0, size*2, size*2);
    Line line2 = new Line(0, size*2, size*2, 0);
    
    line1.setStroke(colorPicker.getValue());
    line2.setStroke(colorPicker.getValue());
    line1.setStrokeWidth(sliderGrosorLinea.getValue());
    line2.setStrokeWidth(sliderGrosorLinea.getValue());

    // Crear un grupo en lugar de un Pane para mejor manejo
    Group marcaX = new Group(line1, line2);
    marcaX.setLayoutX(x - size);
    marcaX.setLayoutY(y - size);
    marcaX.setUserData("marcaX");
    
    // Configurar eventos para la marca X
    marcaX.setOnMouseClicked(this::manejarClickEnMarcaX);
    
   

    paneImagen.getChildren().add(marcaX);
    marcasX.add(marcaX); // Añadir a la lista de marcas
}

private void manejarClickEnMarcaX(MouseEvent e) {
    if (e.getClickCount() == 2) { // Doble click para cambiar color
        Node marca = (Node) e.getSource();
        if (marca instanceof Group) {
            for (Node child : ((Group) marca).getChildren()) {
                if (child instanceof Line) {
                    ((Line) child).setStroke(colorPicker.getValue());
                }
            }
        }
    } else if (e.isSecondaryButtonDown()) { // Click derecho para borrar
        Node marca = (Node) e.getSource();
        paneImagen.getChildren().remove(marca);
        marcasX.remove(marca);
        e.consume();
    }
}
  @FXML
private void handleBotonBorrarTodo(ActionEvent event) {
    // Crear una copia de la lista para evitar ConcurrentModificationException
    List<Node> copiaMarcas = new ArrayList<>(marcasX);
    
    // Eliminar todas las marcas del pane
    for (Node marca : copiaMarcas) {
        paneImagen.getChildren().remove(marca);
    }
    
    // Limpiar la lista
    marcasX.clear();
    
    // También eliminar otros elementos dibujados (opcional)
    paneImagen.getChildren().removeIf(node ->
        (node instanceof Shape || node instanceof Text) &&
        node != imageview &&
        node != imageRegla &&
        node != imageTransportador
    );
    
    // Limpiar variables de estado
   
    if (textFieldActual != null) {
        paneImagen.getChildren().remove(textFieldActual);
        textFieldActual = null;
    }
}
    

    @FXML
private void handleBotonCoordenadasOnAction(ActionEvent event) {
    if (toggleBotonCoordenadas.isSelected()) {
        paneImagen.setOnMouseClicked(e -> {
            if (lineaHorizontal != null) paneImagen.getChildren().remove(lineaHorizontal);
            if (lineaVertical != null) paneImagen.getChildren().remove(lineaVertical);

            lineaHorizontal = new Line(0, e.getY(), paneImagen.getWidth(), e.getY());
            lineaVertical = new Line(e.getX(), 0, e.getX(), paneImagen.getHeight());

            lineaHorizontal.setStroke(Color.BLUE);
            lineaVertical.setStroke(Color.BLUE);
            lineaHorizontal.setStrokeWidth(1);
            lineaVertical.setStrokeWidth(1);

            paneImagen.getChildren().addAll(lineaHorizontal, lineaVertical);
        });
    } else {
        paneImagen.setOnMouseClicked(null);
        if (lineaHorizontal != null) paneImagen.getChildren().remove(lineaHorizontal);
        if (lineaVertical != null) paneImagen.getChildren().remove(lineaVertical);
    }
}

    
    private void configurarDobleClic() {
    paneImagen.setOnMouseClicked(e -> {
        if (e.getClickCount() == 2) { // Doble clic
            for (Node node : paneImagen.getChildren()) {
                if (node.getBoundsInParent().contains(e.getX(), e.getY())) {
                    if (node instanceof Shape) {
                        ((Shape) node).setStroke(colorPicker.getValue());
                    } 
                    else if (node instanceof Text) {
                        ((Text) node).setFill(colorPicker.getValue());
                    }
                    else if (node instanceof Pane && "marcaX".equals(node.getUserData())) {
                        Pane marcaX = (Pane) node;
                        for (Node linea : marcaX.getChildren()) {
                            if (linea instanceof Line) {
                                ((Line) linea).setStroke(colorPicker.getValue());
                            }
                        }
                    }
                }
            }
        }
    });
}
    
    private void limpiarEventos() {
    paneImagen.setOnMouseClicked(null);
    paneImagen.setOnMousePressed(null);
    paneImagen.setOnMouseDragged(null);
    paneImagen.setOnMouseReleased(null);
    
    // Restaurar interacción con otros elementos
    for (Node node : paneImagen.getChildren()) {
        node.setMouseTransparent(false);
    }
    
    // Limpiar referencias a elementos temporales
    linePainting = null;
    circlePainting = null;
    if (textFieldActual != null) {
        paneImagen.getChildren().remove(textFieldActual);
        textFieldActual = null;
    }
}
    
}