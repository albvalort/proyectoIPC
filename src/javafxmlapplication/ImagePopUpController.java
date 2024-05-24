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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.User;
import java.io.File;

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
    @FXML
    private ImageView selectedAvatarImageView;
    @FXML
    private Button button00;
    @FXML
    private Button button10;
    @FXML
    private Button button20;
    @FXML
    private Button button01;
    @FXML
    private Button button11;
    @FXML
    private Button button21;
    @FXML
    private Button button02;
    @FXML
    private Button button12;
    @FXML
    private Button button22;
    
    
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
        
        button00.setOnAction(event->{
            selected = new Image("/resources/images/Avatar0.png");
        });
        
        button10.setOnAction(event->{
            selected = new Image("/resources/images/Avatar1.png");
        });
        
        button20.setOnAction(event->{
            selected = new Image("/resources/images/Avatar2.png");
        });
        
        button01.setOnAction(event->{
            selected = new Image("/resources/images/Avatar3.png");
        });
        
        button11.setOnAction(event->{
            selected = new Image("/resources/images/Avatar4.png");
        });
        
        button21.setOnAction(event->{
            selected = new Image("/resources/images/Avatar5.png");
        });
        
        button02.setOnAction(event->{
            selected = new Image("/resources/images/Avatar6.png");
        });
        
        button12.setOnAction(event->{
            selected = new Image("/resources/images/Avatar7.png");
        });
        
        button12.setOnAction(event->{
            selected = new Image("/resources/images/Avatar7.png");
        });
        
        selectedAvatarImageView.setImage(currentUser.getImage());
        selected = currentUser.getImage();
        
        
    }    


    @FXML
    private void backPress(ActionEvent event) {
        currentUser.setImage(selected);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
        FXRouter.reload(); 
    }

    @FXML
    private void imageSelection(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.gif"));
        File selecteFile = fileChooser.showOpenDialog(addIconButton.getScene().getWindow());
        
        if(selecteFile != null){
            Image imageSelected = new Image(selecteFile.toURI().toString());
            selectedAvatarImageView.setImage(imageSelected);
            selected = imageSelected;
        }
    }
    
}
