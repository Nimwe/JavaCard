package cda.model;

import java.util.ArrayList;
import java.util.List;

public class Crud {

    private static List<Contact> contacts;

    public Crud() {

        Crud.contacts = new ArrayList<>();
    }

    // Create
    public void addContact(Contact contact) {
        contacts.add(contact);
        System.out.println("Contact ajouté");
    }

    // Read
    public static List<Contact> getAllContacts() {
        return new ArrayList<>(contacts);
    }

    // Update
    public void updateContact(int index, Contact updateContact) {
        if (index >= 0 && index < contacts.size()) {
            contacts.set(index, updateContact);
            System.out.println("Modification effectuée ");
        } else {
            System.out.println("Entrée invalide");
        }
    }

    // Delete
    public void deleteContact(int index) {
        if (index >= 0 && index < contacts.size()) {
            contacts.remove(index);
            System.out.println("Contact supprimé");
        } else {
            System.out.println("Entrée invalide");
        }
    }

    // Export en JSon, vCard et CSV
    public void export(String format, List<Contact> contacts) {
        switch (format.toLowerCase()) {
            case "csv":
                exportCSV();
                break;
            case "json":
                exportJSON();
                break;
            case "vcard":
                exportVCard();
                break;
            default:
                System.out.println("Format inconnu");
        }
    }

    /**
     * Méthode pour l'export au format CSV
     */
    private void exportCSV() {
        try (java.io.FileWriter writer = new java.io.FileWriter("contacts.csv")) {
            writer.write(
                    "FirstName, LastName, NickName, Gender, BirthDate, ProfilPic, MobilePhone, HomePhone, Email, GitLink, Address, ZipCode, City, CompanyName, WorkPhone, CompanyPhone, CompanyEmail, Website, Description\n");
            for (Contact nC : contacts) {
                writer.write(
                        nC.getFirstName() + " , " +
                                nC.getLastName() + " , " +
                                nC.getNickName() + " , " +
                                nC.getGender() + " , " +
                                nC.getBirthDate() + " , " +
                                nC.getProfilePic() + " , " +
                                nC.getMobilePhone() + " , " +
                                nC.getHomePhone() + " , " +
                                nC.getEmail() + " , " +
                                nC.getGitLink() + " , " +
                                nC.getAddress() + " , " +
                                nC.getZipCode() + " , " +
                                nC.getCity() + " , " +
                                nC.getCompanyName() + " , " +
                                nC.getWorkPhone() + " , " +
                                nC.getCompanyPhone() + " , " +
                                nC.getCompanyEmail() + " , " +
                                nC.getWebsite() + " , " +
                                nC.getDescription().replace(",", ";") +
                                "\n");
            }
            System.out.println("Export au format CSV terminé");
        } catch (Exception excep) {
            System.out.println("Erreur lors de l'export du fichier CSV : " + excep.getMessage());
        }
    }

    /**
     * Méthode pour l'export au format JSON
     */
    private void exportJSON() {
        try (java.io.FileWriter writer = new java.io.FileWriter("contacts.json")) {
            writer.write("[\n");
            for (int i = 0; i < contacts.size(); i++) {
                Contact nC = contacts.get(i);
                writer.write(" {\n");
                writer.write(" \"firstname\" : \"" + nC.getFirstName() + "\" , \n");
                writer.write(" \"lastname\" : \"" + nC.getLastName() + "\" , \n");
                writer.write(" \"nickname\" : \"" + nC.getNickName() + "\" , \n");
                writer.write(" \"gender\" : \"" + nC.getGender() + "\" , \n");
                writer.write(" \"birthdate\" : \"" + nC.getBirthDate() + "\" , \n");
                writer.write(" \"profilpic\" : \"" + nC.getProfilePic() + "\" , \n");
                writer.write(" \"mobilephone\" : \"" + nC.getMobilePhone() + "\" , \n");
                writer.write(" \"homephone\" : \"" + nC.getHomePhone() + "\" , \n");
                writer.write(" \"email\" : \"" + nC.getEmail() + "\" , \n");
                writer.write(" \"gitlink\" : \"" + nC.getGitLink() + "\" , \n");
                writer.write(" \"address\" : \"" + nC.getAddress() + "\" , \n");
                writer.write(" \"zipcode\" : \"" + nC.getZipCode() + "\" , \n");
                writer.write(" \"city\" : \"" + nC.getCity() + "\" , \n");
                writer.write(" \"city\": \"" + nC.getCity() + "\",\n");
                writer.write(" \"companyName\": \"" + nC.getCompanyName() + "\",\n");
                writer.write(" \"workPhone\": \"" + nC.getWorkPhone() + "\",\n");
                writer.write(" \"companyPhone\": \"" + nC.getCompanyPhone() + "\",\n");
                writer.write(" \"companyEmail\": \"" + nC.getCompanyEmail() + "\",\n");
                writer.write(" \"website\": \"" + nC.getWebsite() + "\",\n");
                writer.write(" \"description\": \"" + nC.getDescription().replace("\"", "\\\"") + "\"\n");
                writer.write(" }" + (i < contacts.size() - 1 ? "," : "") + "\n");
            }
            writer.write("]");
            System.out.println("\"Export au format CSV terminé\"");
        } catch (Exception excep) {
            System.out.println("Erreur lors de l'export du fichier JSON : " + excep.getMessage());
        }
    }

    /**
     * Méhode d'export au format VCard
     */
    private void exportVCard() {
        try (java.io.FileWriter writer = new java.io.FileWriter("contacts.vcf")) {
            for (Contact c : contacts) {
                writer.write("BEGIN:VCARD\n");
                writer.write("VERSION:3.0\n");
                writer.write("N:" + c.getLastName() + ";" + c.getFirstName() + ";\n");
                writer.write("FN:" + c.getFirstName() + " " + c.getLastName() + "\n");
                if (c.getMobilePhone() != null && !c.getMobilePhone().isEmpty())
                    writer.write("TEL;TYPE=CELL:" + c.getMobilePhone() + "\n");
                if (c.getHomePhone() != null && !c.getHomePhone().isEmpty())
                    writer.write("TEL;TYPE=HOME:" + c.getHomePhone() + "\n");
                if (c.getWorkPhone() != null && !c.getWorkPhone().isEmpty())
                    writer.write("TEL;TYPE=WORK:" + c.getWorkPhone() + "\n");
                if (c.getCompanyPhone() != null && !c.getCompanyPhone().isEmpty())
                    writer.write("TEL;TYPE=WORK:" + c.getCompanyPhone() + "\n");
                if (c.getEmail() != null && !c.getEmail().isEmpty())
                    writer.write("EMAIL:" + c.getEmail() + "\n");
                if (c.getCompanyEmail() != null && !c.getCompanyEmail().isEmpty())
                    writer.write("EMAIL;TYPE=WORK:" + c.getCompanyEmail() + "\n");
                writer.write("ADR;TYPE=HOME:;" + c.getAddress() + ";" + c.getCity() + ";;" + c.getZipCode() + ";\n");
                writer.write("ORG:" + c.getCompanyName() + "\n");
                writer.write("URL:" + c.getWebsite() + "\n");
                writer.write("NOTE:" + c.getDescription().replace("\n", " ") + "\n");
                writer.write("END:VCARD\n");
            }
            System.out.println("Export vCard terminé.");
        } catch (Exception e) {
            System.out.println("Erreur export vCard : " + e.getMessage());
        }
    }
}

// Cancel
