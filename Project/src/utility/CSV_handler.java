package utility;

import entity.EntityObject;
import entity.user.HospitalStaff;
import entity.user.StaffFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CSV_handler {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("[d-M-yyyy][dd-MM-yyyy]");
    private static final SimpleDateFormat LEGACY_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    public static List<HospitalStaff> readHospitalStaffFromCSV(String filePath) throws IOException {
        List<HospitalStaff> staffList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String headerLine = br.readLine();
            if (headerLine == null) {
                throw new IOException("CSV file is empty");
            }
            String[] headers = headerLine.split(",");

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                HospitalStaff staff = createHospitalStaffFromCSV(values, headers);
                staffList.add(staff);
            }
        }

        return staffList;
    }

    private static HospitalStaff createHospitalStaffFromCSV(String[] values, String[] headers) {
        try {
            String id = values[0].trim();
            HospitalStaff staff = StaffFactory.createStaffByPrefix(id);

            for (int i = 0; i < headers.length; i++) {
                String fieldName = headers[i].trim();
                Field field = getFieldFromClassHierarchy(staff.getClass(), fieldName);
                field.setAccessible(true);
                Class<?> fieldType = field.getType();
                String value = values[i].trim();

                if (fieldType.isEnum()) {
                    Object enumValue = Enum.valueOf((Class<? extends Enum>) fieldType, value.toUpperCase());
                    field.set(staff, enumValue);
                } else {
                    Object convertedValue = convertValue(fieldType, value);
                    field.set(staff, convertedValue);
                }
            }

            return staff;
        } catch (Exception e) {
            throw new RuntimeException("Error creating HospitalStaff entity from CSV", e);
        }
    }

    private static Field getFieldFromClassHierarchy(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        Class<?> currentClass = clazz;
        while (currentClass != null) {
            try {
                return currentClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                currentClass = currentClass.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }
    
    private static Object convertValue(Class<?> fieldType, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
    
        try {
            if (fieldType == String.class) {
                return value;
            } else if (fieldType == int.class || fieldType == Integer.class) {
                return Integer.parseInt(value);
            } else if (fieldType == double.class || fieldType == Double.class) {
                return Double.parseDouble(value);
            } else if (fieldType == float.class || fieldType == Float.class) {
                return Float.parseFloat(value);
            } else if (fieldType == boolean.class || fieldType == Boolean.class) {
                return Boolean.parseBoolean(value);
            } else if (fieldType == LocalDateTime.class) {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("[d-M-yyyy HH:mm][dd-MM-yyyy HH:mm]");
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("[d-M-yyyy][dd-MM-yyyy]");
                try {
                    return LocalDateTime.parse(value, dateTimeFormatter);
                } catch (Exception e) {
                    LocalDate date = LocalDate.parse(value, dateFormatter);
                    return date.atStartOfDay();
                }
            } else if (fieldType == LocalDate.class) {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("[d-M-yyyy][dd-MM-yyyy]");
                return LocalDate.parse(value, dateFormatter);
            } else if (fieldType == Date.class) {
                SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("d-M-yyyy HH:mm");
                try {
                    return dateTimeFormatter.parse(value);
                } catch (ParseException e) {
                    try {
                        dateTimeFormatter.applyPattern("dd-MM-yyyy HH:mm");
                        return dateTimeFormatter.parse(value);
                    } catch (ParseException ex) {
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("d-M-yyyy");
                        try {
                            return dateFormatter.parse(value);
                        } catch (ParseException exc) {
                            dateFormatter.applyPattern("dd-MM-yyyy");
                            return dateFormatter.parse(value);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid format for value: " + value + " of type " + fieldType.getName(), e);
        }
    
        throw new IllegalArgumentException("Unsupported field type: " + fieldType);
    }
    

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

    public static <T extends EntityObject> void writeToCSV(String filePath, Map<String, T> objMap, Class<T> clazz) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            Field[] superFields = clazz.getSuperclass().getDeclaredFields();
            Field[] fields = clazz.getDeclaredFields();
            StringBuilder header = new StringBuilder();
            for (Field superField : superFields) {
                header.append(superField.getName()).append(",");
            }
            for (Field field : fields) {
                header.append(field.getName()).append(",");
            }
            bw.write(header.substring(0, header.length() - 1));
            bw.newLine();
    
            for (T obj : objMap.values()) {
                StringBuilder line = new StringBuilder();
                for (Field superField : superFields) {
                    line.append(getFieldValue(obj, superField)).append(",");
                }
                for (Field field : fields) {
                    line.append(getFieldValue(obj, field)).append(",");
                }
                bw.write(line.substring(0, line.length() - 1));
                bw.newLine();
            }
        }
    }

    private static <T> String getFieldValue(T obj, Field field) {
        try {
            field.setAccessible(true);
            Object value = field.get(obj);
            if (value == null) {
                return "";
            }
            if (value instanceof LocalDate) {
                return DateTimeFormatter.ofPattern("dd-MM-yyyy").format((LocalDate) value);
            }
            if (value instanceof LocalDateTime) {
                return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").format((LocalDateTime) value);
            }
            if (value instanceof Date) {
                return new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm").format((Date) value);
            }
            if (value instanceof Enum) {
                return ((Enum<?>) value).name();
            }
            return value.toString();
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error accessing field value", e);
        }
    }
    
    
}
