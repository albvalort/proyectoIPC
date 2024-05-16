/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.stage.Stage;
import javafx.util.Duration;

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
    private Button selectCategoryButtonPreView;
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
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO 
      
        
        menuButton.setOnMouseClicked(event-> {
            visibleToolBar = !visibleToolBar;
            toolBar.setVisible(visibleToolBar);
           
        });
        
        
        
        
        
        
        
        
        
        
        
        /** 
        Menu lateral con animacion
        pane1.setVisible(false);
        
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5),pane1);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
        
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), pane2);
        translateTransition.setByX(+600);
        translateTransition.play();
        
        menuButtonToolbar.setOnMouseClicked(event-> {
            pane1.setVisible(true);
            
            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5),pane1);
            fadeTransition1.setFromValue(0);
            fadeTransition1.setToValue(0.75);
            fadeTransition1.play();
        
            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
            translateTransition1.setByX(-600);
            translateTransition1.play(); 
            });
        
        pane1.setOnMouseClicked(event-> {
            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5),pane1);
            fadeTransition1.setFromValue(0.15);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();
            
            fadeTransition1.setOnFinished(event1 -> {
                pane1.setVisible(false);
            });
        
            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
            translateTransition1.setByX(+600);
            translateTransition1.play();     
            
        });
        */
    }    

    @FXML
    private void switchToProfileSettings(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ProfileSettings.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void switchToPrint(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Print.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void switchToChargeManager(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ChargeManager.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    

   
        
        
    
    
}