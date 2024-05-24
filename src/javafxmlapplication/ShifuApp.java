/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import model.Acount;
import model.AcountDAOException;
import model.User;

public class ShifuApp extends Application {
    
    public static Acount account;
    public static User currentUser;
    
   
    public static Locale[] availableLanguages = {
        new Locale("es"), 
        new Locale("de"), 
        new Locale("en"), 
        new Locale("fr")
    };
    
    @Override
    public void start(Stage stage) throws Exception {
        FXRouter.setResourceBundle(new Locale("en"));
        FXRouter.bind(this, stage, "SHIFU", 800, 600);
        FXRouter.when("login", "Login.fxml", 800 ,600);
        FXRouter.when("signup", "Signup.fxml", 800, 600);
        FXRouter.when("profile", "ProfileSettings.fxml", 800, 600);
        FXRouter.when("home", "Home.fxml", 1200, 800);
        FXRouter.when("print", "Print.fxml", 1200, 800);
        FXRouter.when("chargeManager", "ChargeManager.fxml", 650, 800);

        
        
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> {
            try {
                FXRouter.goTo("login");
            } catch (IOException ex) {
                System.out.println("Cannot show login");
            }
        });

        pause.play();
        showLoadingScreen();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        try {
            account = Acount.getInstance();
        } catch (AcountDAOException | IOException ex) {
            Logger.getLogger(ShifuApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    
    private void showLoadingScreen() throws IOException {
        FXMLLoader parentLoader = new FXMLLoader(getClass().getResource("ParentLoader.fxml"));
        PauseTransition pause = new PauseTransition(Duration.seconds(2.0));
        Stage loadingStage = new Stage();
        pause.setOnFinished(e -> loadingStage.hide());
        Scene scene = new Scene((Parent)parentLoader.load());
        loadingStage.setTitle("SHIFU");
        loadingStage.initStyle(StageStyle.UNDECORATED);
        loadingStage.initModality(Modality.APPLICATION_MODAL);
        loadingStage.getIcons().add(new Image(ShifuApp.class.getResourceAsStream("../resources/images/Logo.png")));
        loadingStage.addEventHandler(WindowEvent.WINDOW_SHOWN, (WindowEvent event) -> {
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            loadingStage.setX((screenBounds.getWidth() - loadingStage.getWidth()) / 2);
            loadingStage.setY((screenBounds.getHeight() - loadingStage.getHeight()) / 2);
        });
        
        
        loadingStage.setScene(scene);
        pause.play();
        loadingStage.show();
    }
}
