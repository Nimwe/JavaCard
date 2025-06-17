package cda.controller;

import cda.serializer.ContactBinarySerializer;
import cda.tools.InputValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import cda.Export;
import cda.classe.Contact;
import cda.model.AppContactModel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static cda.classe.Contact.Gender.*;

public class AppContactController {

    ObservableList<Contact.Gender> genderList = FXCollections.observableArrayList(MALE, FEMALE, NON_BINAIRE);

    // Controller formulaires
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private ImageView profilePic;
    File file = new File("../assets/img/profil.png");
    Image defaultPic = new Image(file.toURI().toString());
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
    private ChoiceBox<Contact.Gender> gender;
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

    // Controller recherche
    @FXML
    private TextField searchContact;

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
    private final AppContactModel crud = new AppContactModel();
    private ObservableList<Contact> contactList;

    private String selectedProfilePicUrl;


    // Controller export

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
        ContactBinarySerializer serializer = new ContactBinarySerializer();
        List<?> rawList = serializer.loadList("src/main/resources/contact.bin");

        // Créer une liste typée sans cast non sécurisé
        ObservableList<Contact> loadedContacts = FXCollections.observableArrayList();
        for (Object obj : rawList) {
            if (obj instanceof Contact contact) {
                loadedContacts.add(contact);
            }
        }

        //met une image par defautl
        profilePic.setImage(defaultPic);


        AppContactModel.setContacts(loadedContacts);
        contactList = FXCollections.observableArrayList(AppContactModel.getAllContacts());
        tableView.setItems(contactList);

        setFieldsDisabled(true);
        gender.setItems(genderList);


        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newSelection) -> {
            if (newSelection != null) {
                seeContact(newSelection);
            }
        });
        searchContact();
    }

    // Search
    @FXML
    public void searchContact() {
        FilteredList<Contact> filteredData = new FilteredList<>(contactList, p -> true);

        searchContact.textProperty().addListener((obs, oldVal, newValue) -> {
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
        clearFields(); // vide tous les champs
    }

    // permet de valider la creation et la modification de contact
    @FXML
    private void handleSaveChange()  {
        Contact selectedContact = tableView.getSelectionModel().getSelectedItem();
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();

        // Récupération des champs
        String firstNamecre = firstName.getText().trim();
        String lastNamecre = lastName.getText().trim();
        String profilePicre = selectedProfilePicUrl;
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

        Contact.Gender gendercre = gender.getValue();
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
                    companyNamecre, workPhonecre, companyPhonecre, companyMailcre, websitecre, descriptioncre);
            contactList.add(newContact);
            crud.addContact(newContact);
            Alert alertCreate = new Alert(Alert.AlertType.INFORMATION);
            alertCreate.setTitle("Contact creer");
            alertCreate.setHeaderText("Le contact a été crée");
            alertCreate.showAndWait();
        } else {
            // Mettre à jour le contact sélectionné
            selectedContact.setFirstName(firstNamecre);
            selectedContact.setLastName(lastNamecre);
            selectedContact.setNickname(pseudocre);
            selectedContact.setGender(gendercre);
            selectedContact.setBirthDate(birthDatecre);
            selectedContact.setProfilePic(selectedProfilePicUrl);
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

            crud.updateContact(selectedIndex, selectedContact);

            tableView.refresh();
            Alert alertUpdate = new Alert(Alert.AlertType.INFORMATION);
            alertUpdate.setTitle("Contact modifier");
            alertUpdate.setHeaderText("Le contact a été modifié");
            alertUpdate.showAndWait();
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
            showAlert("Aucun contact sélectionné", "Veuillez selectionner un contact à modifier");
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
            showAlert("Aucun contact sélectionné", "Veuillez selectionner un contact à supprimer");
        }
    }

    // Export

    private final Export exportWindow = new Export();

    //affiche la pop-up d'export de contact
    @FXML
    private void handleExport() {
        exportWindow.showExportWindow(contactList);
    }

    // Cancel
    @FXML
    private void cancel() {
        clearFields();
    }

    // Cancel de création de contact
    @FXML
    private void littleCancel()  {
        Contact selectedContact = tableView.getSelectionModel().getSelectedItem();
        if (selectedContact == null) {
            clearFields();
        } else {
            seeContact(selectedContact);
            setFieldsDisabled(true);
        }
    }

    // Affiche la fiche contact aprés selection dans la liste
    @FXML
    private void seeContact(Contact selectedContact) {


        if (selectedContact != null) {
            selectedProfilePicUrl = selectedContact.getProfilePic();
            setFieldsDisabled(true);
            firstName.setText(selectedContact.getFirstName());

            lastName.setText(selectedContact.getLastName());

            //Chargement de l'image de profil
            if (selectedContact.getProfilePic() != null && !selectedContact.getProfilePic().isEmpty()) {
                try {
                    urlInterpreter(selectedProfilePicUrl);
                } catch (Exception e) {
                    System.out.println("Erreur lors du chargement de l'image : " + e.getMessage());
                }
            } else {
                //mettre l'image par defaut si aucun choisis
                profilePic.setImage(defaultPic);
            }
            pseudo.setText(selectedContact.getNickName());
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

    // Factorisation de l'appel des alertes
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Désactiver l'écriture dans les textfields
    private void setFieldsDisabled(boolean disable) {
        saveChangeButton.setDisable(disable);
        cancelChangeButton.setDisable(disable);
        profilePic.setDisable(disable);
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

    // Rendre les textfields vierges
    public void clearFields()  {
        firstName.clear();
        lastName.clear();
        profilePic.setImage(defaultPic);
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

    //interpreter l'url comme un navigateur pour pouvoir afficher l'image
    private void urlInterpreter(String imageUrl) throws IOException {
        URI uri = URI.create(imageUrl);
        URL url = uri.toURL();
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        InputStream inputSream = conn.getInputStream();
        Image profiltoshow = new Image(inputSream);


        profilePic.setImage(profiltoshow);
    }

    //permet d'ajouter une photo de profil a un contact
    @FXML
    private void selectProfilePic() throws IOException {

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Entrer l'URL de l'image");
            dialog.setHeaderText("URL de l'image");
            dialog.setContentText("Entrez l'URL de l'image");

            //stock l'url dans une variable
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                selectedProfilePicUrl = result.get();
                urlInterpreter(selectedProfilePicUrl);
            }
            else{
                showAlert("Erreur d'url", "l'url n'a pas été enregistré");
            }

    }

    //Grossis l'image et baisse son opacité quand la souris passe dessus
    @FXML
    private void mouseOnImage()  {
        profilePic.setScaleX(1.1);
        profilePic.setScaleY(1.1);

        profilePic.opacityProperty().set(.7);
    }

    //Remet l'image à son état normal quand la souris ne la survole plus
    @FXML
    private void mouseOffImage()  {
        profilePic.setScaleX(1.0);
        profilePic.setScaleY(1.0);

        profilePic.opacityProperty().set(1.0);
    }

}

