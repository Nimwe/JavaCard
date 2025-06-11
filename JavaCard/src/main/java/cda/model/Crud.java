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

    // Export 
    //public void export()



    // Cancel 

}
