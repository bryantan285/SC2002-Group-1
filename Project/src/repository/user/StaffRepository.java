package repository.user;

import entity.user.HospitalStaff;
import entity.user.StaffFactory;
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
    public String getPrefix() {
        throw new UnsupportedOperationException("Use getPrefix(String role) instead for StaffRepository.");
    }

    public String getPrefix(HospitalStaff.Role role) {
        switch (role) {
            case DOCTOR:
                return "D";
            case PHARMACIST:
                return "PH";
            case ADMINISTRATOR:
                return "A";
            default:
                throw new IllegalArgumentException("Invalid staff role: " + role);
        }
    }
    public HospitalStaff createStaffByPrefix(String id) {
        return StaffFactory.createStaffByPrefix(id);
    }

    /**
 * Returns the next unique ID for the given HospitalStaff class or role.
 */
    public String getNextId(HospitalStaff.Role role) {
        String prefix = getPrefix(role);
        int maxIdNumber = 0;
    
        for (HospitalStaff staff : getInstance()) {
            String currentId = staff.getId();
            if (currentId.startsWith(prefix)) {
                try {
                    int idNumber = Integer.parseInt(currentId.substring(prefix.length()));
                    if (idNumber > maxIdNumber) {
                        maxIdNumber = idNumber;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Invalid ID format: " + currentId);
                }
            }
        }
    
        int nextIdNumber = maxIdNumber + 1;
        return prefix + nextIdNumber;
    }


    // @Override
    // public void load() throws IOException {
    //     List<HospitalStaff> objs = CSV_handler.readHospitalStaffFromCSV(getFilePath());
    //     setObjects(objs);
    // }

    @Override
    public String getFilePath() {
        return FILE_PATH;
    }

    @Override
    public Class<HospitalStaff> getEntityClass() {
        return HospitalStaff.class;
    }
}
