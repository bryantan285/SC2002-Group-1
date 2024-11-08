package repository.medicine;

import entity.medicine.Medicine;
import java.io.IOException;
import java.util.Iterator;
import repository.Repository;

public class MedicineRepository extends Repository<Medicine> {
    
    private static MedicineRepository repo = null;
    private static final String FILE_PATH = "Project\\data\\Medicine_List.csv";
    private static final String PREFIX = "MED";

    public static void main(String[] args) {
        try {
            MedicineRepository repo = MedicineRepository.getInstance();
            Iterator<Medicine> iterator = repo.iterator();
            
            System.out.println(repo.getSize());
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private MedicineRepository() throws IOException {
        super();
        load();
    }
    
    public static MedicineRepository getInstance() {
        if (repo == null) {
            try {
                repo = new MedicineRepository();
            } catch (IOException e) {
                System.err.println("Failed to create MedicineRepository instance: " + e.getMessage());
                throw new RuntimeException("Failed to initialize MedicineRepository", e);
            }
        }
        return repo;
    }

    @Override
    public String getPrefix() {
        return PREFIX;
    }
    
    @Override
    public String getFilePath() {
        return FILE_PATH;
    }

    @Override
    public Class<Medicine> getEntityClass() {
        return Medicine.class;
    }
}