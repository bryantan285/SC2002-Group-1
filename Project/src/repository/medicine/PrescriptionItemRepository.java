package repository.medicine;

import entity.medicine.PrescriptionItem;
import java.io.IOException;
import java.util.Iterator;
import repository.Repository;

public class PrescriptionItemRepository extends Repository<PrescriptionItem> {
        
    private static PrescriptionItemRepository repo = null;
    private static final String FILE_PATH = "Project\\data\\PrescriptionItem_List.csv";
    private static final String PREFIX = "PRSCI";

    public static void main(String[] args) {
        try {
            PrescriptionItemRepository repo = PrescriptionItemRepository.getInstance();
            Iterator<PrescriptionItem> iterator = repo.iterator();
            
            System.out.println(repo.getSize());
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private PrescriptionItemRepository() throws IOException {
        super();
        load();
    }
    
    public static PrescriptionItemRepository getInstance() {
        if (repo == null) {
            try {
                repo = new PrescriptionItemRepository();
            } catch (IOException e) {
                System.err.println("Failed to create PrescriptionItemRepository instance: " + e.getMessage());
                throw new RuntimeException("Failed to initialize PrescriptionRepository", e);
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
    public Class<PrescriptionItem> getEntityClass() {
        return PrescriptionItem.class;
    }
}
