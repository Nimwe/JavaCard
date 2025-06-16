package cda.controller;

import cda.serializer.ContactBinarySerializer;
import cda.tools.InputValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import cda.Export;
import cda.classe.Contact;
import cda.model.AppContactModel;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import static cda.model.Contact.Gender.*;

public class AppContactController {

    ObservableList<Contact.Gender> genderList = FXCollections.observableArrayList(MALE, FEMALE, NON_BINAIRE);

    // Controller formulaires
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private ImageView profilePic; 
    @FXML
    private TextField pseudo;
    @FXML
    private TextField mobileNo;
    @FXML
    private TextField homeNo;
    @FXML
    private TextField mail;
    @FXML
    private TextField gitLink;
    @FXML
    private TextField companyName;
    @FXML
    private TextField workPhone;
    @FXML
    private TextField companyPhone;
    @FXML
    private TextField companyMail;
    @FXML
    private TextField website;
    @FXML
    private DatePicker birthDate;
    @FXML
    private ChoiceBox gender;
    @FXML
    private TextField address;
    @FXML
    private TextField zipCode;
    @FXML
    private TextField city;
    @FXML
    private TextArea description;

    @FXML
    private Button saveChangeButton;
    @FXML
    private Button cancelChangeButton;

    // Controler Tableview
    @FXML
    private TableView<Contact> tableView;
    @FXML
    private TableColumn<Contact, String> firstNameColumn;
    @FXML
    private TableColumn<Contact, String> lastNameColumn;
    @FXML
    private TableColumn<Contact, String> mobilePhoneColumn;
    @FXML
    private TableColumn<Contact, String> mailColumn;

    // Controller boutons
    private AppContactModel crud = new AppContactModel();
    private ObservableList<Contact> contactList;

    @FXML
    private RadioButton csvRadio;
    @FXML
    private RadioButton jsonRadio;
    @FXML
    private RadioButton vcardRadio;

    // Controller export
    @FXML
    private ImageView qrCodeImage;
    @FXML
    private ToggleGroup formatToggleGroup;

