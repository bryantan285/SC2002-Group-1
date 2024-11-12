package control.user;

import entity.user.UnavailableDate;
import interfaces.control.ISavable;
import java.time.LocalDateTime;
import java.util.List;
import repository.user.UnavailableDateRepository;

public class UnavailableDateController implements ISavable {
    private final UnavailableDateRepository unavailableDateRepository;

    public UnavailableDateController() {
        this.unavailableDateRepository = UnavailableDateRepository.getInstance();
    }

    

    @Override
    public void save() {
        unavailableDateRepository.save();
    }

    public String addUnavailablity(String doctorId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (UnavailableDate.isValidDuration(startDateTime, endDateTime)) {
            String newId = unavailableDateRepository.getNextClassId();
            unavailableDateRepository.add(new UnavailableDate(newId, doctorId, startDateTime, endDateTime));
            unavailableDateRepository.save();
            return "Added successfully";
        } else
            return "Invalid duration.";
    }

    public String removeUnavailability(String doctorId, String blockId) {
        unavailableDateRepository.remove(unavailableDateRepository.get(blockId));
        unavailableDateRepository.save();
        return "Removed successfully";
    }

    public List<UnavailableDate> getUnavailableDates(String doctorId) {
        return unavailableDateRepository.findByField("doctorId", doctorId);
    }
}
