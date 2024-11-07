package repository.appointment;

import entity.appointment.Appointment;
import java.io.IOException;
import java.util.Iterator;
import repository.Repository;

public class AppointmentRepository extends Repository<Appointment> {
    
    private static AppointmentRepository repo = null;
    private static final String FILE_PATH = "Project\\data\\Appointment_List.csv";

    public static void main(String[] args) {
        try {
            AppointmentRepository repo = AppointmentRepository.getInstance();
            Iterator<Appointment> iterator = repo.iterator();
            
            System.out.println(repo.getSize());
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private AppointmentRepository() throws IOException {
        super();
        load();
    }
    
    public static AppointmentRepository getInstance() {
        if (repo == null) {
            try {
                repo = new AppointmentRepository();
            } catch (IOException e) {
                System.err.println("Failed to create AppointmentRepository instance: " + e.getMessage());
                throw new RuntimeException("Failed to initialize AppointmentRepository", e);
            }
        }
        return repo;
    }

    @Override
    public String getFilePath() {
        return FILE_PATH;
    }

    @Override
    public Class<Appointment> getEntityClass() {
        return Appointment.class;
    }
}