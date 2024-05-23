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
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Acount;
import model.AcountDAOException;
import model.User;

/**
 * FXML Controller class
 *
 * @author migue
 */
public class HomeController implements Initializable {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    private boolean visibleToolBar = false;
    
    
    @FXML
    private Button profileButton;

    //private AnchorPane pane1;
    //private AnchorPane pane2;
    //private Button menu;
    
    @FXML
    private Button menuButton;
    @FXML
    private ToolBar toolBar;
    @FXML
    private Button visualizerButtonToolBar;
    @FXML
    private Button printButtonToolBar;
    @FXML
    private Button chargeManagerButtonToolBar;
    @FXML
    private Button categoryManagerButtonToolBar;
    @FXML
    private ImageView profileImage;
    
    private Acount account;
    private User currentUser;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            account = Acount.getInstance();
        } catch (AcountDAOException | IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        currentUser = account.getLoggedUser();
        menuButton.setOnMouseClicked(event-> {
            visibleToolBar = !visibleToolBar;
            toolBar.setVisible(visibleToolBar);
           
        });
        
        profileImage.setImage(currentUser.getImage());
        
    }    

    @FXML
    private void switchToProfileSettings(MouseEvent event) throws IOException {
        FXRouter.goTo("profile");
    }

    @FXML
    private void switchToPrint(MouseEvent event) throws IOException {
        FXRouter.goTo("print");
    }

    @FXML
    private void switchToChargeManager(ActionEvent event) throws IOException {
        FXRouter.goTo("chargeManager");
    }
    

   
        
        
    
    
}
