/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

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
    private TableColumn<?, ?> categoryColumn1;
    @FXML
    private TableColumn<?, ?> nameColumn1;
    @FXML
    private TableColumn<?, ?> dateColumn1;
    @FXML
    private TableColumn<?, ?> unitsColumn1;
    @FXML
    private TableColumn<?, ?> amountColumn1;
    @FXML
    private TableColumn<?, ?> amountColumn11;
    @FXML
    private Button addChargeButton;
    @FXML
    private Button removeChargeButton;
    @FXML
    private Button editChargeButton;
    @FXML
    private Button addCategoryButton;
    @FXML
    private Button removeCategoryButton;
    @FXML
    private Button editCategoryButton;
    @FXML
    private Button printButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void toggleMenu(MouseEvent event) {
    }
    
}
