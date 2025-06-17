package cda.model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

import cda.classe.Contact;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ExportModel {

    /**
     * Export de la liste de contacts en JSON.
     */
    public void exportToJson(List<Contact> contacts, String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8)) {
            writer.write('\uFEFF'); // BOM
            mapper.writeValue(writer, contacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Export de la liste de contacts en vCard 3.0
     */
    public void exportToVCard(List<Contact> contacts, String filePath) {
        StringBuilder sb = new StringBuilder();

        for (Contact contact : contacts) {
            sb.append("BEGIN:VCARD\n");
            sb.append("VERSION:3.0\n");
            sb.append("FN:").append(contact.getFirstName()).append(" ").append(contact.getLastName()).append("\n");
            sb.append("N:").append(contact.getLastName()).append(";").append(contact.getFirstName()).append(";;;\n");
            sb.append("NICKNAME:").append(contact.getNickName()).append("\n");
            sb.append("GENDER:").append(contact.getGender()).append("\n");
            if (contact.getBirthDate() != null) {
                sb.append("BDAY:").append(contact.getBirthDate()).append("\n");
            }
            sb.append("PHOTO:").append(contact.getProfilePic()).append("\n");
            sb.append("TEL;TYPE=CELL:").append(contact.getMobilePhone()).append("\n");
            sb.append("TEL;TYPE=HOME:").append(contact.getHomePhone()).append("\n");
            sb.append("EMAIL:").append(contact.getEmail()).append("\n");
            sb.append("URL:").append(contact.getGitLink()).append("\n");
            sb.append("ADR:;;").append(contact.getAddress()).append(";").append(contact.getCity()).append(";;")
                    .append(contact.getZipCode()).append(";France\n");
            sb.append("ORG:").append(contact.getCompanyName()).append("\n");
            sb.append("TEL;TYPE=WORK:").append(contact.getWorkPhone()).append("\n");
            sb.append("TEL;TYPE=VOICE:").append(contact.getCompanyPhone()).append("\n");
            sb.append("EMAIL;TYPE=WORK:").append(contact.getCompanyEmail()).append("\n");
            sb.append("URL;TYPE=WORK:").append(contact.getWebsite()).append("\n");
            sb.append("NOTE:").append(contact.getDescription()).append("\n");
            sb.append("END:VCARD\n\n");
        }

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8)) {
            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Export de la liste de contacts en CSV (séparateur configurable).
     */
    public void exportToCSV(List<Contact> contacts, String filePath, char separator) {
        StringBuilder sb = new StringBuilder();

        // En-têtes
        sb.append(String.join(String.valueOf(separator), List.of(
                "Nom", "Prénom", "Surnom", "Genre", "Date de naissance", "Téléphone Mobile", "Téléphone Fixe",
                "Email", "Lien Git", "Adresse", "Code Postal", "Ville", "Nom de la société", "Mobile Société",
                "Fixe Société", "Email Société", "Site Internet", "Notes"))).append("\n");

        for (Contact contact : contacts) {
            sb.append(csvField(contact.getLastName())).append(separator);
            sb.append(csvField(contact.getFirstName())).append(separator);
            sb.append(csvField(contact.getNickName())).append(separator);
            sb.append(csvField(String.valueOf(contact.getGender()))).append(separator);
            sb.append(csvField(contact.getBirthDate() != null ? contact.getBirthDate().toString() : ""))
                    .append(separator);
            sb.append(csvField(contact.getMobilePhone())).append(separator);
            sb.append(csvField(contact.getHomePhone())).append(separator);
            sb.append(csvField(contact.getEmail())).append(separator);
            sb.append(csvField(contact.getGitLink())).append(separator);
            sb.append(csvField(contact.getAddress())).append(separator);
            sb.append(csvField(String.valueOf(contact.getZipCode()))).append(separator);
            sb.append(csvField(contact.getCity())).append(separator);
            sb.append(csvField(contact.getCompanyName())).append(separator);
            sb.append(csvField(contact.getWorkPhone())).append(separator);
            sb.append(csvField(contact.getCompanyPhone())).append(separator);
            sb.append(csvField(contact.getCompanyEmail())).append(separator);
            sb.append(csvField(contact.getWebsite())).append(separator);
            sb.append(csvField(contact.getDescription())).append("\n");
        }

        // Écriture en UTF-8 avec BOM
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8)) {
            writer.write('\uFEFF'); // BOM pour Excel
            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Protège un champ CSV contenant des caractères spéciaux.
     */
    private String csvField(String value) {
        if (value == null)
            return "";
        if (value.contains(";") || value.contains(",") || value.contains("\"") || value.contains("\n")) {
            value = value.replace("\"", "\"\"");
            return "\"" + value + "\"";
        }
        return value;
    }
}
