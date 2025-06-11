package cda.serializer;

import java.io.*;
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
}
