/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
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
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;


public class AuthenticationController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    private String username = "";
    private String password = "";
    private String name = "";
    private String mail = "";

    
    
    
    private Acount account;
    
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField mailField;
    @FXML
    private Label errorLabel;
    @FXML
    private MenuButton languageMenuB;
    @FXML
    private Label createLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label NameLabel;
    @FXML   
    private Label mailLabel;
    @FXML
    private Button signUpButton;
    @FXML
    private Label placeholderSLabel;
    @FXML
    private Button logInButton;
    
    @FXML
    public void switchToLogin(ActionEvent event) throws IOException{
        root  = FXMLLoader.load(getClass().getResource("Login.fxml"), ShifuApp.getResourceBundle());
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void switchToSignup(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("Signup.fxml"), ShifuApp.getResourceBundle());
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void loginUser(ActionEvent event) throws IOException {
        
        try {
            if (!account.logInUserByCredentials(username, password)) {
                errorLabel.textProperty().set("User or password are wrong");
                errorLabel.visibleProperty().set(true);
                return;
            }
        } catch (AcountDAOException ex) {
            System.out.println(ex);
        }
        
        root = FXMLLoader.load(getClass().getResource("Home.fxml"), ShifuApp.getResourceBundle());
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void signUpUser(ActionEvent event) throws IOException, AcountDAOException {
        if(name.equals("") || username.equals("") || password.equals("") || mail.equals("") ) {
            errorLabel.textProperty().set("Please fill all the fields");
            errorLabel.visibleProperty().set(true);
            return;
        }
        
        if(!account.registerUser(name, "", mail, username, password, 
            new Image("./resources/images/AvatarProvisional.png") , LocalDate.now())){
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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        try {
            account = Acount.getInstance();
        } catch (AcountDAOException | IOException ex) {
            System.out.println(ex);
        }
        initializeMenuButton();
    }

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
                    root = FXMLLoader.load(getClass().getResource("Login.fxml"), ShifuApp.getResourceBundle());
                } catch (IOException ex) {
                    Logger.getLogger(AuthenticationController.class.getName()).log(Level.SEVERE, null, ex);
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
