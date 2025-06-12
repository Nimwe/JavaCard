package cda.tools;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

import cda.App;

public class FxmlLoaderUtil {
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
}
