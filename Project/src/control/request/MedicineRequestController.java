package control.request;

import control.medicine.MedicineController;
import entity.request.MedicineRequest;
import entity.request.Request;
import java.util.Date;
import java.util.List;
import repository.request.MedicineRequestRepository;

public class MedicineRequestController {
    private final MedicineRequestRepository medicineRequestRepository;
    private final MedicineController medicineController;

    public static void main(String[] args) {
        MedicineRequestController mc = new MedicineRequestController();
        MedicineRequestRepository mrr = MedicineRequestRepository.getInstance();
        MedicineRequest mcreq = new MedicineRequest("MREQ001", "P001", null, Request.STATUS.PENDING, new Date(), null,"MED001", 10);
        MedicineRequest mcreq2 = new MedicineRequest("MREQ002", "P001", null, Request.STATUS.PENDING, new Date(), null,"MED001", 10);
        mrr.add(mcreq);
        mrr.add(mcreq2);
        mc.approveReplenishmentRequest("A001", mcreq.getId());
        mc.rejectReplenishmentRequest("A001", mcreq2.getId());
        System.out.println(mcreq.getStatus());
        System.out.println(mcreq2.getStatus());
    }

    public MedicineRequestController() {
        this.medicineRequestRepository = MedicineRequestRepository.getInstance();
        this.medicineController = new MedicineController();
    }

    public void approveReplenishmentRequest(String approverId, String requestId) {
        List<MedicineRequest> reqList = medicineRequestRepository.findByField("id", requestId);
        if (reqList.isEmpty()) {
        } else {
            MedicineRequest req = reqList.get(0);
            req.setStatus(Request.STATUS.APPROVED);
            req.setApproverId(approverId);
            req.setTimeModified(new Date());
            int incAmt = req.getAmount();
            String medId = req.getMedicineId();
            System.out.println(medicineController.checkMedicineAmt(medId));
            medicineController.addMedicine(medId, incAmt);
            System.out.println(medicineController.checkMedicineAmt(medId));
        }
    }

    public void rejectReplenishmentRequest(String approverId, String requestId) {
        List<MedicineRequest> reqList = medicineRequestRepository.findByField("id", requestId);
        if (reqList.isEmpty()) {
        } else {
            MedicineRequest req = reqList.get(0);
            req.setStatus(Request.STATUS.REJECTED);
            req.setApproverId(approverId);
            req.setTimeModified(new Date());
        }
    }
}
