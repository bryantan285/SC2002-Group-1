package control.medicine;

import entity.medicine.PrescriptionItem;
import interfaces.control.IController;
import java.util.List;
import java.util.Scanner;
import repository.medicine.PrescriptionItemRepository;

public class PrescriptionItemController implements IController {
    private final PrescriptionItemRepository prescriptionItemRepository;

    public PrescriptionItemController() {
        this.prescriptionItemRepository = PrescriptionItemRepository.getInstance();
    }

    @Override
    public void save() {
        prescriptionItemRepository.save();
    }

    public void renewPrescriptionItem(String itemId, int additionalDoses) {
        PrescriptionItem item = getPrescriptionItemById(itemId);
        boolean isActive = item.getIsActive();
        int totalDoses = item.getTotalDoses();
        if (isActive && totalDoses <= 100) {
            totalDoses += additionalDoses;
            item.setTotalDoses(totalDoses);
            System.out.println("Prescription item renewed successfully. Total doses: " + totalDoses);
        } else {
            System.out.println("Prescription item can't be renewed as it is either inactive or has reached the max number of doses.");
        }
    }

    public void displayPrescriptionItemDetails(String itemId) {
        PrescriptionItem item = getPrescriptionItemById(itemId);
        System.out.println("Item ID: " + item.getId());
        System.out.println("Prescription ID: " + item.getPrescriptionId());
        System.out.println("Medicine ID: " + item.getMedicineId());
        System.out.println("Medicine Name: " + item.getMedicineName());
        System.out.println("Total Doses: " + item.getTotalDoses());
        System.out.println("Doses Taken: " + item.getDosesTaken());
        System.out.println("Doses per Day: " + item.getDosesPerDay());
        System.out.println("Active: " + item.getIsActive());
    }

    public void checkActive(String itemId) {
        PrescriptionItem item = getPrescriptionItemById(itemId);
        if (item.getIsActive()) {
            System.out.println("Prescription item is active.");
        } else {
            System.out.println("Prescription item is inactive.");
        }
    }

    public void cancelPrescriptionItem(String itemId) {
        PrescriptionItem item = getPrescriptionItemById(itemId);
        boolean isActive = item.getIsActive();
        if (isActive) {
            item.setIsActive(false);
            System.out.println("Prescription item is cancelled.");
        } else {
            System.out.println("Prescription item is already inactive.");
        }
    }

    public void trackDoses(String itemId) {
        PrescriptionItem item = getPrescriptionItemById(itemId);
        int dosesTaken = item.getDosesTaken();
        int totalDoses = item.getTotalDoses();
        if (dosesTaken < totalDoses) {
            dosesTaken++;
            item.setDosesTaken(dosesTaken);
            System.out.println("Dose taken. Total doses taken: " + dosesTaken);
        } else {
            System.out.println("All doses have been taken.");
        }
    }

    public void remainingDoses(String itemId, Scanner scanner) {
        PrescriptionItem item = getPrescriptionItemById(itemId);
        System.out.print("Enter the total number of doses you have taken since beginning treatment: ");
        int dosesTaken = scanner.nextInt();
        int totalDoses = item.getTotalDoses();
        if (dosesTaken < totalDoses) {
            int remainingDoses = totalDoses - dosesTaken;
            System.out.println("Remaining doses to be taken: " + remainingDoses);
        } else {
            System.out.println("All doses have been taken.");
        }
    }

    public void adherenceRate(String itemId, Scanner scanner) {
        PrescriptionItem item = getPrescriptionItemById(itemId);
        System.out.print("Enter the total number of doses you have taken since beginning treatment: ");
        int dosesTaken = scanner.nextInt();

        double adherenceRate = ((double) dosesTaken / item.getTotalDoses()) * 100;
        System.out.println("Adherence rate: " + adherenceRate + "%");
    }

    public PrescriptionItem getPrescriptionItemById(String itemId) {
        return prescriptionItemRepository.findByField("id", itemId).stream().findFirst().orElse(null);
    }

    public List<PrescriptionItem> getPrescriptionItems(String prescriptionId) {
        return prescriptionItemRepository.findByField("prescriptionId", prescriptionId);
    }
}
