import java.util.Date;
import java.util.Calendar;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

// Medicine Class
class Medicine {
    private String medicineName;
    private int stockQuantity;
    private Date expirationDate;
    private double dosage; // in mg
    private int lowStockThreshold;

    // Constructor
    public Medicine(String medicineName, int stockQuantity, Date expirationDate, double dosage, int lowStockThreshold) {
        this.medicineName = medicineName;
        this.stockQuantity = stockQuantity;
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
    public Date getExpirationDate() {
        return expirationDate;
    }

    // Method to update medicine information
    public void updateMedicineInfo(String newName, double newDosage) {
        this.medicineName = newName;
        this.dosage = newDosage;
    }

    // Method to calculate days until expiration
    public long daysUntilExpiration(Date currentDate) {
        long diff = expirationDate.getTime() - currentDate.getTime();
        return diff / (1000 * 60 * 60 * 24);
    }

    // Method to check if dosage is valid
    public boolean isValidDosage(double dose) {
        return dose <= dosage;
    }

    // Method to check if stock is low
    public void lowStock() {
        if (stockQuantity < lowStockThreshold) {
            int diff = lowStockThreshold - stockQuantity;
            System.out.println("Stock is low and quantity needs to be updated by " + diff + " to meet the minimum requirement");
        } else {
            System.out.println("Stock is not low");
        }
    }

    // Method to dispense medicine
    public void dispense(int quantity) {
        if (stockQuantity >= quantity) {
            stockQuantity -= quantity;
            System.out.println("Medicine dispensed. Remaining stock: " + stockQuantity);
        } else {
            System.out.println("Not enough stock to dispense.");
        }
    }

    // Getter for medicine name for selection purposes
    public String getMedicineName() {
        return medicineName;
    }

    // Main method for managing multiple medicines
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Prompt user for current date
        System.out.print("Enter today's date (yyyy-MM-dd): ");
        Date currentDate = sdf.parse(scanner.nextLine());

        // Create a list to store multiple medicines
        List<Medicine> medicines = new ArrayList<>();

        int choice;
        do {
            System.out.println("\nMedicine Management Menu:");
            System.out.println("1. Add New Medicine");
            System.out.println("2. Select Medicine for Management");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Adding new medicine
                    System.out.print("Enter medicine name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter stock quantity: ");
                    int stockQuantity = scanner.nextInt();
                    System.out.print("Enter dosage (in mg): ");
                    double dosage = scanner.nextDouble();
                    System.out.print("Enter low stock threshold: ");
                    int lowStockThreshold = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    // Set expiration date to 30 days from today
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(currentDate);
                    cal.add(Calendar.DAY_OF_MONTH, 30);
                    Date expirationDate = cal.getTime();

                    // Create Medicine object and add it to the list
                    Medicine newMedicine = new Medicine(name, stockQuantity, expirationDate, dosage, lowStockThreshold);
                    medicines.add(newMedicine);
                    System.out.println("New medicine added successfully.");
                    break;

                case 2:
                    // Select medicine to manage
                    if (medicines.isEmpty()) {
                        System.out.println("No medicines available. Please add a medicine first.");
                        break;
                    }

                    System.out.println("Select a medicine:");
                    for (int i = 0; i < medicines.size(); i++) {
                        System.out.println((i + 1) + ". " + medicines.get(i).getMedicineName());
                    }
                    System.out.print("Enter your choice: ");
                    int medicineIndex = scanner.nextInt() - 1;
                    scanner.nextLine(); // Consume newline

                    if (medicineIndex < 0 || medicineIndex >= medicines.size()) {
                        System.out.println("Invalid selection. Please try again.");
                        break;
                    }

                    Medicine selectedMedicine = medicines.get(medicineIndex);

                    int action;
                    do {
                        System.out.println("\nManage Medicine: " + selectedMedicine.getMedicineName());
                        System.out.println("1. Restock");
                        System.out.println("2. Check Availability");
                        System.out.println("3. Check Expiration");
                        System.out.println("4. Dispense Medicine");
                        System.out.println("5. Update Medicine Info");
                        System.out.println("6. Check Low Stock");
                        System.out.println("0. Back to Main Menu");
                        System.out.print("Enter your choice: ");
                        action = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        switch (action) {
                            case 1:
                                System.out.print("Enter quantity to restock: ");
                                int restockAmount = scanner.nextInt();
                                selectedMedicine.restock(restockAmount);
                                break;
                            case 2:
                                selectedMedicine.checkAvailability();
                                break;
                            case 3:
                                System.out.println("Days until expiration: " + selectedMedicine.daysUntilExpiration(currentDate));
                                break;
                            case 4:
                                System.out.print("Enter quantity to dispense: ");
                                int dispenseAmount = scanner.nextInt();
                                selectedMedicine.dispense(dispenseAmount);
                                break;
                            case 5:
                                System.out.print("Enter new medicine name: ");
                                String newName = scanner.nextLine();
                                System.out.print("Enter new dosage: ");
                                double newDosage = scanner.nextDouble();
                                selectedMedicine.updateMedicineInfo(newName, newDosage);
                                break;
                            case 6:
                                selectedMedicine.lowStock();
                                break;
                            case 0:
                                System.out.println("Returning to main menu...");
                                break;
                            default:
                                System.out.println("Invalid choice. Please try again.");
                        }
                    } while (action != 0);
                    break;

                case 0:
                    System.out.println("Exiting Medicine Management...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }
}
