package cda.model;

import java.io.IOException;
import java.util.ArrayList;

public interface Serializer<T> {
    void saveList(String filePath, ArrayList<T> objectsToSave) throws IOException;

    void save(String filePath, T object) throws IOException;
}
