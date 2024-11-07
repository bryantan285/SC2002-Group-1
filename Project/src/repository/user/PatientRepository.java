package repository.user;

import entity.user.Patient;
import java.io.IOException;
import java.util.Iterator;
import repository.Repository;

public class PatientRepository extends Repository<Patient> {
    private static PatientRepository repo = null;
    private static final String FILE_PATH = "Project\\data\\Patient_List.csv";

    public static void main(String[] args) {
        try {
            PatientRepository repo = PatientRepository.getInstance();
            Iterator<Patient> iterator = repo.iterator();
            
            System.out.println(repo.getSize());
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private PatientRepository() throws IOException {
        super();
        load();
    }

    public static PatientRepository getInstance() {
        if (repo == null) {
            try {
                repo = new PatientRepository();
            } catch (IOException e) {
                System.err.println("Failed to create PatientRepository instance: " + e.getMessage());
                throw new RuntimeException("Failed to initialize PatientRepository", e);
            }
        }
        return repo;
    }

    @Override
    public String getFilePath() {
        return FILE_PATH;
    }

    @Override
    public Class<Patient> getEntityClass() {
        return Patient.class;
    }
}
