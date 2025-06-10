package cda;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class FormController {

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


}