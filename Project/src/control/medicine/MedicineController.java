package control.medicine;

import entity.medicine.Medicine;
import interfaces.control.IController;
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

    public Integer restockMedicine(Medicine med, int quantity) {
        if (med == null) {
            System.out.println("Medicine not found.");
            return null;
        }
        med.restock(quantity);
        save();
        return med.getStockQuantity();
    }

    public Boolean checkLowStock(Medicine med) {
        if (med == null) {
            return null;
        }
        return med.getStockQuantity() < med.getLowStockThreshold();
    }

    public Integer dispenseMedicine(Medicine med, int quantity) {
        if (med == null) {
            System.out.println("Medicine not found.");
            return null;
        }
        try {
            med.decStock(quantity);
            save();
            System.out.println("Dispensed " + quantity + " units of " + med.getMedicineName() + ". Remaining stock: " + med.getStockQuantity());
            return quantity;
        } catch (Exception e) {
            System.out.println("Not enough stock to dispense " + quantity + " units of " + med.getMedicineName());
        }
        return 0;
    }

    public Boolean checkAvailability(Medicine med) {
        if (med == null) {
            return null;
        } else {
            return med.checkAvailability();
        }
    }

    public Boolean updateMedicineDetails(Medicine med, String newName, double newDosage, int newLowStockThreshold) {
        if (med == null) {
            System.out.println("Medicine not found.");
            return null;
        }
        med.setMedicineName(newName);
        med.setDosage(newDosage);
        med.setLowStockThreshold(newLowStockThreshold);
        save();
        return true;
    }

    public Boolean removeMedicine(Medicine med) {
        if (med == null) {
            return null;
        }
        medicineRepository.remove(med);
        save();
        return true;
    }

    public Medicine getMedicineById(String id) {
        return medicineRepository.get(id);
    }

    public List<Medicine> getAllMedicines() {
        return medicineRepository.toList();
    }

    public Boolean setLowStockThreshold(Medicine med, int newThreshold) {
        if (med == null) {
            return null;
        }
        try {
            med.setLowStockThreshold(newThreshold);
            save();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Boolean incMedStock(Medicine med, int amount) {
        if (med == null) {
            return null;
        }
        
        med.incStock(amount);
        save();
        return true;
    }

    public Boolean decMedStock(Medicine med, int amount) {
        if (med == null) {
            return null;
        }
        try {
            med.decStock(amount);
            save();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Boolean updateMedStock(Medicine med, int amount) {
        if (med == null) {
            return null;
        }
        try {
            med.setStockQuantity(amount);
            save();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<Medicine> searchMedicineByName(String name) {
        return medicineRepository.findByField("medicineName", name);
    }
}
