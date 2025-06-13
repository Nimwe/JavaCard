package cda;

import cda.controller.ExportController;
import cda.classe.Contact;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Export {

    public void showExportWindow(List<Contact> maListeDeContacts) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cda/export.fxml"));
            Parent root = loader.load();

            ExportController controller = loader.getController();
            controller.setContacts(maListeDeContacts);

            Stage stage = new Stage();
            stage.setTitle("Exporter les contacts");
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
