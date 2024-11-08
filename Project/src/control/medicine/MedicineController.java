package control.medicine;

import entity.medicine.Medicine;
import interfaces.control.IController;
import java.time.LocalDateTime;
import java.util.List;
import repository.medicine.MedicineRepository;

public class MedicineController implements IController {
    private final MedicineRepository medicineRepository;

    public MedicineController() {
        this.medicineRepository = MedicineRepository.getInstance();
    }

    @Override
    public void save() {
        medicineRepository.save();
    }

    public Integer checkMedicineAmt(String medicineId) {
        Medicine med = getMedicineById(medicineId);
        return med != null ? med.getStockQuantity() : null;
    }

    public Integer restockMedicine(String medicineId, int quantity) {
        Medicine med = getMedicineById(medicineId);
        if (med == null) {
            System.out.println("Medicine not found.");
            return null;
        }
        med.restock(quantity);
        save();
        return med.getStockQuantity();
    }

    public Integer addMedicine(String medicineId, int value) {
        Medicine med = getMedicineById(medicineId);
        if (med == null) {
            System.out.println("Medicine not found.");
            return null;
        }
        med.incStock(value);
        save();
        return med.getStockQuantity();
    }

    public boolean checkLowStock(String medicineId) {
        Medicine med = getMedicineById(medicineId);
        if (med == null) {
            System.out.println("Medicine not found.");
            return false;
        }
        return med.getStockQuantity() < med.getLowStockThreshold();
    }

    public Integer dispenseMedicine(String medicineId, int quantity) {
        Medicine med = getMedicineById(medicineId);
        if (med == null) {
            System.out.println("Medicine not found.");
            return null;
        }
        if (med.decStock(quantity)) {
            save();
            System.out.println("Dispensed " + quantity + " units of " + med.getMedicineName() + ". Remaining stock: " + med.getStockQuantity());
            return quantity;
        } else {
            System.out.println("Not enough stock to dispense " + quantity + " units of " + med.getMedicineName());
            return 0;
        }
    }

    public void checkAvailability(String medicineId) {
        Medicine med = getMedicineById(medicineId);
        if (med == null) {
            System.out.println("Medicine not found.");
        } else {
            med.checkAvailability();
        }
    }

    public boolean updateMedicineDetails(String medicineId, String newName, double newDosage, int newLowStockThreshold) {
        Medicine med = getMedicineById(medicineId);
        if (med == null) {
            System.out.println("Medicine not found.");
            return false;
        }
        med.setMedicineName(newName);
        med.setDosage(newDosage);
        med.setLowStockThreshold(newLowStockThreshold);
        save();
        System.out.println("Medicine details updated successfully.");
        return true;
    }

    public boolean removeMedicine(String medicineId) {
        Medicine med = getMedicineById(medicineId);
        if (med == null) {
            System.out.println("Medicine not found.");
            return false;
        }
        medicineRepository.remove(med);
        save();
        System.out.println("Medicine removed successfully.");
        return true;
    }

    public Medicine getMedicineById(String id) {
        return medicineRepository.get(id);
    }

    public List<Medicine> getAllMedicines() {
        return medicineRepository.toList();
    }

    public boolean setLowStockThreshold(String medicineId, int newThreshold) {
        Medicine med = getMedicineById(medicineId);
        if (med == null) {
            System.out.println("Medicine not found.");
            return false;
        }
        med.setLowStockThreshold(newThreshold);
        save();
        System.out.println("Low stock threshold updated successfully.");
        return true;
    }

    public boolean isMedicineExpired(String medicineId) {
        Medicine med = getMedicineById(medicineId);
        if (med == null) {
            System.out.println("Medicine not found.");
            return false;
        }
        return med.getExpirationDate().isBefore(LocalDateTime.now());
    }

    public void listExpiredMedicines() {
        List<Medicine> medicines = getAllMedicines();
        boolean foundExpired = false;
        for (Medicine med : medicines) {
            if (med.getExpirationDate().isBefore(LocalDateTime.now())) {
                System.out.println(med);
                foundExpired = true;
            }
        }
        if (!foundExpired) {
            System.out.println("No expired medicines found.");
        }
    }

    public List<Medicine> searchMedicineByName(String name) {
        return medicineRepository.findByField("medicineName", name);
    }
}
