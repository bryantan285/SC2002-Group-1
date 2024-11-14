package interfaces.control;

import repository.Repository;

public interface ISavable {
    public static void save(Repository repo) {
        repo.save();
    }
}
