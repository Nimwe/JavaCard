package cda.serializer;

import java.io.IOException;
import java.util.ArrayList;

public interface Deserializer {
    ArrayList<?> loadList(String filePath) throws IOException, ClassNotFoundException;
    Object load(String filePath) throws IOException, ClassNotFoundException;
}
