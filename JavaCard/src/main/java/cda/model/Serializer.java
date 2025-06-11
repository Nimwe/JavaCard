package cda.model;

import java.io.IOException;
import java.util.ArrayList;

public interface Serializer {
    void saveList(String filePath, ArrayList<?> objectsToSave) throws IOException;
    void save(String filePath, Object object) throws IOException;
}
