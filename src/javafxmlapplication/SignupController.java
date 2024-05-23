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
    private TextField nameField1;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        
        /**      
                BooleanBinding validFields = Bindings.and(validEmail, validPassword)
                 .and(equalPasswords);
        
        */
        
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
    private void mailInput(KeyEvent event) {
        mail = mailField.getText();
    }

    @FXML
    private void signUpUser(ActionEvent event) throws IOException, AcountDAOException {
        if(name.equals("") || username.equals("") || password.equals("") || mail.equals("") ) {
            errorLabel.textProperty().set("Please fill all the fields");
            errorLabel.visibleProperty().set(true);
            return;
        }
        
        if (!checkUsername(usernameField.getText())) {
            validUsername.setValue(Boolean.FALSE);
            showErrorMessage(errorLabel, usernameField);
            usernameField.requestFocus();
            errorLabel.textProperty().set("Please enter a valid username");
            errorLabel.visibleProperty().set(true);
            return;
        }
        
        validUsername.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel, usernameField);
        
        
        if (!checkPassword(passwordField.getText())){
            validPassword.setValue(Boolean.FALSE);
            showErrorMessage(errorLabel,passwordField);
            passwordField.requestFocus();
            errorLabel.textProperty().set("Please enter a valid password");
            errorLabel.visibleProperty().set(true);
            return;
        }
        
        validPassword.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel,passwordField);
        
        
        if(!checkEmail(mailField.getText())){
            validEmail.setValue(Boolean.FALSE);
            showErrorMessage(errorLabel,mailField);
            mailField.requestFocus();
            errorLabel.textProperty().set("Please enter a valid email");
            errorLabel.visibleProperty().set(true);
            return;
        }
        
        validEmail.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel,mailField);
        

        if(!account.registerUser(name, "", mail, username, password, 
            new Image("./resources/images/Avatar0.png") , LocalDate.now())){
            errorLabel.textProperty().set("User already exists, please change the field");
            errorLabel.visibleProperty().set(true);
            return;
        }

        
        root = FXMLLoader.load(getClass().getResource("Login.fxml"), ShifuApp.getResourceBundle());
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    

    @FXML
    private void switchToLogin(ActionEvent event) throws IOException{
        root  = FXMLLoader.load(getClass().getResource("Login.fxml"), ShifuApp.getResourceBundle());
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
//metodos para las restricciones de email y password 
    private void manageCorrect(Label errorLabel,TextField textField, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel,textField);
        
    }
    
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
       // Regex to check valid email. 
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
        ImageView imageMain = new ImageView("/resources/images/" + ShifuApp.getLocaleString() + ".png");
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
                ShifuApp.setLocale(auxLocale);
                try {
                    root = FXMLLoader.load(getClass().getResource("Signup.fxml"), ShifuApp.getResourceBundle());
                } catch (IOException ex) {
                    Logger.getLogger(SignupController.class.getName()).log(Level.SEVERE, null, ex);
                }
                stage = (Stage) languageMenuB.getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            });
            languageMenuB.getItems().add(auxMenuItem);
        }
    }
}
