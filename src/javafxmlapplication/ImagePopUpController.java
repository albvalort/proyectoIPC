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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.User;

/**
 * FXML Controller class
 *
 * @author albvalort
 */
public class ImagePopUpController implements Initializable {

    @FXML
    private GridPane gridPane;
    @FXML
    private Button addIconButton;
    @FXML
    private Button backButton;
    
    private Image selected;

    private Acount account = null;
    private User currentUser = null;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            account = Acount.getInstance();
        } catch (AcountDAOException | IOException ex) {
            Logger.getLogger(ImagePopUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        currentUser = account.getLoggedUser();
        
        
        for (int i = 0; i < 8; i++) {
            Image image = new Image("/resources/images/Avatar" + i + ".png");
            ImageView imageV= new ImageView(image);
            imageV.setFitWidth(100);
            imageV.setFitHeight(100);
            imageV.setOnMouseClicked(event -> {
                selected = image;
            });
            
            
            gridPane.add(imageV, i%3, i/3, 1, 1);
        }
        
        
    }    

    @FXML
    private void addIconPress(ActionEvent event) {
        currentUser.setImage(selected);
    }

    @FXML
    private void backPress(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
}
