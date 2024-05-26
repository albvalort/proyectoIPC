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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.User;

/**
 * FXML Controller class
 *
 * @author albvalort
 */
public class CategoryAdderController implements Initializable {
    
    private User currentUser;
    private Acount account;

    @FXML
    private TextField nameField;
    @FXML
    private Button backButton;
    @FXML
    private TextField descriptionField;
    @FXML
    private Button addCathegory;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            account = Acount.getInstance();
            currentUser = account.getLoggedUser();
            
        } catch (AcountDAOException | IOException ex) {
            Logger.getLogger(CategoryAdderController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        addCathegory.setOnAction(
                event -> {
                    if (nameField.getText().equals("")) {
                        //dialog
                        return;
                    }
                    try {
                    account.registerCategory(nameField.getText(), descriptionField.getText());
                    } catch (AcountDAOException ex) {
                        ex.printStackTrace();
                    }
                    
                    Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                    stage.close();
                }
        );
    }    

    @FXML
    private void closeCategoryManager(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    

    
}
