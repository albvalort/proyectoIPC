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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
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
    
    private GridPane gp;
    @FXML
    private VBox profileBox;
    
    private BooleanProperty validPassword;
    
    @FXML
    private Label errorLabel;
    @FXML
    private Button confirmButton;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       // confirmButton.setDisable(true);
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
        
        validPassword = new SimpleBooleanProperty();
        validPassword.setValue(Boolean.FALSE);
        
        confirmButton.setOnAction(event ->{
            //no va
            
            if (oldPasswordTextField.getText() == currentUser.getPassword()) {
                currentUser.setPassword(newPasswordTextField.getText());
                
                validPassword.setValue(Boolean.TRUE);
                hideErrorMessage(errorLabel,newPasswordTextField);
                
            }else{
                validPassword.setValue(Boolean.FALSE);
                showErrorMessage(errorLabel, newPasswordTextField);
                newPasswordTextField.requestFocus();
                errorLabel.setText("Write your correct old password well");
                errorLabel.visibleProperty().set(true);
            }
                    
            if(!checkPassword(newPasswordTextField.getText())){
                validPassword.setValue(Boolean.FALSE);
                showErrorMessage(errorLabel, newPasswordTextField);
                newPasswordTextField.requestFocus();
                errorLabel.setText("Your password is not permited");
                errorLabel.visibleProperty().set(true);
            }else{
                validPassword.setValue(Boolean.TRUE);
                hideErrorMessage(errorLabel,newPasswordTextField);
            }
        
        });
        
        //oldPasswordTextField.focusedProperty().addListener((observalble,oldValue,newValue)-> {
            //if(newValue.toString() == currentUser.getPassword()) confirmButton.setDisable(false);
           //else confirmButton.setDisable(false);
            
        //});
                
                
            
        
        
        saveButton.setOnAction(event -> {
            
            Alert saveWarning = new Alert(Alert.AlertType.INFORMATION);
            saveWarning.setTitle("Shifu");
            saveWarning.setHeaderText("Thank you!");
            saveWarning.setContentText("Your information has been updated");
            saveWarning.showAndWait();
            
            currentUser.setEmail(mailTextField.getText());
            currentUser.setName(nameTextField.getText());
            currentUser.setSurname(surnameTextField.getText());
            
            
            FXRouter.reload();   
        });
        
        
        
    }    

    @FXML
    private void switchToHome(MouseEvent event) throws IOException {
        FXRouter.goTo("home");
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
            MenuItem auxMenuItem = new MenuItem("",image);
            auxMenuItem.onActionProperty().set(e -> {
                FXRouter.setResourceBundle(auxLocale);
                FXRouter.reload();
            });
            languageMenuB.getItems().add(auxMenuItem);
        }
    }

    @FXML
    private void imageButtonClick(ActionEvent event) {
        Parent proot = null;
        try {
            proot = FXMLLoader.load(getClass().getResource("ImagePopUp.fxml"), FXRouter.getResourceBundle());
            Stage pstage = new Stage();
            proot.setOnKeyPressed( key -> {
                if (key.getCode() == KeyCode.ESCAPE) {
                    pstage.close();
                }
            });

            Scene pscene = new Scene(proot);
            
            pstage.setScene(pscene);
            pstage.initStyle(StageStyle.UNDECORATED);
            pstage.show();
            
            profileBox.setDisable(true);
            
        } catch (IOException ex) {
            Logger.getLogger(ProfileSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static Boolean checkPassword(String password){     
  
        // If the password is empty , return false 
        if (password == null) { 
            return false; 
        } 
        // Regex to check valid password. 
        String regex =  "^[A-Za-z0-9]{7,16}$"; 
  
        // Compile the ReGex 
        Pattern pattern = Pattern.compile(regex); 
        // Match ReGex with value to check
        Matcher matcher = pattern.matcher(password); 
        return matcher.matches();
    
    }

    private void showErrorMessage(Label errorLabel,TextField textField){
        errorLabel.visibleProperty().set(true);
        textField.styleProperty().setValue("-fx-background-color: #FCE5E0");    
    
    }
    
    private void hideErrorMessage(Label errorLabel,TextField textField){
        errorLabel.visibleProperty().set(false);
        textField.styleProperty().setValue("");
        
    }
    
    
    
}
