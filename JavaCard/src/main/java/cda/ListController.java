package cda;

import java.io.IOException;
import javafx.fxml.FXML;

public class ListController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
