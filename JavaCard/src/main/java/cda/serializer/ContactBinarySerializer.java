package cda.serializer;

import cda.model.Contact;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ContactBinarySerializer implements Serializer, Deserializer {


    @Override
    public void saveList(String filePath, ArrayList<?> objectsToSave) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(objectsToSave);
        }
    }

    @Override
    public void save(String filePath, Object object) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(object);
        }
    }

    @Override
    public ArrayList<?> loadList(String filePath) throws IOException, ClassNotFoundException {
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            return (ArrayList<?>) objectInputStream.readObject();
        }
    }

    @Override
    public Object load(String filePath) throws IOException, ClassNotFoundException {
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            return objectInputStream.readObject();
        }
    }

    public static void main(String[] args) throws IOException {
        ArrayList<Contact> objectsToSave = new ArrayList<>();

        Contact contact1 = new Contact(
                "Alice", "Dupont", "Ali", Contact.Gender.FEMALE, LocalDate.of(1990, 5, 12),
                "alice.jpg", "0612345678", "0147852369", "alice.dupont@example.com", "https://github.com/aliced",
                "123 Rue de Paris", 75001, "Paris", "TechCorp", "0147234567", "0147998888",
                "contact@techcorp.com", "https://techcorp.com", "Développeuse Java expérimentée."
        );

        Contact contact2 = new Contact(
                "Bob", "Martin", "Bobby", Contact.Gender.MALE, LocalDate.of(1985, 3, 22),
                "bob.png", "0622334455", "0155667788", "bob.martin@example.com", "https://github.com/bobmartin",
                "45 Boulevard Haussmann", 75009, "Paris", "DevSolutions", "0147234560", "0147223344",
                "bob@devsolutions.com", "https://devsolutions.com", "Architecte logiciel passionné."
        );

        Contact contact3 = new Contact(
                "Chloé", "Durand", "Clo", Contact.Gender.FEMALE, LocalDate.of(1995, 11, 5),
                "chloe.png", "0677889900", "0199887766", "chloe.d@example.com", "https://github.com/chloed",
                "78 Avenue des Champs", 75008, "Paris", "Innovatech", "0177889900", "0177889911",
                "c.durand@innovatech.com", "https://innovatech.com", "Ingénieure en IA et data science."
        );

        Contact contact4 = new Contact(
                "David", "Lemoine", "Dave", Contact.Gender.MALE, LocalDate.of(1978, 7, 30),
                "david.jpg", "0633221144", "0133445566", "david.l@example.com", "https://github.com/davidlemoine",
                "12 Rue Victor Hugo", 69002, "Lyon", "Lemoine Consulting", "0437223344", "0437223345",
                "info@lemoineconsulting.com", "https://lemoineconsulting.com", "Consultant senior en stratégie IT."
        );


        objectsToSave.add(contact1);
        objectsToSave.add(contact2);
        objectsToSave.add(contact3);
        objectsToSave.add(contact4);

        Serializer serializer = new ContactBinarySerializer();
        serializer.saveList("src/main/resources/contact.bin", objectsToSave);

    }
}
