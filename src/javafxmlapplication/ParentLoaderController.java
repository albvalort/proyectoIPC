package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ParentLoaderController implements Initializable {

    @FXML
    private VBox VBox;
    @FXML
    private ImageView ImageView;
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            
        try {
            FXMLLoader authenticationLoader = new FXMLLoader(getClass().getResource("Login.fxml"), JavaFXMLApplication.getResourceBundle());
            Parent root = authenticationLoader.load();
            
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(e -> stage.show());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Autenticacion | SHIFU");
            pause.play();
            
        } catch (IOException ex) {
            System.out.print(ex);
        }
        
        
        
        
    }    

    
}
