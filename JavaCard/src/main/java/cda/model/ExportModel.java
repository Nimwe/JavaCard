package cda.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import cda.classe.Contact;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ExportModel {

    /**
     * Export de la liste de contacts en JSON.
     * 
     * @param contacts Liste de contacts à exporter.
     * @param filePath Chemin du fichier de destination.
     */
    public void exportToJson(List<Contact> contacts, String filePath) {
        ObjectMapper mapper = new ObjectMapper();

        // Pour avoir un JSON lisible
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // Gestion de la sérialisation correctement pour les dates en JavaTime
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try (FileWriter writer = new FileWriter(filePath)) {
            // Écriture directe dans le writer
            mapper.writeValue(writer, contacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Export en vCard
     * 
     * @param contacts Liste de contacts à exporter.
     * @param filePath Chemin du fichier de destination.
     */
    public void exportToVCard(List<Contact> contacts, String filePath) {
        StringBuilder sb = new StringBuilder();

        for (Contact contact : contacts) {
            sb.append("BEGIN:VCARD\n");
            sb.append("VERSION:3.0\n");
            sb.append("FN:").append(contact.getLastName()).append(" ").append(contact.getFirstName()).append("\n");
            sb.append("TEL:").append(contact.getMobilePhone()).append("\n");
            sb.append("EMAIL:").append(contact.getEmail()).append("\n");
            sb.append("END:VCARD\n\n");
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Export en CSV - Prévoir la gestion du séparateur
     * 
     * @param contacts Liste de contacts à exporter.
     * @param filePath Chemin du fichier de destination.
     */
    public void exportToCSV(List<Contact> contacts, String filePath) {
        StringBuilder sb = new StringBuilder();
        sb.append("Nom,Prénom,Téléphone,Email\n");

        for (Contact contact : contacts) {
            sb.append(contact.getLastName()).append(",");
            sb.append(contact.getFirstName()).append(",");
            sb.append(contact.getMobilePhone()).append(",");
            sb.append(contact.getEmail()).append("\n");
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
