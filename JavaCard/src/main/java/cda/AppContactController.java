package cda;

import cda.tools.InputValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import cda.model.Contact;
import cda.model.Crud;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;

public class AppContactController {

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
    private TextField birthDate;
    @FXML
    private ChoiceBox<String> gender;
    @FXML
    private TextField address;
    @FXML
    private TextField zipCode;
    @FXML
    private TextField city;
    @FXML
    private TextArea description;

    // Controler Tableview
    // Colonnes de la liste
    @FXML
    private TextField search;
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

    @FXML
    private Button export;

    private Crud crud = new Crud();
    private ObservableList<Contact> contactList;



    // Méthodes
    @FXML
    public void initialize() {

        // Initialisation des colonnes pour récuperer les proprietés des objects
        // "Contact"
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        mobilePhoneColumn.setCellValueFactory(new PropertyValueFactory<>("mobilePhone"));
        mailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Initialistion de la liste des observables à partir du CRUD
        contactList = FXCollections.observableArrayList(cda.model.Crud.getAllContacts());
        tableView.setItems(contactList);

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
        String firstNamecre =  firstName.getText().trim();
        String lastNamecre =  lastName.getText().trim();
        String profilePicre = String.valueOf(profilePic);
        String pseudocre =  pseudo.getText().trim();
        String mobileNocre =  mobileNo.getText().trim();
        String homeNocre =  homeNo.getText().trim();
        String mailcre =  mail.getText().trim();
        String gitLinkcre =  gitLink.getText().trim();
        String companyNamecre =  companyName.getText().trim();
        String workPhonecre =  workPhone.getText().trim();
        String companyPhonecre =  companyPhone.getText().trim();
        String companyMailcre =  companyMail.getText().trim();
        String websitecre =  website.getText().trim();
        String addresscre =  address.getText().trim();
        String citycre = city.getText().trim();
        String descriptioncre = description.getText().trim();
        LocalDate birthDatecre = LocalDate.parse(birthDate.getText().trim());
        String zipcre = zipCode.getText().trim();

        // Date de naissance
        if (birthDatecre == null) {
            showAlert("Date de naissance manquante", "Veuillez sélectionner une date de naissance.");
            return;
        }

        // Genre
        String gendercre = gender.getSelectionModel().getSelectedItem();;
        if (!InputValidator.isChoiceSelected(gendercre)) {
            showAlert("Genre manquant", "Veuillez sélectionner un genre.");
            return;
        }

        // Prénom & nom
        if ((!InputValidator.isValidName(firstNamecre) || !InputValidator.isValidName(lastNamecre))) {
            showAlert("Nom ou prénom invalide", "Veuillez entrer un nom et un prénom valides (lettres uniquement).");
            return;
        }

        // Email
        if (!InputValidator.isValidEmail(mailcre)) {
            showAlert("Email invalide", "Veuillez entrer une adresse email valide.");
            return;
        }

        // Telephones
        if (!mobileNocre.isEmpty() && !InputValidator.isValidPhoneNumber(mobileNocre)) {
            showAlert("Numéro invalide", "Le numéro de mobile est invalide.");
            return;
        }if (!InputValidator.isValidPhoneNumber(homeNocre)) {
            showAlert("Numéro de téléphone fixe invalide", "Le numéro de téléphone fixe est invalide.");
            return;
        }

        //Telephone pro
        if (!InputValidator.isValidPhoneNumber(workPhonecre)) {
            showAlert("Numéro invalide", "Le numéro de mobile pro. est invalide.");
            return;
        }if (!InputValidator.isValidPhoneNumber(companyPhonecre)) {
            showAlert("Numéro de téléphone fixe invalide", "Le numéro de téléphone fixe est invalide.");
            return;
        }

        // GitHub
        if (!InputValidator.isValidGitLink(gitLinkcre)) {
            showAlert("Lien GitHub invalide", "Veuillez entrer un lien GitHub valide ou laisser vide.");
            return;
        }

        // Site web
        if (!InputValidator.isValidWebsite(websitecre)) {
            showAlert("Site web invalide", "Veuillez entrer une URL valide ou laisser vide.");
            return;
        }

        // Code postal
        String zipText = zipCode.getText().trim();
        if (!InputValidator.isValidZipCode(zipText)) {
            showAlert("Code postal invalide", "Le code postal doit comporter 5 chiffres.");
            return;
        }

        int zipCodecre = Integer.parseInt(zipText); // sûr à ce stade

        // Conversion genre vers enum
        Contact.Gender genderEnum = Contact.Gender.valueOf(gendercre.toUpperCase());

        // Création du contact
        Contact contact = new Contact(
                firstNamecre, lastNamecre, pseudocre, genderEnum, birthDatecre, profilePicre,
                mobileNocre, homeNocre, mailcre, gitLinkcre, addresscre, zipCodecre, citycre,
                companyNamecre, workPhonecre, companyPhonecre, companyMailcre, websitecre, descriptioncre
        );

        contactList.add(contact);
        crud.addContact(contact);

    }

    // Update
    @FXML
    private void update() {
        Contact selectedContact = tableView.getSelectionModel().getSelectedItem();

        if (selectedContact != null) {
            int index = contactList.indexOf(selectedContact);
            selectedContact.setMobilePhone("0909090906");
            crud.updateContact(index, selectedContact);
            tableView.refresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun contact sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez selectionner un contact à modifier");
            alert.showAndWait();
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
    @FXML
    private void export() {

    }

    // Cancel
    @FXML
    private void cancel() {

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

}
