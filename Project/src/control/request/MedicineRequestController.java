package control.request;

import control.medicine.MedicineController;
import entity.request.MedicineRequest;
import entity.request.Request;
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

        int incAmt = req.getAmount();
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
        return medicineRequestRepository.findByField("id", requestId).stream().findFirst().orElse(null);
    }

    public List<MedicineRequest> listPendingRequests() {
        return medicineRequestRepository.findByField("status", Request.STATUS.PENDING.name());
    }

    public void displayRequestDetails(String requestId) {
        MedicineRequest req = getRequestById(requestId);
        if (req == null) {
            System.out.println("Request not found: " + requestId);
            return;
        }

        System.out.println("Request ID: " + req.getId());
        System.out.println("Requestor ID: " + req.getRequestorId());
        System.out.println("Approver ID: " + req.getApproverId());
        System.out.println("Status: " + req.getStatus());
        System.out.println("Medicine ID: " + req.getMedicineId());
        System.out.println("Amount: " + req.getAmount());
        System.out.println("Time Created: " + req.getTimeCreated());
        System.out.println("Time Modified: " + req.getTimeModified());
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
