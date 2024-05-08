package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
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
            FXMLLoader authenticationLoader = new FXMLLoader(getClass().getResource("Authentication.fxml"));
            Parent root = authenticationLoader.load();
            
            AuthenticationController authController = authenticationLoader.getController();
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            
            stage.setScene(scene);
            stage.setTitle("Autenticacion | SHIFU");
            stage.initModality(Modality.NONE);
            PauseTransition pause = new PauseTransition(Duration.seconds(5.0));
            pause.setOnFinished(e -> stage.show());
        } catch (IOException ex) {
            Logger.getLogger(ParentLoaderController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    }    

    
}
