package utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CSV_handler {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy"); // Updated to dd-MM-yyyy

    public static <T> List<T> readFromCSV(String filePath, Class<T> clazz) throws IOException {
        List<T> entities = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String headerLine = br.readLine(); // Read the header
            if (headerLine == null) {
                throw new IOException("CSV file is empty");
            }
            String[] headers = headerLine.split(",");

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                T entity = createEntityFromCSV(values, headers, clazz);
                entities.add(entity); // Add the new entity to the list
            }
        }

        return entities;
    }

    private static <T> T createEntityFromCSV(String[] values, String[] headers, Class<T> clazz) {
        try {
            T entity = clazz.getDeclaredConstructor().newInstance(); // Ensure a new instance each time

            for (int i = 0; i < headers.length; i++) {
                String fieldName = headers[i].trim();
                Field field;

                // Check if the field exists in the current class or in the superclass
                try {
                    field = clazz.getDeclaredField(fieldName);
                } catch (NoSuchFieldException e) {
                    field = clazz.getSuperclass().getDeclaredField(fieldName);
                }

                field.setAccessible(true);
                Class<?> fieldType = field.getType();
                String value = values[i].trim();

                if (fieldType.isEnum()) {
                    Class<? extends Enum> enumType = (Class<? extends Enum>) fieldType;
                    Object enumValue = Enum.valueOf(enumType, value.toUpperCase());
                    field.set(entity, enumValue);
                } else {
                    Object convertedValue = convertValue(fieldType, value);
                    field.set(entity, convertedValue);
                }
            }

            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating entity from CSV", e);
        }
    }

    private static Object convertValue(Class<?> fieldType, String value) {
        if (fieldType == String.class) {
            return value;
        } else if (fieldType == int.class || fieldType == Integer.class) {
            return Integer.parseInt(value);
        } else if (fieldType == double.class || fieldType == Double.class) {
            return Double.parseDouble(value);
        } else if (fieldType == boolean.class || fieldType == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (fieldType == Date.class) {
            try {
                return DATE_FORMAT.parse(value);
            } catch (ParseException e) {
                throw new IllegalArgumentException("Invalid date format for value: " + value + ". Expected format: dd-MM-yyyy", e);
            }
        }
        throw new IllegalArgumentException("Unsupported field type: " + fieldType);
    }
}
