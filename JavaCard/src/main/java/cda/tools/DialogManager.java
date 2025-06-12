package cda.tools;

import cda.AppContactController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DialogManager {

    public static void showExportDialog(AppContactController mainController) {
        try {
            FXMLLoader loader = new FXMLLoader(DialogManager.class.getResource("/cda/export.fxml"));
            Scene scene = new Scene(loader.load());

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Exporter les contacts");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(null);
            dialogStage.setScene(scene);

            // Récupération du controller de la modale
            AppContactController exportController = loader.getController();
            exportController.setDialogStage(dialogStage);

            // Chargement d'un QR code image si tu en veux un (optionnel)
            Image qrImage = new Image(DialogManager.class.getResourceAsStream("/path/to/qr-code.png"));
            exportController.setQRCodeImage(qrImage);

            dialogStage.showAndWait();

            // Si l'utilisateur a cliqué sur OK
            if (exportController.isOkClicked()) {
                String format = exportController.getSelectedFormat();
                mainController.handleExport(format);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
