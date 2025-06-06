package cda;

import java.io.IOException;
import javafx.fxml.FXML;

public class FormController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}