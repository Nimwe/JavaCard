package cda.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.ToggleGroup;

import java.io.File;
import java.util.List;

import cda.classe.Contact;
import cda.model.ExportModel;

// Contrôleur export 

public class ExportController {

    @FXML
    private RadioButton jsonRadio;
    @FXML
    private RadioButton vcardRadio;
    @FXML
    private RadioButton csvRadio;

    @FXML
    private Button okButton;

    private ExportModel model;
    private List<Contact> contacts;

    // Groupe de boutons radio
    private ToggleGroup formatToggleGroup;

    // Initialisation après chargement du FXML.
    @FXML
    private void initialize() {
        model = new ExportModel();

        // Création et association du ToggleGroup
        formatToggleGroup = new ToggleGroup();
        jsonRadio.setToggleGroup(formatToggleGroup);
        vcardRadio.setToggleGroup(formatToggleGroup);
        csvRadio.setToggleGroup(formatToggleGroup);

        jsonRadio.setSelected(true);
    }

    /**
     * Setter pour récupérer la liste de contacts à exporter.
     * 
     * @param contacts Liste des contacts.
     */
    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    /**
     * Gestion du bouton OK : lance l'export selon le format choisi.
     */
    @FXML
    private void handleOk() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier");

        // Extension par défaut
        if (jsonRadio.isSelected()) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier JSON (*.json)", "*.json"));
        } else if (vcardRadio.isSelected()) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("vCard (*.vcf)", "*.vcf"));
        } else if (csvRadio.isSelected()) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier CSV (*.csv)", "*.csv"));
        }

        File selectedFile = fileChooser.showSaveDialog(okButton.getScene().getWindow());
        if (selectedFile != null) {
            String path = selectedFile.getAbsolutePath();

            if (jsonRadio.isSelected()) {
                model.exportToJson(contacts, path);
            } else if (vcardRadio.isSelected()) {
                model.exportToVCard(contacts, path);
            } else if (csvRadio.isSelected()) {
                model.exportToCSV(contacts, path);
            }

            // Fermer la fenêtre après export
            Stage stage = (Stage) okButton.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Gestion du bouton Annuler : ferme la fenêtre.
     */
    @FXML
    private void handleCancel() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}
