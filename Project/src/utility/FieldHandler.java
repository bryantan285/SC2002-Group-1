package utility;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FieldHandler {
    public static List<String> getFieldNames(Object obj) {
        List<String> fieldNames = new ArrayList<>();
        List<Class<?>> classHierarchy = new ArrayList<>();

        Class<?> currentClass = obj.getClass();
        while (currentClass != null) {
            classHierarchy.add(currentClass);
            currentClass = currentClass.getSuperclass();
        }

        for (int i = classHierarchy.size() - 1; i >= 0; i--) {
            Class<?> clazz = classHierarchy.get(i);
            for (Field field : clazz.getDeclaredFields()) {
                fieldNames.add(field.getName());
            }
        }

        return fieldNames;
    }
}
