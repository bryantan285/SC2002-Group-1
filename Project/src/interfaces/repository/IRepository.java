package interfaces.repository;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IRepository<T> {

    public void load() throws IOException;
    public void save();
    public void add(T obj);
    public void remove(T obj);
    public T get(String id);
    public List<T> toList();
    public Map<String, T> getObjects();
    public String getNextId(String prefix);
    public String getNextClassId();


}
