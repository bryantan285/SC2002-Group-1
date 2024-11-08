package repository;

import entity.EntityObject;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utility.CSV_handler;

public abstract class Repository<T extends EntityObject> implements Iterable<T> {

    private Map<String,T> objMap;

    public static void main(String[] args) {
        System.out.println("Testing");
        }

    public Repository() {
        objMap = new HashMap<>();
    }

    public abstract String getFilePath();

    public abstract Class<T> getEntityClass();

    public abstract String getPrefix();

    public void load() throws IOException {
        List<T> objs = CSV_handler.readFromCSV(getFilePath(), getEntityClass());
        setObjects(objs);
    }

    public void save() {
        try {
            CSV_handler.writeToCSV(getFilePath(), objMap, getEntityClass());
        } catch (IOException e) {
            System.err.println("Error saving data to CSV file: " + getFilePath());
            e.printStackTrace();
        }
    }

    public Map<String, T> getObjects() {
        return objMap;
    }

    public List<T> toList() {
        List<T> objs = new ArrayList<>();
        objMap.forEach((k,v) -> {
            objs.add(v);
        });

        return objs;
    }

    public int getSize() {
        return objMap.size();
    }

    public void remove(T obj) {
        objMap.remove(obj.getId());
    }

    public void add(T obj) {
        objMap.put(obj.getId(), obj);
    }

    public T get(String id) {
        return objMap.get(id);
    }

    public void update(T obj) {
        String id = obj.getId();
        if (objMap.containsKey(id)) {
            objMap.put(id, obj);
        }
    }

    public void setObjects(List<T> entities) {
        for (T entity : entities) {
            objMap.put(entity.getId(),entity);
        }
    }

    public List<T> findByField(String fieldName, Object value) {
        List<T> matchingObjects = new ArrayList<>();
    
        for (T obj : objMap.values()) {
            try {
                Field field = getFieldFromClassHierarchy(obj.getClass(), fieldName);
                field.setAccessible(true);  // Allows access to private fields
                Object fieldValue = field.get(obj);
    
                if (fieldValue != null && fieldValue.equals(value)) {
                    matchingObjects.add(obj);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                System.out.println("Error accessing field '" + fieldName + "' on object: " + obj);
                e.printStackTrace();
            }
        }
    
        return matchingObjects;
    }

    private Field getFieldFromClassHierarchy(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        Class<?> currentClass = clazz;
        while (currentClass != null) {
            try {
                return currentClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                currentClass = currentClass.getSuperclass(); // Move up the hierarchy
            }
        }
        throw new NoSuchFieldException(fieldName); // Field not found in the hierarchy
    }

    public List<String> getAllIds() {
        List<String> idList = new ArrayList<>();
    
        for (T obj : objMap.values()) {
            idList.add(obj.getId());
        }
    
        return idList;
    }

    public String getNextId(String prefix) {
        if (objMap.isEmpty()) {
            return prefix + "001";
        }
        int highestCount = 0;
        Pattern pattern = Pattern.compile("^" + prefix + "(\\d+)$");
    
        for (T obj : objMap.values()) {
            String id = obj.getId();
            Matcher matcher = pattern.matcher(id);
            if (matcher.matches()) {
                int count = Integer.parseInt(matcher.group(1));
                highestCount = Math.max(highestCount, count);
            }
        }
        int nextCount = highestCount + 1;
        return prefix + String.format("%03d", nextCount);
    }

    public String getNextClassId() {
        return getNextId(getPrefix());
    }

    @Override
    public Iterator<T> iterator() {
        return objMap.values().iterator();
    }

    public void printObjs() {
        Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            T iter = iterator.next();
            System.out.println(iter);
        }
    }
}


