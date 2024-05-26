/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;
import model.User;

/**
 * FXML Controller class
 *
 * @author migue
 */
public class HomeController implements Initializable {
    

    private boolean visibleMenu;
    
    
    @FXML
    private Button profileButton;

    @FXML
    private Button menuButton;
    @FXML
    private Button visualizerButtonToolBar;
    @FXML
    private Button printButtonToolBar;
    @FXML
    private Button chargeManagerButtonToolBar;
    @FXML
    private Button categoryManagerButtonToolBar;
    @FXML
    private ImageView profileImage;
    
    private Acount account;
    private User currentUser;
    @FXML
    private BarChart<String, Double> barChart;
    @FXML
    private TableView<Charge> tableViewHome;
    @FXML
    private TableColumn<Charge, String> nameColumn;
    @FXML
    private TableColumn<Charge, Number> unitsColumn;
    @FXML
    private TableColumn<Charge, Number> amountColumn;
    
    private ObservableList<Charge> data;
    @FXML
    private NumberAxis numberAxis;
    @FXML
    private CategoryAxis categoryAxis;
    @FXML
    private Label username;
    @FXML
    private VBox mainHome;
    @FXML
    private VBox lateralMenu;
    @FXML
    private Button logOutButton;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Charge> dataValues = null;
        data = FXCollections.observableArrayList();
        try {
            account = Acount.getInstance();
            dataValues = FXCollections.observableArrayList(account.getUserCharges());
        } catch (AcountDAOException | IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        currentUser = account.getLoggedUser();
        
        username.setText(account.getLoggedUser().getNickName());
        
        profileImage.setImage(currentUser.getImage());
        tableViewHome.setItems(data);
        
        
        nameColumn.setCellValueFactory(
                chargeRow -> new SimpleStringProperty(chargeRow.getValue().getName())
        );
        
        unitsColumn.setCellValueFactory(
                chargeRow -> new SimpleDoubleProperty(chargeRow.getValue().getUnits())
        );
        
        amountColumn.setCellValueFactory(
                chargeRow -> new SimpleDoubleProperty(chargeRow.getValue().getCost())
        );
        
        for (Charge charge: dataValues) {
            if (charge.getDate().compareTo(LocalDate.now().minus(30, ChronoUnit.DAYS)) > 0) {
                data.add(charge);
            }
            
        }
        
        try {
            for (Category cat: account.getUserCategories()) {
                XYChart.Series serie = new XYChart.Series();
                serie.setName(cat.getName());
                var total = 0.0;
                
                for (Charge charge: data) {
                    if (charge.getCategory().getName().equals(cat.getName())) {
                        total += charge.getUnits() * charge.getCost();
                    }
                }
                if (total == 0) continue;
                serie.getData().add(new XYChart.Data(cat.getName(), total));
                barChart.getData().add(serie);
            }
        } catch (AcountDAOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
        addButtonsToTable();
        
        
    }    

    @FXML
    private void switchToProfileSettings(MouseEvent event) throws IOException {
        FXRouter.goTo("profile");
    }

    @FXML
    private void switchToChargeManager(ActionEvent event) throws IOException {
        FXRouter.goTo("chargeManager");
    }

    @FXML
    private void switchToChargeManager(MouseEvent event) {
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
    private void toggleMenu(MouseEvent event) throws IOException {
        visibleMenu = !visibleMenu;
        lateralMenu.setVisible(visibleMenu);
    }

    @FXML
    private void switchToPrint(ActionEvent event) throws IOException {
        FXRouter.goTo("print");
    }

    @FXML
    private void switchToLogin(ActionEvent event) throws IOException {
        FXRouter.goTo("login");
    }

    
    private void addButtonsToTable() {
        TableColumn<Charge, Void> colBtn = new TableColumn("Edit");
        
        Callback<TableColumn<Charge, Void>, TableCell<Charge, Void>> cellFactory = (final TableColumn<Charge, Void> param) -> {
            final TableCell<Charge, Void> cell = new TableCell<Charge, Void>() {

                private final Button btn = new Button();

                {
                    btn.setOnAction((ActionEvent event) -> {
                        Charge charge = getTableView().getItems().get(getIndex());
                        handleButtonEdit(charge);
                        FXRouter.reload();
                    });
                    
                    ImageView img = new ImageView("resources/images/editIcon.png");
                    img.setFitHeight(20);
                    img.setFitWidth(20);
                    btn.setGraphic(img);
                    
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                    }
                }
            };
            return cell;
        };
        
        // Columna para el botón de borrar
    TableColumn<Charge, Void> deleteColBtn = new TableColumn("Delete");

    Callback<TableColumn<Charge, Void>, TableCell<Charge, Void>> deleteCellFactory = (final TableColumn<Charge, Void> param) -> {
        final TableCell<Charge, Void> cell = new TableCell<Charge, Void>() {
            private final Button deleteBtn = new Button();
            
            {
                deleteBtn.setOnAction((ActionEvent event) -> {
                    Charge charge = getTableView().getItems().get(getIndex());
                    handleButtonDelete(charge);
                    FXRouter.reload();
                });
                ImageView img = new ImageView("resources/images/deleteIcon.png");
                img.setFitHeight(20);
                img.setFitWidth(20);
                deleteBtn.setGraphic(img);
                
            }
            
            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteBtn);
                }
            }
        };
        return cell;
        };
        deleteColBtn.setCellFactory(deleteCellFactory);
        colBtn.setCellFactory(cellFactory);

        tableViewHome.getColumns().add(colBtn);
        tableViewHome.getColumns().add(deleteColBtn);
    }
   
    private void handleButtonEdit(Charge charge) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChargeManager.fxml"), FXRouter.getResourceBundle());
        Parent root = loader.load();
        
        ChargeManagerController controller = loader.getController();
        controller.setCharge(charge);
        
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        
    } catch (IOException ex) {
        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
    }
}
        private void handleButtonDelete(Charge charge) {
    try {
        account.removeCharge(charge);
        data.remove(charge);
    } catch (Exception ex) {
        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error deleting charge");
        alert.setContentText(ex.getMessage());
        alert.showAndWait();
    }
}
        
    
    
}
