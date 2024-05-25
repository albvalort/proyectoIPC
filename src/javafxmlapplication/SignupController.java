/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;

/**
 * FXML Controller class
 *
 * @author migue
 */
public class SignupController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private String username = "";
    private String password = "";
    private String name = "";
    private String surname = "";
    private String mail = "";

    private Acount account;
    
    private BooleanProperty validEmail;
    private BooleanProperty validPassword;
    private BooleanProperty validUsername;
    
    @FXML
    private Label errorLabel;
    @FXML
    private Label createLabel;
    @FXML
    private MenuButton languageMenuB;
    @FXML
    private Label usernameLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private Label passwordLabel;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label NameLabel;
    @FXML
    private TextField nameField;
    @FXML
    private Label NameLabel1;
    @FXML
    private Label mailLabel;
    @FXML
    private TextField mailField;
    @FXML
    private Button signUpButton;
    @FXML
    private Label placeholderSLabel;
    @FXML
    private Button logInButton;

    private ResourceBundle rbundle;
    @FXML
    private TextField surnameField;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rbundle = rb;
        try {
            account = Acount.getInstance();
        } catch (AcountDAOException | IOException ex) {
            System.out.println(ex);
        }
        
        initializeMenuButton();
        
        validEmail = new SimpleBooleanProperty();
        validEmail.setValue(Boolean.FALSE);
        
        validPassword = new SimpleBooleanProperty();
        validPassword.setValue(Boolean.FALSE);
        
        validUsername = new SimpleBooleanProperty();
        validUsername.setValue(Boolean.FALSE);
        
        
        
        
    }    
    
    
    //All methods
    
    @FXML
    private void usernameInput(KeyEvent event) {
        username = usernameField.getText();
    }

    @FXML
    private void passwordInput(KeyEvent event) {
        password = passwordField.getText();
    }

    @FXML
    private void nameInput(KeyEvent event) {
        name = nameField.getText();
    }
    
    @FXML
    private void surnameInput(KeyEvent event) {
        surname = surnameField.getText();
    }

    @FXML
    private void mailInput(KeyEvent event) {
        mail = mailField.getText();
    }

    @FXML
    private void signUpUser(ActionEvent event) throws IOException, AcountDAOException {
        if(name.equals("") || username.equals("") || password.equals("") || mail.equals("") || surname.equals("") ) {
            errorLabel.textProperty().set(rbundle.getString("enterCredentials"));
            errorLabel.visibleProperty().set(true);
            return;
        }
        
        if (!checkUsername(usernameField.getText())) {
            validUsername.setValue(Boolean.FALSE);
            showErrorMessage(errorLabel, usernameField);
            usernameField.requestFocus();
            errorLabel.textProperty().set(rbundle.getString("validUsername"));
            errorLabel.visibleProperty().set(true);
            return;
        }
        
        validUsername.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel, usernameField);
        
        
        if (!checkPassword(passwordField.getText())){
            validPassword.setValue(Boolean.FALSE);
            showErrorMessage(errorLabel,passwordField);
            passwordField.requestFocus();
            errorLabel.textProperty().set(rbundle.getString("validPassword"));
            errorLabel.visibleProperty().set(true);
            return;
        }
        
        validPassword.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel,passwordField);
        
        
        if(!checkEmail(mailField.getText())){
            validEmail.setValue(Boolean.FALSE);
            showErrorMessage(errorLabel,mailField);
            mailField.requestFocus();
            errorLabel.textProperty().set(rbundle.getString("validEmail"));
            errorLabel.visibleProperty().set(true);
            return;
        }
        
        validEmail.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel,mailField);
        
        try {
            if(account.registerUser(name, surname, mail, username, password, 
                new Image("./resources/images/Avatar0.png") , LocalDate.now())){
                
                FXRouter.goTo("login");
                    
            }
        } catch (AcountDAOException ex) {
            errorLabel.textProperty().set(rbundle.getString("userExists"));
            errorLabel.visibleProperty().set(true);
            usernameField.clear();
            usernameField.requestFocus();
        }
               
    }
    

    @FXML
    private void switchToLogin(ActionEvent event) throws IOException{
        FXRouter.goTo("login");
    }
    
//metodos para las restricciones de email y password 
    
    private void showErrorMessage(Label errorLabel,TextField textField){
        errorLabel.visibleProperty().set(true);
        textField.styleProperty().setValue("-fx-background-color: #FCE5E0");    
    
    }
    
    private void hideErrorMessage(Label errorLabel,TextField textField){
        errorLabel.visibleProperty().set(false);
        textField.styleProperty().setValue("");
        
    }
    
    
     private static  Boolean checkUsername (String email){
        if(email == null){
          return false; 
        }
       // Regex to check valid username. 
        String regex = "^\\S*$";
        // Compile the ReGex 
        Pattern pattern = Pattern.compile(regex);
        // Match ReGex with value to check
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
     
    private static  Boolean checkEmail (String email){
        if(email == null){
          return false; 
        }
       // Regex to check valid email. 
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        // Compile the ReGex 
        Pattern pattern = Pattern.compile(regex);
        // Match ReGex with value to check
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    public static Boolean checkPassword(String password){     
  
        // If the password is empty , return false 
        if (password == null) { 
            return false; 
        } 
        // Regex to check valid password. 
        String regex =  "^[A-Za-z0-9]{7,16}$"; 
  
        // Compile the ReGex 
        Pattern pattern = Pattern.compile(regex); 
        // Match ReGex with value to check
        Matcher matcher = pattern.matcher(password); 
        return matcher.matches();
    
    }
    
    
    private void initializeMenuButton() {
        Locale[] availableLanguages = ShifuApp.availableLanguages;
        ImageView imageMain = new ImageView("/resources/images/" + FXRouter.getCurrentLocale() + ".png");
        imageMain.setFitWidth(30);
        imageMain.setFitHeight(20);
        languageMenuB.setGraphic(imageMain);
        for (int i = 0; i < availableLanguages.length; i++) {
            Locale auxLocale = availableLanguages[i];
            ImageView image = new ImageView("/resources/images/" + auxLocale.toString() + ".png");
            image.setFitWidth(30);
            image.setFitHeight(20);
            MenuItem auxMenuItem = new MenuItem("",image);
            auxMenuItem.onActionProperty().set(e -> {
                FXRouter.setResourceBundle(auxLocale);
                FXRouter.reload();
            });
            languageMenuB.getItems().add(auxMenuItem);
        }
    }

    
}
