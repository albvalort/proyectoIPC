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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author migue
 */
public class VisualizerController implements Initializable {

    @FXML
    private BarChart<?, ?> barChart1;
    @FXML
    private NumberAxis numberAxis1;
    @FXML
    private CategoryAxis categoryAxis1;
    @FXML
    private TableView<?> tableViewHome1;
    @FXML
    private Button addChargeButton;
    @FXML
    private Button addCategoryButton;
    @FXML
    private ImageView chargeSelectedImage;
    @FXML
    private Button backButton;
    @FXML
    private TableColumn<?, ?> categoryColumn;
    @FXML
    private TableColumn<?, ?> nameColumn;
    @FXML
    private TableColumn<?, ?> dateColumn;
    @FXML
    private TableColumn<?, ?> unitsColumn;
    @FXML
    private TableColumn<?, ?> amountColumn;
    @FXML
    private TableColumn<?, ?> descriptionColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

   
    @FXML
    private void addCharge(ActionEvent event) {
        Parent proot = null;
        try {
            proot = FXMLLoader.load(getClass().getResource("ChargeManager.fxml"), FXRouter.getResourceBundle());
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

            
        } catch (IOException ex) {
            Logger.getLogger(ProfileSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void toggleMenu(MouseEvent event) {
    }

    @FXML
    private void switchToHome(ActionEvent event) throws IOException {
        FXRouter.goTo("home");
    }
    
}
