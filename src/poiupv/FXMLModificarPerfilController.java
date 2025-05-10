/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.YEARS;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
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
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Navigation;
import model.User;

public class FXMLModificarPerfilController implements Initializable {
    
    
    private boolean emailTouched = false;
    private boolean passwordTouched = false;
    private boolean passwordConfirmTouched = false;
    private boolean dateTouched = false;


    @FXML
    private Label emailError;

     @FXML
    private Label passwordError, passwordConfirmError, dateError;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField passwordConfirmField;
    @FXML
    private DatePicker dateField;
    @FXML
    private Button bAccept;
    @FXML
    private Button bCancel;
 
    //properties to control valid fields values. 
    private BooleanProperty validEmail;
    private BooleanProperty validPassword;
    private BooleanProperty confirmPasswords;
    private BooleanProperty validDate;
    
    // listener to register on textProperty() or valueProperty()
    private ChangeListener<String> listenerEmail;
    private ChangeListener<String> listenerPassword;
    private ChangeListener<String> listenerPassword2;
    private ChangeListener<String> listenerDate;
    
    
    @FXML
    private Button interrogante2;

    @FXML
    private Tooltip tooltip2;
   
    @FXML
    private TextField userField;
    @FXML
    private Label userError;
    @FXML
    private ToggleButton userDefault;
    @FXML
    private ToggleGroup grupito;
    @FXML
    private ImageView ImageViewUserDefault;
    @FXML
    private ToggleButton user1;
    @FXML
    private ImageView ImageViewUser1;
    @FXML
    private ToggleButton user2;
    @FXML
    private ImageView ImageViewUser2;
    @FXML
    private ToggleButton user3;
    @FXML
    private ImageView imageViewUser3;

    private Image image;
  
    
    

