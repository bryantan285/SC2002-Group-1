package repository.request;

import entity.request.MedicineRequest;
import entity.request.Request;

import java.io.IOException;
import java.util.Iterator;
import repository.Repository;

public class MedicineRequestRepository extends Repository<MedicineRequest> {
    
    private static MedicineRequestRepository repo = null;
    private static final String FILE_PATH = "Project\\data\\MedicineRequest_List.csv";
    private static final String PREFIX = "MEDREQ";

    public static void main(String[] args) {
        try {
            MedicineRequestRepository repo = MedicineRequestRepository.getInstance();
            Iterator<MedicineRequest> iterator = repo.iterator();
            
            System.out.println(repo.getSize());
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(repo.findByField("status", Request.STATUS.PENDING));
    }

    private MedicineRequestRepository() throws IOException {
        super();
        load();
    }
    
    public static MedicineRequestRepository getInstance() {
        if (repo == null) {
            try {
                repo = new MedicineRequestRepository();
            } catch (IOException e) {
                System.err.println("Failed to create MedicineRequestRepository instance: " + e.getMessage());
                throw new RuntimeException("Failed to initialize MedicineRequestRepository", e);
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
    public Class<MedicineRequest> getEntityClass() {
        return MedicineRequest.class;
    }
}