package cda.model;

import java.io.IOException;
import java.util.ArrayList;

public interface Deserializer<T> {
    ArrayList<T> loadList(String filePath) throws IOException, ClassNotFoundException;
    T load(String filePath) throws IOException, ClassNotFoundException;
}
