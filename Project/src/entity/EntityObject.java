package entity;

public abstract class EntityObject {

    public abstract String getId();

    public boolean isValid() {
        return getId() != null;
    }    
}
