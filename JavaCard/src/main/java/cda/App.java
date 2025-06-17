package cda;


import cda.classe.Contact;
import cda.model.AppContactModel;
import cda.serializer.ContactBinarySerializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage primaryStage) throws IOException {
        scene = new Scene(loadFXML("appContact"));
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Gestion de Contacts - VCard");


        primaryStage.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Voulez-vous sauvegarder vos contacts avant de quitter ?");

            ButtonType saveAndQuit = new ButtonType("Sauvegarder et quitter");
            ButtonType quit = new ButtonType("Quitter sans sauvegarder");
            ButtonType cancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(saveAndQuit, quit, cancel);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isEmpty() || result.get() == cancel) {
                // Annule la fermeture
                event.consume();
            } else if (result.get() == saveAndQuit) {
                ContactBinarySerializer saver = new ContactBinarySerializer();
                ArrayList<Contact> arrayList = new ArrayList<>(AppContactModel.getContacts());

                try {
                    saver.saveList("src/main/resources/contact.bin", arrayList);
                } catch (IOException e) {
                    e.printStackTrace();
                    // Ne ferme pas si la sauvegarde échoue
                    event.consume();
                }

            } else if (result.get() == quit) {

            }
        });




        primaryStage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
