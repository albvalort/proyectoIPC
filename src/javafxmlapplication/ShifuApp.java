/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;


public class ShifuApp extends Application {
    
    private static Locale language = new Locale("en");
    public static Locale[] availableLanguages = {
        new Locale("es"), 
        new Locale("de"), 
        new Locale("en"), 
        new Locale("fr")
    };
    
    public static Stage stage;
    
    @Override
    public void start(Stage stage) throws Exception {
        //======================================================================
        // 1- creación del grafo de escena a partir del fichero FXML
        FXMLLoader parentLoader = new FXMLLoader(getClass().getResource("ParentLoader.fxml"), getResourceBundle());
        Parent root = parentLoader.load();
        PauseTransition pause = new PauseTransition(Duration.seconds(2.0));
        pause.setOnFinished(e -> stage.hide());
        //======================================================================
        // 2- creación de la escena con el nodo raiz del grafo de escena
        Scene scene = new Scene(root);
        //======================================================================
        // 3- asiganación de la escena al Stage que recibe el metodo 
        //     - configuracion del stage
        //     - se muestra el stage de manera no modal mediante el metodo show()
        stage.setTitle("SHIFU");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(ShifuApp.class.getResourceAsStream("../resources/images/Logo.png")));
        
        
        //CenterOnScreen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
        
        stage.setScene(scene);
        ShifuApp.stage = stage;        
        pause.play();
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }

    public static ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle("resources.language.UIResources", language);
    }

    public static void setLocale(Locale newLocale) {
        language = newLocale;
    }
    public static String getLocaleString() {
        return language.toString();
    }
}
