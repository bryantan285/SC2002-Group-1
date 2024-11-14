package control.request;

import control.medicine.MedicineController;
import entity.medicine.Medicine;
import entity.request.MedicineRequest;
import entity.request.Request;
import entity.request.Request.STATUS;
import entity.user.HospitalStaff;
import exception.EntityNotFoundException;
import exception.InvalidInputException;
import java.time.LocalDateTime;
import java.util.List;
import repository.request.MedicineRequestRepository;

public class MedicineRequestController {

    private static final MedicineRequestRepository repo = MedicineRequestRepository.getInstance();

    public static String createReplenishmentRequest(HospitalStaff staff, String medicineId, int quantity) throws InvalidInputException {
        if (staff.getId() == null || staff.getId().isEmpty()) {
            throw new InvalidInputException("Requestor ID cannot be null or empty.");
        }
        if (medicineId == null || medicineId.isEmpty()) {
            throw new InvalidInputException("Medicine ID cannot be null or empty.");
        }
        if (quantity <= 0) {
            throw new InvalidInputException("Quantity must be greater than zero.");
        }

        MedicineRequest req = new MedicineRequest();
        req.setId(repo.getNextClassId());
        req.setRequestorId(staff.getId());
        req.setApproverId(null);
        req.setStatus(STATUS.PENDING);
        req.setTimeCreated(LocalDateTime.now());
        req.setMedicineId(medicineId);
        req.setQuantity(quantity);
        repo.add(req);
        repo.save();
        return req.getId();
    }

    public static MedicineRequest getRequestById(String requestId) throws EntityNotFoundException, InvalidInputException {
        if (requestId == null || requestId.isEmpty()) {
            throw new InvalidInputException("Request ID cannot be null or empty.");
        }

        MedicineRequest req = repo.get(requestId);
        if (req == null) {
            throw new EntityNotFoundException("MedicineRequest", requestId);
        }
        return req;
    }

    public static List<MedicineRequest> getPendingRequests() {
        return repo.findByField("status", Request.STATUS.PENDING);
    }

    public static void approveReplenishmentRequest(HospitalStaff user, MedicineRequest req) throws InvalidInputException, EntityNotFoundException {
        if (user == null) {
            throw new InvalidInputException("Approver cannot be null.");
        }
        if (req == null || req.getStatus() != Request.STATUS.PENDING) {
            throw new InvalidInputException("Request is either null or not in PENDING status.");
        }

        req.setStatus(Request.STATUS.APPROVED);
        req.setApproverId(user.getId());
        req.setTimeModified(LocalDateTime.now());

        Medicine med = MedicineController.getMedicineById(req.getMedicineId());
        if (med == null) {
            throw new EntityNotFoundException("Medicine", req.getMedicineId());
        }

        MedicineController.incMedStock(med, req.getQuantity());
        repo.save();
    }

    public static void rejectReplenishmentRequest(HospitalStaff user, MedicineRequest req) throws InvalidInputException {
        if (user == null) {
            throw new InvalidInputException("Approver cannot be null.");
        }
        if (req == null || req.getStatus() != Request.STATUS.PENDING) {
            throw new InvalidInputException("Request is either null or not in PENDING status.");
        }

        req.setStatus(Request.STATUS.REJECTED);
        req.setApproverId(user.getId());
        req.setTimeModified(LocalDateTime.now());
        repo.save();
    }

    public static void updateRequestStatus(String requestId, Request.STATUS newStatus) throws InvalidInputException, EntityNotFoundException {
        if (requestId == null || requestId.isEmpty()) {
            throw new InvalidInputException("Request ID cannot be null or empty.");
        }

        MedicineRequest req = getRequestById(requestId);
        req.setStatus(newStatus);
        req.setTimeModified(LocalDateTime.now());
        repo.save();
    }

    public static void removeReplenishmentRequest(String requestId) throws InvalidInputException, EntityNotFoundException {
        if (requestId == null || requestId.isEmpty()) {
            throw new InvalidInputException("Request ID cannot be null or empty.");
        }

        MedicineRequest req = getRequestById(requestId);
        repo.remove(req);
        repo.save();
    }
}