    private void checkPassword() {
        String password = passwordField.getText();
        boolean isValid = password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%&*()\\-+=]).{8,20}$");
        validPassword.set(isValid); //actualiza la property asociada
        showError(isValid, passwordField, passwordError); //muestra o esconde el mensaje de error
    }
    private void checkPasswordsMatch() {
        boolean match = passwordField.getText().equals(passwordConfirmField.getText());
        confirmPasswords.set(match);
        showError(match, passwordConfirmField, passwordConfirmError);
    }
    private void checkEmail() {
        String email = emailField.getText();
//        boolean isValid = email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
        boolean isValid = email.matches("^[\\w!#$%&'*+/=?{|}~^-]+(?:\\.[\\w!#$%&'*+/=?{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
        validEmail.set(isValid); //actualiza la property asociada
        showError(isValid, emailField, emailError);
    }
    
   

    private void checkDate(){
    try{    
        LocalDate value = dateField.getValue();
        boolean isValid = value.isBefore(LocalDate.now().minus(16, YEARS));
        validDate.set(isValid);
        showError(isValid, dateField, dateError);
    } catch (Exception e) {validDate.set(false);
            showError(false, dateField, dateError);}    
    }
    
    
    private void showError(boolean isValid, Node field, Node errorMessage){
        errorMessage.setVisible(!isValid);
        field.setStyle(((isValid) ? "" : "-fx-background-color: #FCE5E0"));
    }
    
    @FXML
private void handleBAcceptOnAction(ActionEvent event) {
    // Verificar que los campos modificados sean validos
    

    if ( validEmail.get() && validPassword.get() && confirmPasswords.get() &&  validDate.get() ) {
        // Aquí puedes agregar lógica para guardar el usuario, enviar datos, etc.
        User user = Persona.getInstance().getUser();
        
        user.setEmail(emailField.getText());
        user.setPassword(passwordField.getText());
        user.setBirthdate(dateField.getValue());
        user.setAvatar(image);
        
        
        
        // Opcional: Mostrar un mensaje en una etiqueta
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modificación Exitosa");
        alert.setHeaderText(null);
        alert.setContentText("¡Tu perfil ha sido modificado correctamente!");
        alert.showAndWait();

        // Limpiar los campos después del registro
        emailField.clear();
        passwordField.clear();
        passwordConfirmField.clear();
        dateField.setValue(null);

        validEmail.set(true);
        validPassword.set(true);
        confirmPasswords.set(true);
        validDate.set(true);

        
        // Cerrar la ventana de registro
        Stage currentStage = (Stage) bAccept.getScene().getWindow();
        currentStage.close();

        // Abrir la interfaz principal (FXMLDocumentController)


    } else {
        // Si algún campo no es válido, mostrar un mensaje de error
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error en el Registro");
        alert.setHeaderText(null);
        alert.setContentText("Por favor, completa todos los campos correctamente.");
        alert.showAndWait();
    }
}
   // Método para comparar imágenes basado en sus píxeles
public boolean imagenesIguales(Image img1, Image img2) {
    if (img1 == null || img2 == null) return false;
    
    // Compara las dimensiones primero
    if ((int)img1.getWidth() != (int)img2.getWidth() || 
        (int)img1.getHeight() != (int)img2.getHeight()) {
        return false;
    }
    
    // Compara los píxeles (esto es más preciso pero requiere más procesamiento)
    for (int y = 0; y < img1.getHeight(); y++) {
        for (int x = 0; x < img1.getWidth(); x++) {
            if (img1.getPixelReader().getArgb(x, y) != img2.getPixelReader().getArgb(x, y)) {
                return false;
            }
        }
    }
    return true;
}

@Override
public void initialize(URL url, ResourceBundle rb) {
    User user = Persona.getInstance().getUser();
    System.out.println("Avatar del usuario: " + user.getAvatar());
    
    userField.setText(user.getNickName());
    emailField.setText(user.getEmail());
    passwordField.setText(user.getPassword());
    passwordConfirmField.setText(user.getPassword());
    dateField.setValue(user.getBirthdate());
    
    Platform.runLater(() -> {
        Image userAvatar = user.getAvatar();
        List<ToggleButton> toggleButtons = List.of(userDefault, user1, user2, user3);
        
        if (userAvatar != null) {
            for (ToggleButton tb : toggleButtons) {
                ImageView iv = (ImageView) tb.getGraphic();
                if (iv != null && iv.getImage() != null) {
                    // Comparación simplificada (puedes usar el método más preciso si es necesario)
                    if (imagenesIguales(userAvatar, iv.getImage())) {
                        tb.setSelected(true);
                        image = iv.getImage();
                        break;
                    }
                }
            }
        }
    });



        
     
       
        tooltip2.install(interrogante2, tooltip2);
        interrogante2.setFocusTraversable(false);
        
        
        
        // Evento al presionar el botón para mostrar el Tooltip
        interrogante2.setOnMouseClicked(event -> {
            // Obtener las coordenadas del mouse donde se hizo clic
            double mouseX = event.getScreenX();
            double mouseY = event.getScreenY();

            // Mostrar el tooltip manualmente donde el usuario hizo clic
            tooltip2.show(interrogante2, mouseX + 10, mouseY + 10); // Agregamos un pequeño offset para que no esté justo en el punto de clic

            // Establecer que el tooltip desaparezca automáticamente después de un tiempo (por ejemplo, 3 segundos)
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(e -> tooltip2.hide());
            pause.play();
         });
        
        

        
        
        // Inicializar propiedades antes de usarlas
        validEmail = new SimpleBooleanProperty(true);
        validPassword = new SimpleBooleanProperty(true);
        confirmPasswords = new SimpleBooleanProperty(true);
        validDate = new SimpleBooleanProperty(true);
        
        
        emailField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) { // cuando pierde el foco
            emailTouched = true;
            checkEmail();
            } else {
            showError(true, emailField, emailError); // ocultamos el error al entrar
            }
        });
        
        
        emailField.textProperty().addListener((obs, oldVal, newVal) -> {
    if (emailTouched) {
        checkEmail(); // Valida cada vez que cambia el texto si el campo ya fue tocado
    }
});
        
        
        

        passwordField.focusedProperty().addListener((obs, oldVal, newVal) -> {
        if (!newVal) { // cuando pierde el foco
            passwordTouched = true;
            checkPassword();
            checkPasswordsMatch(); // Comprobar si coinciden las contraseñas al perder el foco de passwordField
        } else {
            showError(true, passwordField, passwordError); // ocultar el error al entrar
        }
    });
        
         passwordField.textProperty().addListener((obs, oldVal, newVal) -> {
        if (passwordTouched) {
            checkPassword();
            checkPasswordsMatch(); // Comprobar si coinciden las contraseñas cuando se modifica passwordField
        }
    });

        passwordConfirmField.textProperty().addListener((obs, oldVal, newVal) -> {
        if (passwordConfirmTouched) {
            checkPasswordsMatch(); // Comprobar si coinciden las contraseñas cuando se modifica passwordConfirmField
        }
    });
        
        passwordConfirmField.focusedProperty().addListener((obs, oldVal, newVal) -> {
        if (!newVal) { // cuando pierde el foco
            passwordConfirmTouched = true;
            checkPasswordsMatch(); // Comprobar si coinciden las contraseñas al perder el foco de passwordConfirmField
        } else {
            showError(true, passwordConfirmField, passwordConfirmError); // ocultar el error al entrar
        }
    });

        dateField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) { // cuando pierde el foco
            dateTouched = true;
            checkDate();
            } else {
            showError(true, dateField, dateError); // ocultamos el error al entrar
    }
            
        });
        
        dateField.valueProperty().addListener((obs, oldVal, newVal) -> {
        if (dateTouched) {
        checkDate(); // Revalidamos en cuanto cambia el valor, si ya se ha tocado el campo
    }
});
        

        dateField.setConverter(new LocalDateStringConverter() {
            @Override
            public LocalDate fromString(String value) {
                try {
                    return super.fromString(value);
                } catch (Exception e) {
                    dateError.setVisible(true);
                    return null;
                }
            }
        });
        
        emailTouched = true;
        passwordTouched = true;
        passwordConfirmTouched = true;
        dateTouched = true;

        BooleanBinding validFields = validEmail.and(validPassword)
                .and(confirmPasswords)
                .and(validDate);
              

        bAccept.disableProperty().bind(Bindings.not(validFields));

    }

  @FXML
    private void handleBCancelOnAction(ActionEvent event) {
  
        // Obtener el Stage actual y cerrarlo (si es necesario)
        Stage currentStage = (Stage) bAccept.getScene().getWindow();
        currentStage.close(); // Cerrar ventana de login

    }

    @FXML
    private void userDefaultOnAction(ActionEvent event) {
        image = ImageViewUserDefault.getImage();
        
    }

    @FXML
    private void user1OnAction(ActionEvent event) {
        image = ImageViewUser1.getImage();
    }

    @FXML
    private void user3OnAction(ActionEvent event) {
        image = imageViewUser3.getImage();
    }

    @FXML
    private void user2OnAction(ActionEvent event) {
        image = ImageViewUser2.getImage();
    }
 }

