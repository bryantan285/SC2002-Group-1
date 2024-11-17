package interfaces.utility;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IStorageHandler {
    
    public <T> List<T> readFromFile(String filePath, Class<T> clazz) throws IOException;
    public <T> void writeToFile(String filePath, Map<String, T> data, Class<T> clazz) throws IOException;
}