/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Category;
import model.Charge;
/**
 * FXML Controller class
 *
 * @author migue
 */
public class CategoryEditorController implements Initializable {
    
    @FXML
    private Button backButton;
    
    private Category givenCategory; 
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField descriptionTextField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

  

    @FXML
    private void closeCategoryManager(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
       
    }

    public void setCategory(Category category) {
        this.givenCategory = category;
        updateUIWithCategoryDetails();
    }

    private void updateUIWithCategoryDetails() {
        if (givenCategory != null) {
            nameTextField.setText(givenCategory.getName());
            descriptionTextField.setText(givenCategory.getDescription());
            
            
        }
    }
    
}
