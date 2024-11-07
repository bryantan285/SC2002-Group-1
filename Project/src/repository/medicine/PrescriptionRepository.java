package repository.medicine;

import entity.medicine.Prescription;
import java.io.IOException;
import java.util.Iterator;
import repository.Repository;

public class PrescriptionRepository extends Repository<Prescription> {
        
    private static PrescriptionRepository repo = null;
    private static final String FILE_PATH = "Project\\data\\Prescription_List.csv";

    public static void main(String[] args) {
        try {
            PrescriptionRepository repo = PrescriptionRepository.getInstance();
            Iterator<Prescription> iterator = repo.iterator();
            
            System.out.println(repo.getSize());
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private PrescriptionRepository() throws IOException {
        super();
        load();
    }
    
    public static PrescriptionRepository getInstance() {
        if (repo == null) {
            try {
                repo = new PrescriptionRepository();
            } catch (IOException e) {
                System.err.println("Failed to create PrescriptionRepository instance: " + e.getMessage());
                throw new RuntimeException("Failed to initialize PrescriptionRepository", e);
            }
        }
        return repo;
    }
    
    @Override
    public String getFilePath() {
        return FILE_PATH;
    }

    @Override
    public Class<Prescription> getEntityClass() {
        return Prescription.class;
    }
}
