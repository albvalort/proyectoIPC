package javafxmlapplication;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.rendering.PDFRenderer;

public class PrintController implements Initializable {

    @FXML
    private Button backButton;
    @FXML
    private VBox pdfViewerBox;
    @FXML
    private ScrollPane scrollPane;
    private Collection<ImageView> pdfCollector;
    private PDFRenderer renderer;
    private PDDocument doc;
    private File file;

    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker finalDate;
    @FXML
    private MenuButton menu;
    private List<Charge> lista;
    private Acount account;
    private List<Category> listaCat;
    private Category selectedCat;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectedCat = null;
        try {
            account = Acount.getInstance();
            lista = account.getUserCharges();
            listaCat = account.getUserCategories();
        } catch (IOException | AcountDAOException ex) {
            Logger.getLogger(PrintController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        scrollPane.prefWidthProperty().bindBidirectional(pdfViewerBox.prefWidthProperty());
        startDate.setValue(LocalDate.now().minus(30, ChronoUnit.DAYS));
        finalDate.setValue(LocalDate.now());
        
        for (Category cat : listaCat) {
            MenuItem mi = new MenuItem(cat.getName());
            mi.setOnAction(event -> {
                selectedCat = cat;
                menu.setText(cat.getName());
                reloadPDF();
            });
            menu.getItems().add(mi);
        }

        reloadPDF();
    }

    private void write(PDPageContentStream cs) throws IOException {
        cs.beginText();
        cs.setFont(PDType1Font.HELVETICA, 14);
        for (Charge charge : lista) {
            if (selectedCat != null) {
                if (charge.getCategory().equals(selectedCat) 
                        && charge.getDate().compareTo(finalDate.getValue()) <= 0
                        && charge.getDate().compareTo(startDate.getValue()) >= 0) {
                    cs.showText(charge.toString() + "\n");
                }
            } else {
                if (charge.getDate().compareTo(finalDate.getValue()) <= 0
                        && charge.getDate().compareTo(startDate.getValue()) >= 0) {
                    cs.showText(charge.toString() + "\n");
                }
            }
        }
    }
    
    @FXML
    private void switchToHome(MouseEvent event) {
        try {
            FXRouter.goTo("home");
        } catch (IOException ex) {
            Logger.getLogger(PrintController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void savePDF(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf"));
        File chosenFile = fileChooser.showSaveDialog(null); // Mostrar diálogo para guardar archivo

        if (chosenFile != null) {
            try {
                doc.save(chosenFile);
                doc.close();
            } catch (IOException ex) {
                Logger.getLogger(PrintController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void reloadPDF() {
        pdfCollector = new ArrayList<>();
        try {
            pdfViewerBox.getChildren().clear();
            
            doc = new PDDocument();
            
            PDPage page = new PDPage();
            
            doc.addPage(page);

            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
                write(cs);
                cs.endText();
            } catch (IOException e) {
                Logger.getLogger(PrintController.class.getName()).log(Level.SEVERE, null, e);
            }
            renderer = new PDFRenderer(doc);

            for (int i = 0; i < doc.getNumberOfPages(); i++) {
                ImageView image = new ImageView(SwingFXUtils.toFXImage(renderer.renderImage(i, 0.5f), null));
                pdfCollector.add(image);
            }

        } catch (IOException ex) {
            Logger.getLogger(PrintController.class.getName()).log(Level.SEVERE, null, ex);
        }

        pdfViewerBox.getChildren().addAll(pdfCollector);
    }
}
