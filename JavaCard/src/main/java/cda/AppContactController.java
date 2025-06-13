package cda;

import cda.serializer.ContactBinarySerializer;
import cda.tools.InputValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import cda.model.Contact;
import cda.model.AppContactModel;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

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

    private AppContactModel crud = new AppContactModel();
    private ObservableList<Contact> contactList;



    // Méthodes
    @FXML
    public void initialize() throws IOException, ClassNotFoundException {

        // Initialisation des colonnes pour récuperer les proprietés des objects
        // "Contact"
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        mobilePhoneColumn.setCellValueFactory(new PropertyValueFactory<>("mobilePhone"));
        mailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Initialistion de la liste des observables à partir du CRUD

        ContactBinarySerializer serializer = new ContactBinarySerializer();


        AppContactModel.setContacts((ObservableList<Contact>) FXCollections.observableArrayList(serializer.loadList("src/main/resources/contact.bin")));
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
        tableView.getSelectionModel().clearSelection(); // pour désélectionner un éventuel contact
        setFieldsDisabled(false); // réactive les champs si besoin
        clearFields();            // vide tous les champs
    }

    @FXML
    private void handleSaveChange() {
        Contact selectedContact = tableView.getSelectionModel().getSelectedItem();
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();


        // Récupération des champs
        String firstNamecre = firstName.getText().trim();
        String lastNamecre = lastName.getText().trim();
        String profilePicre = String.valueOf(profilePic);
        String pseudocre = pseudo.getText().trim();
        String mobileNocre = mobileNo.getText().trim();
        String homeNocre = homeNo.getText().trim();
        String mailcre = mail.getText().trim();
        String gitLinkcre = gitLink.getText().trim();
        String companyNamecre = companyName.getText().trim();
        String workPhonecre = workPhone.getText().trim();
        String companyPhonecre = companyPhone.getText().trim();
        String companyMailcre = companyMail.getText().trim();
        String websitecre = website.getText().trim();
        String addresscre = address.getText().trim();
        String citycre = city.getText().trim();
        String descriptioncre = description.getText().trim();
        LocalDate birthDatecre = birthDate.getValue();
        String zipText = zipCode.getText().trim();

        // Validations (comme avant)
        if (birthDatecre == null) {
            showAlert("Date de naissance manquante", "Veuillez sélectionner une date de naissance.");
            return;
        }

        Contact.Gender gendercre = (Contact.Gender) gender.getValue();
        if (!InputValidator.isChoiceSelected(String.valueOf(gendercre))) {
            showAlert("Genre manquant", "Veuillez sélectionner un genre.");
            return;
        }

        if (firstNamecre.isEmpty() || lastNamecre.isEmpty()) {
            showAlert("Nom ou prénom manquant", "Veuillez entrer un nom et un prénom.");
            return;
        }

        if (!InputValidator.isValidName(firstNamecre) || !InputValidator.isValidName(lastNamecre)) {
            showAlert("Nom ou prénom invalide", "Veuillez entrer un nom et un prénom valides.");
            return;
        }

        if (!InputValidator.isValidEmail(mailcre)) {
            showAlert("Email invalide", "Veuillez entrer une adresse email valide.");
            return;
        }

        if (!InputValidator.isValidPhoneNumber(mobileNocre)) {
            showAlert("Numéro de mobile invalide", "Veuillez entrer un numéro de mobile valide.");
            return;
        }

        if (!homeNocre.isEmpty() && !InputValidator.isValidPhoneNumber(homeNocre)) {
            showAlert("Téléphone fixe invalide", "Le numéro de téléphone fixe est invalide.");
            return;
        }

        if (!workPhonecre.isEmpty() && !InputValidator.isValidPhoneNumber(workPhonecre)) {
            showAlert("Téléphone pro invalide", "Le numéro pro est invalide.");
            return;
        }

        if (!companyPhonecre.isEmpty() && !InputValidator.isValidPhoneNumber(companyPhonecre)) {
            showAlert("Téléphone entreprise invalide", "Le numéro de téléphone de l'entreprise est invalide.");
            return;
        }

        if (!InputValidator.isValidGitLink(gitLinkcre)) {
            showAlert("Lien GitHub invalide", "Veuillez entrer un lien GitHub valide.");
            return;
        }

        if (!InputValidator.isValidWebsite(websitecre)) {
            showAlert("Site web invalide", "Veuillez entrer une URL valide.");
            return;
        }

        if (zipText.isEmpty() || !InputValidator.isValidZipCode(zipText)) {
            showAlert("Code postal invalide", "Le code postal est invalide.");
            return;
        }

        int zipCodecre = Integer.parseInt(zipText);

        // --- Création ou mise à jour ---
        if (selectedContact == null) {
            // Créer nouveau contact
            Contact newContact = new Contact(
                    firstNamecre, lastNamecre, pseudocre, gendercre, birthDatecre, profilePicre,
                    mobileNocre, homeNocre, mailcre, gitLinkcre, addresscre, zipCodecre, citycre,
                    companyNamecre, workPhonecre, companyPhonecre, companyMailcre, websitecre, descriptioncre
            );
            contactList.add(newContact);
            crud.addContact(newContact);
            showAlert("Contact créé", "Le contact a été ajouté avec succès.");
        } else {
            // Mettre à jour le contact sélectionné
            selectedContact.setFirstName(firstNamecre);
            selectedContact.setLastName(lastNamecre);
            selectedContact.setNickname(pseudocre);
            selectedContact.setGender(gendercre);
            selectedContact.setBirthDate(birthDatecre);
            selectedContact.setProfilePic(profilePicre);
            selectedContact.setMobilePhone(mobileNocre);
            selectedContact.setHomePhone(homeNocre);
            selectedContact.setEmail(mailcre);
            selectedContact.setGitLink(gitLinkcre);
            selectedContact.setAddress(addresscre);
            selectedContact.setZipCode(zipCodecre);
            selectedContact.setCity(citycre);
            selectedContact.setCompanyName(companyNamecre);
            selectedContact.setWorkPhone(workPhonecre);
            selectedContact.setCompanyPhone(companyPhonecre);
            selectedContact.setCompanyEmail(companyMailcre);
            selectedContact.setWebsite(websitecre);
            selectedContact.setDescription(descriptioncre);

            crud.updateContact(selectedIndex,selectedContact);

            tableView.refresh(); // Important pour voir les changements
            showAlert("Contact modifié", "Les informations ont été mises à jour.");
        }

        clearFields();
        tableView.getSelectionModel().clearSelection(); // on revient à l’état "aucun contact sélectionné"
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
    @FXML
    private void export() {

    }

    // Cancel
    @FXML
    private void cancel() {

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
