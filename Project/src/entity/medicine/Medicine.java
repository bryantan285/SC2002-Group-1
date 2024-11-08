package entity.medicine;
import entity.EntityObject;
import java.time.LocalDateTime;
import utility.DateFormat;

// Medicine Class
public class Medicine extends EntityObject {
    private String id;
    private String medicineName;
    private int stockQuantity;
    private float unitCost;
    private LocalDateTime expirationDate;
    private double dosage; // in mg
    private int lowStockThreshold;

    public Medicine() {
        
    }

    // Constructor
    public Medicine(String medicineName, int stockQuantity, float unitCost, LocalDateTime expirationDate, double dosage, int lowStockThreshold) {
        this.medicineName = medicineName;
        this.stockQuantity = stockQuantity;
        this.unitCost = unitCost;
        this.expirationDate = expirationDate;
        this.dosage = dosage;
        this.lowStockThreshold = lowStockThreshold;
    }

    // Method to restock medicine
    public void restock(int quantity) {
        stockQuantity += quantity;
        System.out.println("Medicine restocked. New stock: " + stockQuantity);
    }

    // Method to check if medicine is available
    public void checkAvailability() {
        if (stockQuantity > 0) {
            System.out.println("Medicine is available. Stock: " + stockQuantity);
        } else {
            System.out.println("Medicine is not available.");
        }
    }

    // Method to get the expiration date
    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public double getDosage() {
        return dosage;
    }

    public void setDosage(double dosage) {
        this.dosage = dosage;
    }

    public int getLowStockThreshold() {
        return lowStockThreshold;
    }

    public void setLowStockThreshold(int lowStockThreshold) {
        this.lowStockThreshold = lowStockThreshold;
    }

    // Getter for medicine name for selection purposes
    public String getMedicineName() {
        return medicineName;
    }

    public void incStock(int value) {
        this.stockQuantity += value;
    }

    public boolean decStock(int value) {
        if (this.stockQuantity >= value) {
            this.stockQuantity -= value;
            return true;
        } else {
            return false;
        }
    }

    // // Main method for managing multiple medicines
    // public static void main(String[] args) throws ParseException {
    //     Scanner scanner = new Scanner(System.in);
    //     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    //     // Prompt user for current date
    //     System.out.print("Enter today's date (yyyy-MM-dd): ");
    //     Date currentDate = sdf.parse(scanner.nextLine());

    //     // Create a list to store multiple medicines
    //     List<Medicine> medicines = new ArrayList<>();

    //     int choice;
    //     do {
    //         System.out.println("\nMedicine Management Menu:");
    //         System.out.println("1. Add New Medicine");
    //         System.out.println("2. Select Medicine for Management");
    //         System.out.println("0. Exit");
    //         System.out.print("Enter your choice: ");
    //         choice = scanner.nextInt();
    //         scanner.nextLine(); // Consume newline

    //         switch (choice) {
    //             case 1:
    //                 // Adding new medicine
    //                 System.out.print("Enter medicine name: ");
    //                 String name = scanner.nextLine();
    //                 System.out.print("Enter stock quantity: ");
    //                 int stockQuantity = scanner.nextInt();
    //                 System.out.print("Enter dosage (in mg): ");
    //                 double dosage = scanner.nextDouble();
    //                 System.out.print("Enter low stock threshold: ");
    //                 int lowStockThreshold = scanner.nextInt();
    //                 scanner.nextLine(); // Consume newline

    //                 // Set expiration date to 30 days from today
    //                 Calendar cal = Calendar.getInstance();
    //                 cal.setTime(currentDate);
    //                 cal.add(Calendar.DAY_OF_MONTH, 30);
    //                 Date expirationDate = cal.getTime();

    //                 // Create Medicine object and add it to the list
    //                 Medicine newMedicine = new Medicine(name, stockQuantity, expirationDate, dosage, lowStockThreshold);
    //                 medicines.add(newMedicine);
    //                 System.out.println("New medicine added successfully.");
    //                 break;

    //             case 2:
    //                 // Select medicine to manage
    //                 if (medicines.isEmpty()) {
    //                     System.out.println("No medicines available. Please add a medicine first.");
    //                     break;
    //                 }

    //                 System.out.println("Select a medicine:");
    //                 for (int i = 0; i < medicines.size(); i++) {
    //                     System.out.println((i + 1) + ". " + medicines.get(i).getMedicineName());
    //                 }
    //                 System.out.print("Enter your choice: ");
    //                 int medicineIndex = scanner.nextInt() - 1;
    //                 scanner.nextLine(); // Consume newline

    //                 if (medicineIndex < 0 || medicineIndex >= medicines.size()) {
    //                     System.out.println("Invalid selection. Please try again.");
    //                     break;
    //                 }

    //                 Medicine selectedMedicine = medicines.get(medicineIndex);

    //                 int action;
    //                 do {
    //                     System.out.println("\nManage Medicine: " + selectedMedicine.getMedicineName());
    //                     System.out.println("1. Restock");
    //                     System.out.println("2. Check Availability");
    //                     System.out.println("3. Check Expiration");
    //                     System.out.println("4. Dispense Medicine");
    //                     System.out.println("5. Update Medicine Info");
    //                     System.out.println("6. Check Low Stock");
    //                     System.out.println("0. Back to Main Menu");
    //                     System.out.print("Enter your choice: ");
    //                     action = scanner.nextInt();
    //                     scanner.nextLine(); // Consume newline

    //                     switch (action) {
    //                         case 1:
    //                             System.out.print("Enter quantity to restock: ");
    //                             int restockAmount = scanner.nextInt();
    //                             selectedMedicine.restock(restockAmount);
    //                             break;
    //                         case 2:
    //                             selectedMedicine.checkAvailability();
    //                             break;
    //                         case 3:
    //                             System.out.println("Days until expiration: " + selectedMedicine.daysUntilExpiration(currentDate));
    //                             break;
    //                         case 4:
    //                             System.out.print("Enter quantity to dispense: ");
    //                             int dispenseAmount = scanner.nextInt();
    //                             selectedMedicine.dispense(dispenseAmount);
    //                             break;
    //                         case 5:
    //                             System.out.print("Enter new medicine name: ");
    //                             String newName = scanner.nextLine();
    //                             System.out.print("Enter new dosage: ");
    //                             double newDosage = scanner.nextDouble();
    //                             selectedMedicine.updateMedicineInfo(newName, newDosage);
    //                             break;
    //                         case 6:
    //                             selectedMedicine.lowStock();
    //                             break;
    //                         case 0:
    //                             System.out.println("Returning to main menu...");
    //                             break;
    //                         default:
    //                             System.out.println("Invalid choice. Please try again.");
    //                     }
    //                 } while (action != 0);
    //                 break;

    //             case 0:
    //                 System.out.println("Exiting Medicine Management...");
    //                 break;

    //             default:
    //                 System.out.println("Invalid choice. Please try again.");
    //         }
    //     } while (choice != 0);

    //     scanner.close();
    // }
    
    @Override
    public String toString() {
        return id + ", " + medicineName + ", " + stockQuantity + ", " + unitCost + ", " + DateFormat.format(expirationDate) + ", " + dosage + " mg, " + lowStockThreshold;
    }

    public float getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(float unitCost) {
        this.unitCost = unitCost;
    }
    
}
