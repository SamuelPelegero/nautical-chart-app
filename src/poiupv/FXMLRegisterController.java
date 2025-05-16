/* REGISTER
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.YEARS;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.NavDAOException;
import model.Navigation;
import model.User;
import poiupv.FXMLMenuController;
import poiupv.Persona;

public class FXMLRegisterController implements Initializable {
    
    
    private boolean emailTouched = false;
    private boolean userTouched = false;
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
     private BooleanProperty validUser;
    
    // listener to register on textProperty() or valueProperty()
    private ChangeListener<String> listenerEmail;
    private ChangeListener<String> listenerPassword;
    private ChangeListener<String> listenerPassword2;
    private ChangeListener<String> listenerDate;
    private ChangeListener<String> listenerUser;
    @FXML
    private TextField userField;
    @FXML
    private Label userError;
    @FXML
    private Button interrogante1;
    @FXML
    private Button interrogante2;
    @FXML
    private Tooltip tooltip1;
    @FXML
    private Tooltip tooltip2;
    
    @FXML
    private Button bIniciarSesion;
    private ImageView ImageViewUserDefault;
    private ImageView ImageViewUser1;
    private ImageView ImageViewUser2;
    private ImageView imageViewUser3;
    private Image image;
    @FXML
    private Button btnSeleccionarAvatar;
    @FXML
    private Label lblRutaAvatar;
    @FXML
    private ImageView imgPreview;

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
        boolean isValid = email.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
        validEmail.set(isValid); //actualiza la property asociada
        showError(isValid, emailField, emailError);
    }
    
    private void checkUser() throws NavDAOException {
        Navigation navegacion = Navigation.getInstance();
        String user = userField.getText();
        boolean isValid = user.matches("^[a-zA-Z0-9 _-]{6,15}$") && !user.contains(" ") && !navegacion.exitsNickName(userField.getText()); //TODO: Se debe comprobar que el usuario no existe
        validUser.set(isValid); //actualiza la property asociada
        showError(isValid, userField, userError); //muestra o esconde el mensaje de error
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
private void handleBAcceptOnAction(ActionEvent event) throws NavDAOException {
    // Verificar que todos los campos sean válidos
    Navigation navegacion = Navigation.getInstance();
    if (validEmail.get() && validPassword.get() && confirmPasswords.get() && validDate.get() && validUser.get() && !navegacion.exitsNickName(userField.getText())) {
        System.out.println("✅ Registro exitoso!");
        
        navegacion.registerUser(userField.getText(),emailField.getText(), passwordField.getText(), image, dateField.getValue());
        
        
        // Aquí puedes agregar lógica para guardar el usuario, enviar datos, etc.
        User user = Navigation.getInstance().authenticate(userField.getText(), passwordField.getText());
        Persona.getInstance().setUser(user);
        
        
        // Opcional: Mostrar un mensaje en una etiqueta
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registro Exitoso");
        alert.setHeaderText(null);
        alert.setContentText("¡Tu registro ha sido completado correctamente!");
        alert.showAndWait();

        // Limpiar los campos después del registro
        emailField.clear();
        userField.clear();
        passwordField.clear();
        passwordConfirmField.clear();
        dateField.setValue(null);

        validEmail.set(false);
        validPassword.set(false);
        confirmPasswords.set(false);
        validDate.set(false);
        validUser.set(false);
    
        // Cerrar la ventana de registro
        Stage currentStage = (Stage) bAccept.getScene().getWindow();
        currentStage.close();

        // Abrir la interfaz principal (FXMLDocumentController)
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/poiupv/FXMLMenu.fxml"));
            Parent root = loader.load();
            FXMLMenuController controller = loader.getController();
            controller.setUser(user);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));
            stage.setTitle("Menú Principal");
            stage.setMinWidth(600);
            stage.setMinHeight(600);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    } else {
        // Si algún campo no es válido, mostrar un mensaje de error
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error en el Registro");
        alert.setHeaderText(null);
        alert.setContentText("Por favor, completa todos los campos correctamente.");
        alert.showAndWait();
    }
}
    
    
    //=========================================================
    // you must initialize here all related with the object 



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        image = imgPreview.getImage();
        
        
        
      
        
        tooltip1.install(interrogante1, tooltip1);
        tooltip2.install(interrogante2, tooltip2);
        interrogante1.setFocusTraversable(false);
        interrogante2.setFocusTraversable(false);
        
        // Evento al presionar el botón para mostrar el Tooltip
        interrogante1.setOnMouseClicked(event -> {
            // Obtener las coordenadas del mouse donde se hizo clic
            double mouseX = event.getScreenX();
            double mouseY = event.getScreenY();

            // Mostrar el tooltip manualmente donde el usuario hizo clic
            tooltip1.show(interrogante1, mouseX + 10, mouseY + 10); // Agregamos un pequeño offset para que no esté justo en el punto de clic

            // Establecer que el tooltip desaparezca automáticamente después de un tiempo (por ejemplo, 3 segundos)
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(e -> tooltip1.hide());
            pause.play();
         });
        
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
        validEmail = new SimpleBooleanProperty(false);
        validPassword = new SimpleBooleanProperty(false);
        confirmPasswords = new SimpleBooleanProperty(false);
        validDate = new SimpleBooleanProperty(false);
        validUser = new SimpleBooleanProperty(false);
       

         
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
    
     
        
        
        userField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) { // cuando pierde el foco
            userTouched = true;
                try {
                    checkUser();
                } catch (NavDAOException ex) {
                    Logger.getLogger(FXMLRegisterController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
            showError(true, userField, userError); // ocultamos el error al entrar
    }
        });
        
        userField.textProperty().addListener((obs, oldVal, newVal) -> {
        if (userTouched) {
            try {
                checkUser();
            } catch (NavDAOException ex) {
                Logger.getLogger(FXMLRegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        
        ((TextField) dateField.getEditor()).textProperty().addListener((obs, oldText, newText) -> {
    dateTouched = true;
    try {
        LocalDate parsedDate = dateField.getConverter().fromString(newText);
        dateField.setValue(parsedDate); // actualiza el valor para que los bindings funcionen
    } catch (Exception e) {
        // no hacer nada, el texto aún no es una fecha válida
        validDate.set(false);
        showError(false, dateField, dateError);
    }
});
        

    

// Escuchar cambios en la fecha para validar sin necesidad de perder el foco
dateField.valueProperty().addListener((obs, oldVal, newVal) -> {
    dateTouched = true;
    checkDate();
});

        BooleanBinding validFields = validEmail.and(validPassword)
                .and(confirmPasswords)
                .and(validDate)
                .and(validUser);

        bAccept.disableProperty().bind(Bindings.not(validFields));

        bCancel.setOnAction(event -> bCancel.getScene().getWindow().hide());
    }

    @FXML
    private void handleBIniciarSesionOnAction(ActionEvent event) {
        try {
        // Cargar la pantalla de inicio de sesión
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/poiupv/FXMLLogIn.fxml"));
        Parent root = loader.load();
        
        // Obtener la ventana actual y cerrarla (si es necesario)
        Stage currentStage = (Stage) bIniciarSesion.getScene().getWindow();
        currentStage.close(); // Cerrar ventana de registro
        
        // Crear el nuevo Stage para la ventana de inicio de sesión
        Stage newStage = new Stage();
        newStage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));
        newStage.setScene(new Scene(root));
        newStage.setResizable(false);
        newStage.setTitle("Iniciar Sesión");
        newStage.show();
        
    } catch (IOException e) {
        e.printStackTrace();
    }
        
    }

    private void userDefaultOnAction(ActionEvent event) {
        image = ImageViewUserDefault.getImage();
        
    }

    private void user1OnAction(ActionEvent event) {
        image = ImageViewUser1.getImage();
    }

    private void user3OnAction(ActionEvent event) {
        image = imageViewUser3.getImage();
    }

    private void user2OnAction(ActionEvent event) {
        image = ImageViewUser2.getImage();
    }

    @FXML
    private void seleccionarAvatar(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Seleccionar imagen de avatar");
    
    // Configurar filtros para imágenes
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif"),
        new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
    );
    
    // Establecer directorio inicial (opcional)
    fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    
    Stage stage = (Stage) btnSeleccionarAvatar.getScene().getWindow();
    File archivoSeleccionado = fileChooser.showOpenDialog(stage);
    
    if (archivoSeleccionado != null) {
        try {
            // Mostrar nombre del archivo (sin ruta completa)
            lblRutaAvatar.setText(archivoSeleccionado.getName());
            
            // Cargar y mostrar la imagen seleccionada
            Image imagen = new Image(archivoSeleccionado.toURI().toString());
            image = imagen;
            imgPreview.setImage(imagen);
            
        } catch (Exception e) {
            lblRutaAvatar.setText("Error al cargar la imagen");
            e.printStackTrace();
        }
    }
    }
 }
