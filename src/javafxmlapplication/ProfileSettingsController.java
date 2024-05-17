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
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.User;

/**
 * FXML Controller classprofile settings des1
 *
 * @author migue
 */
public class ProfileSettingsController implements Initializable {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    private Button backButton;
    @FXML
    private MenuButton languageMenuB;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField mailTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private PasswordField oldPasswordTextField;
    @FXML
    private PasswordField newPasswordTextField;
    @FXML
    private Button saveButton;

    private User currentUser;
    @FXML
    private TextField nameTextField;
    @FXML
    private ImageView profileImage;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeMenuButton();
        try {
            currentUser = Acount.getInstance().getLoggedUser();
        } catch (AcountDAOException | IOException ex) {
            Logger.getLogger(ProfileSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        usernameTextField.setText(currentUser.getNickName());
        mailTextField.setText(currentUser.getEmail());
        surnameTextField.setText(currentUser.getSurname());
        nameTextField.setText(currentUser.getName());
        profileImage.setImage(currentUser.getImage());
        
    }    

    @FXML
    private void switchToHome(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
                    root = FXMLLoader.load(getClass().getResource("ProfileSettings.fxml"), ShifuApp.getResourceBundle());
                } catch (IOException ex) {
                    Logger.getLogger(ProfileSettingsController.class.getName()).log(Level.SEVERE, null, ex);
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
