package control.request;

import control.medicine.MedicineController;
import entity.request.MedicineRequest;
import entity.request.Request;
import entity.request.Request.STATUS;
import interfaces.control.IController;
import java.time.LocalDateTime;
import java.util.List;
import repository.request.MedicineRequestRepository;

public class MedicineRequestController implements IController {
    private final MedicineRequestRepository medicineRequestRepository;
    private final MedicineController medicineController;

    public static void main(String[] args) {
        MedicineRequestController mc = new MedicineRequestController();
        MedicineRequestRepository mrr = MedicineRequestRepository.getInstance();
        MedicineRequest mreq = mrr.get("MREQ001");

        mc.approveReplenishmentRequest("A001", mreq.getId());

        System.out.println("Status of Request 1: " + mreq.getStatus());
    }

    @Override
    public void save() {
        medicineRequestRepository.save();
    }

    public MedicineRequestController() {
        this.medicineRequestRepository = MedicineRequestRepository.getInstance();
        this.medicineController = new MedicineController();
    }

    public String createReplenishmentRequest(String requestorId,String medicineId, int quantity) {
        MedicineRequest req = new MedicineRequest();
        req.setId(medicineRequestRepository.getNextClassId());
        req.setRequestorId(requestorId);
        req.setApproverId(null);
        req.setStatus(STATUS.PENDING);
        req.setTimeCreated(LocalDateTime.now());
        req.setTimeCreated(LocalDateTime.now());
        req.setMedicineId(medicineId);
        req.setQuantity(quantity);
        medicineRequestRepository.add(req);
        save();
        return req.getId();
    }

    public void removeReplenishmentRequest(String requestId) {
        MedicineRequest req = getRequestById(requestId);
        medicineRequestRepository.remove(req);
    }

    public void approveReplenishmentRequest(String approverId, String requestId) {
        MedicineRequest req = getRequestById(requestId);
        if (req == null) {
            System.out.println("Request not found: " + requestId);
            return;
        }

        if (req.getStatus() != Request.STATUS.PENDING) {
            System.out.println("Request is not in a pending state: " + requestId);
            return;
        }

        req.setStatus(Request.STATUS.APPROVED);
        req.setApproverId(approverId);
        req.setTimeModified(LocalDateTime.now());

        int incAmt = req.getQuantity();
        String medId = req.getMedicineId();
        medicineController.addMedicine(medId, incAmt);

        save();
        System.out.println("Request approved: " + requestId);
    }

    public void rejectReplenishmentRequest(String approverId, String requestId) {
        MedicineRequest req = getRequestById(requestId);
        if (req == null) {
            System.out.println("Request not found: " + requestId);
            return;
        }

        if (req.getStatus() != Request.STATUS.PENDING) {
            System.out.println("Request is not in a pending state: " + requestId);
            return;
        }

        req.setStatus(Request.STATUS.REJECTED);
        req.setApproverId(approverId);
        req.setTimeModified(LocalDateTime.now());

        save();
        System.out.println("Request rejected: " + requestId);
    }

    public MedicineRequest getRequestById(String requestId) {
        return medicineRequestRepository.get(requestId);
    }

    public List<MedicineRequest> getPendingRequests() {
        return medicineRequestRepository.findByField("status", Request.STATUS.PENDING);
    }

    public void updateRequestStatus(String requestId, Request.STATUS newStatus) {
        MedicineRequest req = getRequestById(requestId);
        if (req == null) {
            System.out.println("Request not found: " + requestId);
            return;
        }

        req.setStatus(newStatus);
        req.setTimeModified(LocalDateTime.now());
        save();
        System.out.println("Request status updated: " + requestId + " to " + newStatus);
    }
}
