/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

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
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author migue
 */
public class HomeController implements Initializable {

    @FXML
    private Button profileButtonToolBar;
    @FXML
    private Button menuButtonToolbar;
    @FXML
    private Button selectCategoryButtonPreView;
    @FXML
    private Button addChargeButtonPreview;

    //private AnchorPane pane1;
    //private AnchorPane pane2;
    //private Button menu;
    //private Button exit;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO 
      
        
        
        
        
        
        
        
        
        
        
        
        
        
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

   
        
        
    
    
}
