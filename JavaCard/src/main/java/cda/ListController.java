package cda;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import cda.model.Contact;
import cda.model.Crud;

import java.time.LocalDate;
import java.util.Optional;

public class ListController {

    // Colonnes de la liste
    @FXML
    private TableView<Contact> tableView;
    @FXML
    private TableColumn<Contact, String> firstName;
    @FXML
    private TableColumn<Contact, String> lastName;
    @FXML
    private TableColumn<Contact, String> phone;

    @FXML
    private TableColumn<Contact, String> mail;

    private Crud crud = new Crud();

    private ObservableList<Contact> contactList;

    @FXML
    public void initialize() {

        // Initialisation des colonnes pour récuperer les proprietés des objects
        // "Contact"
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        mail.setCellValueFactory(new PropertyValueFactory<>("mail"));

        // Initialistion de la liste des observables à partir du CRUD
        contactList = FXCollections.observableArrayList(cda.model.Crud.getAllContacts());
        tableView.setItems(contactList);
    }

    // Create
    @FXML
    private void create() {
        // Création d'un contact de test
        Contact contact = new Contact("John", "Do", "Unknown", Contact.Gender.MALE, LocalDate.of(1980, 01, 01), null,
                "0606060606",
                "0909090909", "johnDo@inconnu.com",
                null, "0 rue de nullePart", "00000", "Ailleurs",
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

}
