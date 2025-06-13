package cda.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import cda.classe.Contact;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ContactJsonSerializer implements Serializer {

    private final ObjectMapper objectMapper;

    public ContactJsonSerializer() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }


    @Override
    public void saveList(String filePath, ArrayList<?> objectsToSave) {
        try {
            File file = new File(filePath);
            objectMapper.writeValue(file, objectsToSave);
            System.out.println("List Sauvegardé bien comme il faut ici: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Erreurr lors de la sauvegarde de la list ici: " + e.getMessage());
            throw new RuntimeException("Erreur lors de la sauvegarde de la list", e);
        }
    }

    @Override
    public void save(String filePath, Object object) {
        try {
            File file = new File(filePath);
            objectMapper.writeValue(file, object);
            System.out.println("Objet Sauvegardé bien comme il faut ici: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde de l'objet ici: " + e.getMessage());
            throw new RuntimeException("Erreur lors de la sauvegarde de l'objet", e);
        }
    }





    public static void main(String[] args) {
        Contact contact = new Contact("Jean",
                "Dupont",
                "JD",
                Contact.Gender.FEMALE,
                LocalDate.of(1985, 5, 15),
                "profile.jpg",
                "+33612345678",
                "+33123456789",
                "jean.dupont@example.com",
                "https://github.com/jeandupont",
                "123 Rue de Paris",
                75001,
                "Paris",
                "AFPA",
                "+33198765432",
                "+33145678901",
                "contact@techcorp.com",
                "https://techcorp.com",
                "Développeur senior spécialisé en Java");

        ContactJsonSerializer serializer = new ContactJsonSerializer();
        serializer.save("contact.json", contact);
    }
}
