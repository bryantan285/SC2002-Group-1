package control.user;

import entity.user.HospitalStaff;
import entity.user.UnavailableDate;
import exception.EntityNotFoundException;
import exception.InvalidInputException;
import java.time.LocalDateTime;
import java.util.List;
import repository.user.UnavailableDateRepository;

public class UnavailableDateController {

    private static final UnavailableDateRepository repo = UnavailableDateRepository.getInstance();

    public static String addUnavailability(HospitalStaff staff, LocalDateTime date) throws InvalidInputException {
        if (staff == null) {
            throw new InvalidInputException("Hospital staff cannot be null.");
        }
        if (date == null) {
            throw new InvalidInputException("Date cannot be null.");
        }
        if (isDateUnavailable(staff.getId(), date)) {
            throw new InvalidInputException("The specified date is already marked as unavailable.");
        }

        String newId = repo.getNextClassId();
        repo.add(new UnavailableDate(newId, staff.getId(), date));
        repo.save();
        return "Added successfully.";
    }

    public static Boolean removeUnavailability(String unavabilityId) throws EntityNotFoundException, InvalidInputException {
        if (unavabilityId == null || unavabilityId.isEmpty()) {
            throw new InvalidInputException("Block ID cannot be null or empty.");
        }
        UnavailableDate unavailableDate = repo.get(unavabilityId);
        if (unavailableDate == null) {
            throw new EntityNotFoundException("UnavailableDate", unavabilityId);
        }

        repo.remove(unavailableDate);
        repo.save();
        return true;
    }

    public static List<UnavailableDate> getUnavailableDates(HospitalStaff staff) throws InvalidInputException {
        if (staff == null) {
            throw new InvalidInputException("Hospital staff cannot be null.");
        }
        return repo.findByField("staffId", staff.getId());
    }

    public static boolean isDateUnavailable(String staffId, LocalDateTime date) throws InvalidInputException {
        if (staffId == null || staffId.isEmpty()) {
            throw new InvalidInputException("Staff ID cannot be null or empty.");
        }
        if (date == null) {
            throw new InvalidInputException("Date cannot be null.");
        }

        List<UnavailableDate> unavailableDates = repo.findByField("staffId", staffId);
        return unavailableDates.stream().anyMatch(unavailable -> unavailable.getDate().isEqual(date));
    }
}
