package utility;

import entity.EntityObject;
import entity.appointment.Appointment.AppointmentOutcome;
import entity.medicine.Prescription;
import entity.user.HospitalStaff;
import entity.user.StaffFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CSV_handler {

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
            String id = values[1].trim();
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
                return LocalDateTime.parse(value);
            } else if (fieldType == Date.class) {
                return new Date(value);
            }  else if (List.class.isAssignableFrom(fieldType)) {
                // Handle List deserialization (custom logic for AppointmentOutcome)
                return deserializeList(value);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid format for value: " + value + " of type " + fieldType.getName(), e);
        }

        throw new IllegalArgumentException("Unsupported field type: " + fieldType);
    }

    public static <T> List<T> readFromCSV(String filePath, Class<T> clazz) throws IOException {
        List<T> entities = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String headerLine = br.readLine();
            if (headerLine == null) {
                throw new IOException("CSV file is empty");
            }
            String[] headers = headerLine.split(",");

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", -1);
                T entity = createEntityFromCSV(values, headers, clazz);
                entities.add(entity);
            }
        }

        return entities;
    }

    private static <T> T createEntityFromCSV(String[] values, String[] headers, Class<T> clazz) {
        try {
            T entity = clazz.getDeclaredConstructor().newInstance();

            for (int i = 0; i < headers.length; i++) {
                String fieldName = headers[i].trim();
                Field field;

                try {
                    field = clazz.getDeclaredField(fieldName);
                } catch (NoSuchFieldException e) {
                    field = clazz.getSuperclass().getDeclaredField(fieldName);
                }

                field.setAccessible(true);
                Class<?> fieldType = field.getType();
                String value = values[i].trim();

                if (fieldType.isEnum()) {
                    Object enumValue = Enum.valueOf((Class<? extends Enum>) fieldType, value.toUpperCase());
                    field.set(entity, enumValue);
                } else {
                    Object convertedValue = convertValue(fieldType, value);
                    field.set(entity, convertedValue);
                }
            }

            return entity;
        } catch (Exception e) {
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

            if (value instanceof List) {
                // Handle List serialization (custom logic for AppointmentOutcome)
                return serializeList((List<?>) value);
            }

            return value.toString();
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error accessing field value", e);
        }
    }

    private static List<AppointmentOutcome> deserializeList(String value) {
        List<AppointmentOutcome> list = new ArrayList<>();
        if (value == null || value.isEmpty() || !value.startsWith("[") || !value.endsWith("]")) {
            return list;
        }
    
        // Remove surrounding square brackets
        value = value.substring(1, value.length() - 1);
    
        String[] items = value.split("\\],\\["); // Split by "],[" to handle the format
        for (String item : items) {
            String[] parts = item.split(","); // Split by commas to separate parts
            if (parts.length >= 3) {
                // Extract Diagnosis, Consultation Notes, and Prescription data
                String diagnosis = parts[0].replace("Diagnosis: ", "").trim();
                String consultationNotes = parts[1].replace("Consultation Notes: ", "").trim();
                List<Prescription> prescriptions = parsePrescriptions(parts[2].replace("Prescription ID: ", "").trim());
    
                // Create new AppointmentOutcome
                list.add(new AppointmentOutcome(diagnosis, consultationNotes, prescriptions));
            }
        }
        return list;
    }
    

    private static String serializeList(List<?> list) {
        if (list == null || list.isEmpty()) {
            return "[]"; // Empty list is represented as "[]"
        }
    
        List<String> serializedItems = new ArrayList<>();
        for (Object item : list) {
            if (item instanceof AppointmentOutcome) {
                AppointmentOutcome outcome = (AppointmentOutcome) item;
    
                // Format each part as "[Diagnosis: <value>]" and so on
                StringBuilder serializedOutcome = new StringBuilder();
                serializedOutcome.append("[Diagnosis: ").append(outcome.getDiagnosis()).append("], ");
                serializedOutcome.append("[Consultation Notes: ").append(outcome.getConsultationNotes()).append("], ");
    
                // Serialize Prescription
                String prescriptionsString = outcome.getPrescriptions().stream()
                    .map(p -> "[Prescription ID: " + p.getId() + "]")
                    .collect(Collectors.joining(", "));
    
                serializedOutcome.append(prescriptionsString);
    
                // Add this serialized outcome to the list
                serializedItems.add(serializedOutcome.toString());
            }
        }
    
        // Join the items as a single array-like string with square brackets around the whole list
        return "[" + String.join(", ", serializedItems) + "]"; 
    }
    

    // Helper method to parse prescriptions (adjust as per your Prescription class)
    private static List<Prescription> parsePrescriptions(String prescriptionsString) {
        // Implement logic to parse prescriptions
        return new ArrayList<>(); // Stub implementation
    }

}
