/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.YEARS;
import java.util.ResourceBundle;
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
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

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
    
    private void checkUser() {
        String user = userField.getText();
        boolean isValid = user.matches("^[a-zA-Z0-9 _-]{6,15}$") && !user.contains(" "); //TODO: Se debe comprobar que el usuario no existe
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
private void handleBAcceptOnAction(ActionEvent event) {
    // Verificar que todos los campos sean válidos
    if (validEmail.get() && validPassword.get() && confirmPasswords.get() && validDate.get() && validUser.get()) {
        System.out.println("✅ Registro exitoso!");
        // Aquí puedes agregar lógica para guardar el usuario, enviar datos, etc.

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/poiupv/FXMLDocument.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Interfaz Principal");
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
        tooltip1.install(interrogante1, tooltip1);
        tooltip2.install(interrogante2, tooltip2);
        interrogante1.setFocusTraversable(false);
        interrogante2.setFocusTraversable(false);
        
        
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
        
     
        
        
        userField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) { // cuando pierde el foco
            userTouched = true;
            checkUser();
            } else {
            showError(true, userField, userError); // ocultamos el error al entrar
    }
        });
        
        userField.textProperty().addListener((obs, oldVal, newVal) -> {
        if (userTouched) {
        checkUser();
        }
        });

        passwordField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) { // cuando pierde el foco
            passwordTouched = true;
            checkPassword();
             } else {
            showError(true, passwordField, passwordError); // ocultamos el error al entrar
    }
        });
        
        passwordField.textProperty().addListener((obs, oldVal, newVal) -> {
        if (passwordTouched) {
        checkPassword();
        }
        });

        passwordConfirmField.focusedProperty().addListener((obs, oldVal, newVal) -> {
             if (!newVal) { // cuando pierde el foco
            passwordConfirmTouched  = true;
            checkPasswordsMatch();
            } else {
            showError(true, passwordConfirmField, passwordConfirmError); // ocultamos el error al entrar
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

        BooleanBinding validFields = validEmail.and(validPassword)
                .and(confirmPasswords)
                .and(validDate)
                .and(validUser);

        bAccept.disableProperty().bind(Bindings.not(validFields));

        bCancel.setOnAction(event -> bCancel.getScene().getWindow().hide());
    }
 }


