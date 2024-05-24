/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
public class LoginController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    private String username = "";
    private String password = "";
    
    private Acount account;

    @FXML
    private Label errorLabel;
    @FXML
    private MenuButton languageMenuB;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

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
    private void loginUser(ActionEvent event) throws IOException {
        try {
            if (!account.logInUserByCredentials(username, password)) {
                errorLabel.textProperty().set("User or password are wrong");
                errorLabel.visibleProperty().set(true);
                return;
            }
        } catch (AcountDAOException ex) {
            System.out.println(ex);
        }
        
        FXRouter.goTo("home");
    }

    @FXML
    private void switchToSignup(ActionEvent event) throws IOException {
        FXRouter.goTo("signup");
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
            MenuItem auxMenuItem = new MenuItem();
            auxMenuItem.setGraphic(image);
            auxMenuItem.onActionProperty().set(e -> {
                FXRouter.setResourceBundle(auxLocale);
                FXRouter.reload();
            });
            languageMenuB.getItems().add(auxMenuItem);
        }
    }
    
}
