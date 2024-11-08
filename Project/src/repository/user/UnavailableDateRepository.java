package repository.user;

import entity.user.UnavailableDate;
import java.io.IOException;
import java.util.Iterator;
import repository.Repository;

public class UnavailableDateRepository extends Repository<UnavailableDate> {

    private static UnavailableDateRepository repo = null;
    private static final String FILE_PATH = "Project\\data\\UnavailableBlock_List.csv";
    private static final String PREFIX = "UD";

    public static void main(String[] args) {
        try {
            UnavailableDateRepository repo = UnavailableDateRepository.getInstance();
            Iterator<UnavailableDate> iterator = repo.iterator();
            
            System.out.println(repo.getSize());
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

     private UnavailableDateRepository() throws IOException {
        super();
        load();
    }

    public static UnavailableDateRepository getInstance() {
        if (repo == null) {
            try {
                repo = new UnavailableDateRepository();
            } catch (IOException e) {
                System.err.println("Failed to create UnavailableDateRepository instance: " + e.getMessage());
                throw new RuntimeException("Failed to initialize UnavailableDateRepository", e);
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
    public Class<UnavailableDate> getEntityClass() {
        return UnavailableDate.class;
    }
}
