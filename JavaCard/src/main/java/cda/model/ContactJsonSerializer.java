package cda.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class ContactJsonSerializer {
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

        //Creation d'une instance pour serialiser
        ObjectMapper om = new ObjectMapper();

        //permet de gerer le type LocalDate
        om.registerModule(new JavaTimeModule());

        //demande a Jackson d'ecrire la date au format ISO-8601 ex: 1985-05-15
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            File file = new File("contacts.json");
            om.writeValue(file, contact);
            System.out.println("fichier enregistrer ici: " + file.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
