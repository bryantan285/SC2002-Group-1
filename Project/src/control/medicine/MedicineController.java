package control.medicine;

import entity.medicine.Medicine;
import exception.EntityNotFoundException;
import exception.InvalidInputException;
import java.util.List;
import repository.medicine.MedicineRepository;

public class MedicineController {

    private static final MedicineRepository medicineRepository = MedicineRepository.getInstance();

    public static Boolean restockMedicine(Medicine med, int quantity) throws InvalidInputException, EntityNotFoundException {
        if (med == null) {
            throw new EntityNotFoundException("Medicine", "null");
        }
        if (quantity <= 0) {
            throw new InvalidInputException("Quantity must be greater than zero.");
        }
        med.restock(quantity);
        medicineRepository.save();
        return true;
    }

    public static Medicine getMedicineById(String id) throws InvalidInputException, EntityNotFoundException {
        if (id == null || id.isEmpty()) {
            throw new InvalidInputException("Medicine ID cannot be null or empty.");
        }

        Medicine med = medicineRepository.get(id);
        if (med == null) {
            throw new EntityNotFoundException("Medicine", id);
        }
        return med;
    }

    public static List<Medicine> getAllMedicines() {
        return medicineRepository.toList();
    }

    public static Integer checkMedicineAmt(String medicineId) throws InvalidInputException, EntityNotFoundException {
        Medicine med = getMedicineById(medicineId);
        return med.getStockQuantity();
    }

    public static Boolean checkLowStock(Medicine med) throws InvalidInputException {
        if (med == null) {
            throw new InvalidInputException("Medicine cannot be null.");
        }
        return med.getStockQuantity() < med.getLowStockThreshold();
    }

    public static Boolean checkAvailability(Medicine med) throws InvalidInputException {
        if (med == null) {
            throw new InvalidInputException("Medicine cannot be null.");
        }
        return med.checkAvailability();
    }

    public static List<Medicine> searchMedicineByName(String name) throws InvalidInputException {
        if (name == null || name.isEmpty()) {
            throw new InvalidInputException("Medicine name cannot be null or empty.");
        }
        return medicineRepository.findByField("medicineName", name);
    }

    public static Boolean updateMedicineDetails(Medicine med, String newName, double newDosage, int newLowStockThreshold) throws InvalidInputException, EntityNotFoundException {
        if (med == null) {
            throw new EntityNotFoundException("Medicine", "null");
        }
        if (newName == null || newName.isEmpty()) {
            throw new InvalidInputException("Medicine name cannot be null or empty.");
        }
        if (newDosage <= 0) {
            throw new InvalidInputException("Dosage must be greater than zero.");
        }
        if (newLowStockThreshold <= 0) {
            throw new InvalidInputException("Low stock threshold must be greater than zero.");
        }
        med.setMedicineName(newName);
        med.setDosage(newDosage);
        med.setLowStockThreshold(newLowStockThreshold);
        medicineRepository.save();
        return true;
    }

    public static Boolean incMedStock(Medicine med, int amount) throws InvalidInputException, EntityNotFoundException {
        if (med == null) {
            throw new EntityNotFoundException("Medicine", "null");
        }
        if (amount <= 0) {
            throw new InvalidInputException("Amount must be greater than zero.");
        }
        med.incStock(amount);
        medicineRepository.save();
        return true;
    }

    public static Boolean decMedStock(Medicine med, int amount) throws InvalidInputException, EntityNotFoundException {
        if (med == null) {
            throw new EntityNotFoundException("Medicine", "null");
        }
        if (amount <= 0) {
            throw new InvalidInputException("Amount must be greater than zero.");
        }
        try {
            med.decStock(amount);
            medicineRepository.save();
            return true;
        } catch (Exception e) {
            throw new InvalidInputException(e.getMessage());
        }
    }

    public static Boolean updateMedStock(Medicine med, int amount) throws InvalidInputException, EntityNotFoundException {
        if (med == null) {
            throw new EntityNotFoundException("Medicine", "null");
        }
        if (amount < 0) {
            throw new InvalidInputException("Stock quantity cannot be negative.");
        }
        try {
            med.setStockQuantity(amount);
            medicineRepository.save();
            return true;
        } catch (Exception e) {
            throw new InvalidInputException(e.getMessage());
        }
    }

    public static Boolean setLowStockThreshold(Medicine med, int newThreshold) throws InvalidInputException, EntityNotFoundException {
        if (med == null) {
            throw new EntityNotFoundException("Medicine", "null");
        }
        if (newThreshold <= 0) {
            throw new InvalidInputException("Low stock threshold must be greater than zero.");
        }
        try {
            med.setLowStockThreshold(newThreshold);
            medicineRepository.save();
            return true;
        } catch (Exception e) {
            throw new InvalidInputException(e.getMessage());
        }
    }

    public static Boolean removeMedicine(Medicine med) throws EntityNotFoundException {
        if (med == null) {
            throw new EntityNotFoundException("Medicine", "null");
        }
        medicineRepository.remove(med);
        medicineRepository.save();
        return true;
    }
}
