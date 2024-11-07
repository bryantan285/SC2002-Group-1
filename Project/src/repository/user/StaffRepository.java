package repository.user;

import entity.user.HospitalStaff;
import java.io.IOException;
import java.util.Iterator;
import repository.Repository;

public class StaffRepository extends Repository<HospitalStaff> {
    private static StaffRepository repo = null;
    private static final String FILE_PATH = "Project\\data\\Staff_List.csv";

    public static void main(String[] args) {
        try {
            StaffRepository repo = StaffRepository.getInstance();
            Iterator<HospitalStaff> iterator = repo.iterator();
            
            System.out.println(repo.getSize());
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private StaffRepository() throws IOException {
        super();
        load();
    }

    public static StaffRepository getInstance() {
        if (repo == null) {
            try {
                repo = new StaffRepository();
            } catch (IOException e) {
                System.err.println("Failed to create StaffRepository instance: " + e.getMessage());
                throw new RuntimeException("Failed to initialize StaffRepository", e);
            }
        }
        return repo;
    }

    @Override
    public String getFilePath() {
        return FILE_PATH;
    }

    @Override
    public Class<HospitalStaff> getEntityClass() {
        return HospitalStaff.class;
    }
}
