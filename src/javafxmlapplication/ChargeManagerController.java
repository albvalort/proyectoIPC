/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.User;

/**
 * FXML Controller class
 *
 * @author migue
 */
public class ChargeManagerController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button backButton;
    @FXML
    private Button categoryButton;
    @FXML
    private MenuButton categoryList;
    @FXML
    private Label nameEdit;
    @FXML
    private TextField name;
    @FXML
    private TextField unitsEdit;
    @FXML
    private TextField descriptionEdit;
    @FXML
    private Button imageSelector;
    @FXML
    private Button addCharge;

    private Acount account;
    private User currentUser;
    private Category selectedCategory;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField priceEdit1;
    private Image scannedImage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectedCategory = null;
        DoubleProperty price = new SimpleDoubleProperty();
        DoubleProperty quantity = new SimpleDoubleProperty();
        StringConverter<? extends Number> converter = new DoubleStringConverter();
        
        Bindings.bindBidirectional(priceEdit1.textProperty(), price, (StringConverter<Number>)converter);
        Bindings.bindBidirectional(unitsEdit.textProperty(), quantity, (StringConverter<Number>)converter);
        
        priceEdit1.setTextFormatter(new DecimalTextFormatter(0,2));
        unitsEdit.setTextFormatter(new DecimalTextFormatter(0,2));
        
        try {
            account = Acount.getInstance();
            for (Category cat : account.getUserCategories()) {
                var cathegoryName = cat.getName();
                MenuItem item = new MenuItem(cathegoryName);
                item.setOnAction(event -> {
                    categoryList.setText(cathegoryName);
                    selectedCategory = cat;
                });
                categoryList.getItems().add(item);
            }
        } catch (AcountDAOException | IOException ex) {
            Logger.getLogger(ChargeManagerController.class.getName()).log(Level.SEVERE, null, ex);
        }

        backButton.setOnMouseClicked(
                event -> {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.close();
                    FXRouter.reload();
                }
        );

        datePicker.setValue(LocalDate.now());

        addCharge.setOnAction((var event) -> {
            if (selectedCategory == null) {
                //dialog ERROR select category
                return;
            }

            if (nameEdit.getText().equals("")
                    || unitsEdit.getText().equals("")
                    || priceEdit1.getText().equals("")) {
                //dialog ERROR enter a valid name
                return;
            }
            
            try {
                var priceUnit = price.getValue();
                var quantityUnits = quantity.getValue();
                
                if (!account.registerCharge(nameEdit.getText(),
                        descriptionEdit.getText(),
                        0, 0, scannedImage, datePicker.getValue(),
                        selectedCategory)) {
                    //dialog error
                    return;
                }
                
                Stage stageForClosing = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stageForClosing.close();
                FXRouter.reload();
                
            } catch (Exception ex) {
                Logger.getLogger(ChargeManagerController.class.getName()).log(Level.SEVERE, null, ex);
            }

            

        }
        );

    }

    @FXML
    private void openCategoryManager(ActionEvent event) {
        Parent proot = null;
        try {
            proot = FXMLLoader.load(getClass().getResource("CategoryAdder.fxml"), FXRouter.getResourceBundle());
            Stage pstage = new Stage();
            proot.setOnKeyPressed(key -> {
                if (key.getCode() == KeyCode.ESCAPE) {
                    pstage.close();
                }
            });

            Scene pscene = new Scene(proot);

            pstage.setScene(pscene);
            pstage.initStyle(StageStyle.UNDECORATED);
            pstage.show();
        } catch (IOException ex) {
            Logger.getLogger(ProfileSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void switchToHome(MouseEvent event) {
        try {
            FXRouter.goTo("home");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
