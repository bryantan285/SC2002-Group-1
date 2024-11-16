package repository.user;

import entity.user.UnavailableDate;
import java.io.IOException;
import java.util.Iterator;
import repository.Repository;

/**
 * UnavailableDateRepository is a singleton class that manages the collection of UnavailableDate entities.
 * It extends the Repository class and provides methods to load, retrieve, and iterate over the list of
 * unavailable dates stored in a CSV file.
 */
public class UnavailableDateRepository extends Repository<UnavailableDate> {

    private static UnavailableDateRepository repo = null;
    private static final String FILE_PATH = "Project\\data\\UnavailableDate_List.csv";
    private static final String PREFIX = "UD";

    /**
     * The main method demonstrates how to use the UnavailableDateRepository. It prints the total number
     * of unavailable dates and iterates through all the unavailable dates in the repository.
     *
     * @param args command line arguments (not used)
     */
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

    /**
     * Private constructor for the UnavailableDateRepository. It calls the parent constructor
     * and loads the list of unavailable dates from the CSV file.
     *
     * @throws IOException if there is an error loading the data from the CSV file
     */
    private UnavailableDateRepository() throws IOException {
        super();
        load();
    }

    /**
     * Provides access to the singleton instance of the UnavailableDateRepository. If the instance does
     * not exist, it creates a new one.
     *
     * @return the singleton instance of UnavailableDateRepository
     */
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

    /**
     * Returns the prefix used for identifying UnavailableDate entities.
     *
     * @return the prefix "UD"
     */
    @Override
    public String getPrefix() {
        return PREFIX;
    }

    /**
     * Returns the file path where the unavailable date data is stored.
     *
     * @return the file path "Project\\data\\UnavailableDate_List.csv"
     */
    @Override
    public String getFilePath() {
        return FILE_PATH;
    }

    /**
     * Returns the class type of the entity managed by this repository, which is UnavailableDate.
     *
     * @return the class type UnavailableDate.class
     */
    @Override
    public Class<UnavailableDate> getEntityClass() {
        return UnavailableDate.class;
    }
}
