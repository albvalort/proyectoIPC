package javafxmlapplication;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javafx.scene.layout.VBox;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

public class PrintController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button backButton;
    @FXML
    private VBox pdfViewerBox;
    private float width;
    @FXML
    private ScrollPane scrollPane;
    private Collection<ImageView> pdfCollector;
    private PDFRenderer renderer;
    private PDDocument doc;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stage = ShifuApp.stage;
        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            System.out.print(oldVal);
            if (oldVal != newVal) {
                pdfCollectionResizer(((float)newVal/100.0f));
            }
        });
        scrollPane.prefWidthProperty().bindBidirectional(pdfViewerBox.prefWidthProperty());
        pdfCollector = new ArrayList<ImageView>();
        try {
        File f = new File("Entraga IPC.pdf");
        doc = Loader.loadPDF(f);
        renderer = new PDFRenderer(doc);
        
        for (int i = 0; i<doc.getNumberOfPages(); i++) {
            ImageView image = new ImageView(SwingFXUtils.toFXImage(renderer.renderImage(i, 0.5f), null));
            pdfCollector.add(image);
        }
        
        } catch (IOException ex) { 
            Logger.getLogger(PrintController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        pdfViewerBox.getChildren().addAll(pdfCollector);
        
    }

    @FXML
    private void switchToHome(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    
    
    
    private void pdfCollectionResizer(float scale) {
        
        pdfViewerBox.getChildren().removeAll();
        pdfCollector.clear();
        for (int i = 0; i<doc.getNumberOfPages(); i++) {
            ImageView image;
            try {
                image = new ImageView(SwingFXUtils.toFXImage(renderer.renderImage(i, scale), null));
                pdfCollector.add(image);
            } catch (IOException ex) {
                Logger.getLogger(PrintController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        pdfViewerBox.getChildren().addAll(pdfCollector);
    }
    
    
}