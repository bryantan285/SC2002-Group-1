package control.medicine;

import entity.medicine.Medicine;
import java.util.List;
import repository.medicine.MedicineRepository;

public class MedicineController {
    private final MedicineRepository medicineRepository;


    public static void main(String[] args) {
        MedicineController mc = new MedicineController();
        String medicineId = "MED001";
        mc.addMedicine(medicineId, 5);
        System.out.println(mc.checkLowStock(medicineId));
        System.out.println(mc.dispenseMedicine(medicineId, 10));
        System.out.println(mc.checkMedicineAmt(medicineId));
        System.out.println(mc.dispenseMedicine(medicineId, 90));
        System.out.println(mc.checkMedicineAmt(medicineId));
        System.out.println(mc.checkLowStock(medicineId));
    }

    public MedicineController() {
        this.medicineRepository = MedicineRepository.getInstance();
    }

    public int checkMedicineAmt(String medicineId) {
        List<Medicine> medicine = medicineRepository.findByField("id", medicineId);
        if (medicine.isEmpty()) {
            return -1;
        } else {
            Medicine med = medicine.get(0);
            return med.getStockQuantity();
        }
    }

    public void addMedicine(String medicineId, int value) {
        List<Medicine> medicine = medicineRepository.findByField("id", medicineId);
        if (medicine.isEmpty()) {
            
        } else {
            Medicine med = medicine.get(0);
            med.incStock(value);
        }
    }

    public boolean checkLowStock(String medicineId) {
        List<Medicine> medicine = medicineRepository.findByField("id", medicineId);
        if (medicine.isEmpty()) {
            return false;
        } else {
            Medicine med = medicine.get(0);
            return med.getStockQuantity() < med.getLowStockThreshold();
        }
        
    }

    public int dispenseMedicine(String medicineId, int quantity) {
        List<Medicine> medicine = medicineRepository.findByField("id", medicineId);
        if (medicine.isEmpty()) {
            return -1;
        } else {
            Medicine med = medicine.get(0);
            if (med.decStock(quantity)) {
                return quantity;
            } else {
                return 0;
            }
            // System.out.println("Dispensed " + quantity + " units of " + med.getMedicineName() +
            //                    ". Remaining stock: " + med.getStockQuantity());
            // System.out.println("Not enough stock to dispense " + quantity + " units of " + med.getMedicineName());
        }
    }
}
