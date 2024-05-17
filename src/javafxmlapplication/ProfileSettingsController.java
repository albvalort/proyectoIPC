/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;
import model.Acount;
import model.AcountDAOException;

/**
 * FXML Controller classprofile settings des1
 *
 * @author migue
 */
public class ProfileSettingsController implements Initializable {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    private Acount account;
    private User currentUser;
    
    @FXML
    private Button backButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField mailTextField;
    @FXML
    private PasswordField oldPasswordTextField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Refill of TextFields
        
        try {
            account = Acount.getInstance();
        } catch (AcountDAOException | IOException ex) {
            Logger.getLogger(ProfileSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        currentUser = account.getLoggedUser();
        
        usernameTextField.setText(currentUser.getNickName());
        usernameTextField.setEditable(false);
        
        nameTextField.setText(currentUser.getName());
        surnameTextField.setText(currentUser.getSurname());
        mailTextField.setText(currentUser.getEmail());
    }    

    @FXML
    private void switchToHome(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
