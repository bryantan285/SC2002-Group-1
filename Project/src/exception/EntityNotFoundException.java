package exception;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String entityType, String id) {
        super(entityType + " not found with ID: " + id);
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}