package poiupv;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.NavDAOException;
import model.Navigation;
import model.User;

public class FXMLLogInController {

    @FXML
    private Button bAccept;

    @FXML
    private Button bCancel;

    @FXML
    private PasswordField passwordField;


    @FXML
    private TextField userField;
    @FXML
    private Label error;
    
    private User user;
    
    @FXML
    private Button bRegistrarse;
    
   
    
    @FXML
    void handleBAcceptOnAction(ActionEvent event) throws NavDAOException {
         // Obtener los valores ingresados por el usuario
        String username = userField.getText();
        String password = passwordField.getText();
         Navigation navegacion = Navigation.getInstance();
        // Verificar si las credenciales son correctas
        if (navegacion.authenticate(username, password) != null) { //TODO: Verificar el usuario y la contraseña
            // Si son correctas, procedemos a abrir la interfaz principal
            User user = Navigation.getInstance().authenticate(userField.getText(), passwordField.getText());
            Persona.getInstance().setUser(user);
           
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/poiupv/FXMLMenu.fxml"));
                Parent root = loader.load();
                FXMLMenuController controller = loader.getController();
                controller.setUser(user);
                
                // Obtener el Stage actual y cerrarlo (si es necesario)
                Stage currentStage = (Stage) bAccept.getScene().getWindow();
                currentStage.close(); // Cerrar ventana de login
                
                // Crear el nuevo Stage para la interfaz principal
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
            // Si las credenciales son incorrectas, mostrar el mensaje de error
            error.setText("Las credenciales introducidas no son válidas.");
            error.setVisible(true);  // Aseguramos que el mensaje se haga visible
            // Hacer que el mensaje desaparezca después de 3 segundos
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(event1 -> error.setVisible(false));  // Ocultar el mensaje de error
            pause.play();
        }
        
    }

    @FXML
    private void handleBCancellOnAction(ActionEvent event) {
        userField.getScene().getWindow().hide();
    }

    @FXML
    private void handleBRegistrarseOnAction(ActionEvent event) {
        try {
        // Cargar el archivo FXML del registro
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/poiupv/FXMLRegister.fxml"));
        Parent root = loader.load();

        // Obtener el Stage actual y cerrarlo (si es necesario)
        Stage currentStage = (Stage) bAccept.getScene().getWindow();
        currentStage.close(); // Cerrar ventana de login

        // Crear el nuevo Stage para la ventana de registro
        Stage newStage = new Stage();
        newStage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));
        newStage.setScene(new Scene(root));
        newStage.setResizable(false);
        newStage.setTitle("Registrarse");
        newStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    
    public void setUser(User user) {
    this.user = user;
    }
    
    @FXML
    public void initialize() {
        
        if (userField != null && passwordField != null && bAccept != null) {
            bAccept.disableProperty().bind(
                userField.textProperty().isEmpty()
                    .or(passwordField.textProperty().isEmpty())
            );
        }
    
    }
}