    // Méthodes
    // Initialisation
    @FXML
    public void initialize() throws IOException, ClassNotFoundException {

        // Initialisation des colonnes pour récuperer les proprietés des objects
        // "Contact"
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        mobilePhoneColumn.setCellValueFactory(new PropertyValueFactory<>("mobilePhone"));
        mailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Initialistion de la liste des observables à partir du CRUD
        contactList = FXCollections.observableArrayList(cda.model.AppContactModel.getAllContacts());
        tableView.setItems(contactList);

        setFieldsDisabled(true);
        gender.setItems(genderList);

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                seeContact(newSelection);
            }});
        searchContact();
    }

    @FXML
    public void searchContact() {
        FilteredList<Contact> filteredData = new FilteredList<>(contactList, p -> true);

        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(contact -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseSearch = newValue.toLowerCase();

                return contact.getFirstName().toLowerCase().contains(lowerCaseSearch)
                        || contact.getLastName().toLowerCase().contains(lowerCaseSearch)
                        || contact.getMobilePhone().toLowerCase().contains(lowerCaseSearch)
                        || contact.getEmail().toLowerCase().contains(lowerCaseSearch);
            });
        });

        tableView.setItems(filteredData);
    }

    // Create
    @FXML
    private void create() {
        // Création d'un contact - En dur pour les tests
        Contact contact = new Contact("John", "Do", "Unknown", Contact.Gender.MALE, LocalDate.of(1980, 01, 01), null,
                "0606060606",
                "0909090909", "johnDo@inconnu.com",
                null, "0 rue de nullePart", 00000, "Ailleurs",
                "Avengers", "0707070707", "0202020202", "johnDoWorkMail@avengers.com", "avengers.com",
                "C'est pas le plus malin des Avengers mais quand on est désespérés ça fait de la chair à canon");

        contactList.add(contact);
        crud.addContact(contact);
    }

    // Update
    @FXML
    private void update() {
        Contact selectedContact = tableView.getSelectionModel().getSelectedItem();

        if (selectedContact != null) {
           setFieldsDisabled(false);

        } else {
            showAlert("Aucun contact sélectionné","Veuillez selectionner un contact à modifier");
        }
    }

    // Delete
    @FXML
    private void delete() {
        Contact selectedContact = tableView.getSelectionModel().getSelectedItem();

        // Alertes et confirmation de suppression
        if (selectedContact != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Suppression");
            alert.setHeaderText("Suppresion d'un contact");
            alert.setContentText("Voulez-vous vraiment supprimer ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                int index = contactList.indexOf(selectedContact);
                crud.deleteContact(index);
                contactList.remove(selectedContact);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun contact sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez selectionner un contact à supprimer");
            alert.showAndWait();
        }
    }

    // Export

    private Export exportWindow = new Export();

    @FXML
    private void handleExport() {
        exportWindow.showExportWindow(contactList);
    }

    // Cancel
    @FXML
    private void cancel() {
        clearFields();
    }

    @FXML
    private void littleCancel() {
        Contact selectedContact = tableView.getSelectionModel().getSelectedItem();
        if (selectedContact == null) {
            clearFields();
        } else {
            seeContact(selectedContact);
            setFieldsDisabled(true);
        }
    }

    @FXML
    private void seeContact(Contact selectedContact) {

        if (selectedContact != null) {
            setFieldsDisabled(true);
            firstName.setText(selectedContact.getFirstName());

            lastName.setText(selectedContact.getLastName());
//            profilePic
            pseudo.setText(selectedContact.getNickname());
            mobileNo.setText(selectedContact.getMobilePhone());
            homeNo.setText(selectedContact.getHomePhone());
            mail.setText(selectedContact.getEmail());
            gitLink.setText(selectedContact.getGitLink());
            companyName.setText(selectedContact.getCompanyName());
            workPhone.setText(selectedContact.getWorkPhone());
            companyPhone.setText(selectedContact.getCompanyPhone());
            companyMail.setText(selectedContact.getCompanyEmail());
            website.setText(selectedContact.getWebsite());
            birthDate.setValue(selectedContact.getBirthDate());
            gender.setValue(selectedContact.getGender());
            address.setText(selectedContact.getAddress());
            zipCode.setText(String.valueOf(selectedContact.getZipCode()));
            city.setText(selectedContact.getCity());
            description.setText(selectedContact.getDescription());
        }
    }

    @FXML
    private void chooseDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Sélectionnez un dossier");

        Stage stage = (Stage) export.getScene().getWindow();
        File dir = directoryChooser.showDialog(stage);

        if (dir != null) {
            System.out.println("Dossier sélectionné : " + dir.getAbsolutePath());
        } else {
            System.out.println("Aucun dossier sélectionné.");
        }

    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void setFieldsDisabled(boolean disable) {
        saveChangeButton.setDisable(disable);
        cancelChangeButton.setDisable(disable);
        firstName.setDisable(disable);
        lastName.setDisable(disable);
        pseudo.setDisable(disable);
        mobileNo.setDisable(disable);
        homeNo.setDisable(disable);
        mail.setDisable(disable);
        gitLink.setDisable(disable);
        companyName.setDisable(disable);
        workPhone.setDisable(disable);
        companyPhone.setDisable(disable);
        companyMail.setDisable(disable);
        website.setDisable(disable);
        birthDate.setDisable(disable);
        gender.setDisable(disable);
        address.setDisable(disable);
        zipCode.setDisable(disable);
        city.setDisable(disable);
        description.setDisable(disable);
    }
    public void clearFields() {
        firstName.clear();
        lastName.clear();
        pseudo.clear();
        mobileNo.clear();
        homeNo.clear();
        mail.clear();
        gitLink.clear();
        companyName.clear();
        workPhone.clear();
        companyPhone.clear();
        companyMail.clear();
        website.clear();
        birthDate.setValue(null);
        gender.setValue(null);
        address.clear();
        zipCode.clear();
        city.clear();
        description.clear();
    }



}
