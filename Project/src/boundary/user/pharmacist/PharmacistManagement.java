// package boundary.user.pharmacist;

// import control.medicine.PrescriptionController;
// import control.medicine.PrescriptionItemController;
// import control.user.PharmacistController;
// import entity.medicine.Medicine;
// import entity.medicine.Prescription;
// import entity.medicine.PrescriptionItem;
// import java.util.List;
// import java.util.Scanner;

// public class PharmacistManagement {
//     private final Scanner scanner;
//     private final PharmacistController pharmacistController;
//     private final PrescriptionController prescriptionController;
//     private final PrescriptionItemController prescriptionItemController;

//     public PharmacistManagement() {
//         this.scanner = new Scanner(System.in);
//         this.pharmacistController = new PharmacistController();
//         this.prescriptionController = new PrescriptionController();
//         this.prescriptionItemController = new PrescriptionItemController();
//     }

//     public void pharmacistMenu(String pharmacistId) {
//         pharmacistController.setCurrentPharmacist(pharmacistId);

//         while (true) {
//             System.out.println("\nPharmacist Menu:");
//             System.out.println("1. View Active Prescriptions");
//             System.out.println("2. View Prescription Items");
//             System.out.println("3. Dispense Prescription Item");
//             System.out.println("4. Create Replenishment Request");
//             System.out.println("5. View All Medicines");
//             System.out.println("6. Logout");

//             int choice = scanner.nextInt();
//             scanner.nextLine(); // Consume newline

//             switch (choice) {
//                 case 1:
//                     viewActivePrescriptions();
//                     break;
//                 case 2:
//                     viewPrescriptionItems();
//                     break;
//                 case 3:
//                     dispensePrescriptionItem();
//                     break;
//                 case 4:
//                     createReplenishmentRequest();
//                     break;
//                 case 5:
//                     viewAllMedicines();
//                     break;
//                 case 6:
//                     System.out.println("Logging out...");
//                     return;
//                 default:
//                     System.out.println("Invalid choice. Please try again.");
//             }
//         }
//     }

//     private void viewActivePrescriptions() {
//         List<Prescription> activePrescriptions = pharmacistController.getActivePrescriptions();

//         if (activePrescriptions.isEmpty()) {
//             System.out.println("No active prescriptions.");
//         } else {
//             for (Prescription prescription : activePrescriptions) {
//                 System.out.printf("Prescription ID: %s, Appointment ID: %s, Status: %s%n",
//                         prescription.getId(),
//                         prescription.getApptId(),
//                         prescription.getIsActive());
//             }
//         }
//     }

//     private void viewPrescriptionItems() {
//         System.out.print("Enter prescription ID to view items: ");
//         String prescriptionId = scanner.nextLine();
//         Prescription prescription = prescriptionController.getPrescriptionById(prescriptionId);

//         if (prescription == null) {
//             System.out.println("Invalid prescription ID.");
//             return;
//         }

//         List<PrescriptionItem> prescriptionItems = pharmacistController.getPrescriptionItems(prescription);
//         if (prescriptionItems.isEmpty()) {
//             System.out.println("No items found for this prescription.");
//         } else {
//             for (PrescriptionItem item : prescriptionItems) {
//                 System.out.printf("Item ID: %s, Medicine: %s, Quantity: %d%n",
//                         item.getId(),
//                         item.getMedicineId(),
//                         item.getQuantity());
//             }
//         }
//     }

//     private void dispensePrescriptionItem() {
//         System.out.print("Enter prescription item ID to dispense: ");
//         String prescriptionItemId = scanner.nextLine();
//         PrescriptionItem item = prescriptionItemController.getPrescriptionItemById(prescriptionItemId);

//         if (item == null) {
//             System.out.println("Invalid prescription item ID.");
//             return;
//         }

//         boolean isDispensed = pharmacistController.dispensePrescriptionItem(item);
//         if (isDispensed) {
//             System.out.println("Item dispensed successfully.");
//         } else {
//             System.out.println("Failed to dispense item.");
//         }
//     }

//     private void createReplenishmentRequest() {
//         System.out.print("Enter medicine ID to replenish: ");
//         String medicineId = scanner.nextLine();
//         System.out.print("Enter quantity to replenish: ");
//         int amount = scanner.nextInt();
//         scanner.nextLine(); // Consume newline

//         String requestId = pharmacistController.createReplenishmentRequest(medicineId, amount);
//         System.out.println("Replenishment request created. Request ID: " + requestId);
//     }

//     private void viewAllMedicines() {
//         List<Medicine> medicines = pharmacistController.getAllMedicine();

//         if (medicines.isEmpty()) {
//             System.out.println("No medicines available.");
//         } else {
//             for (Medicine medicine : medicines) {
//                 System.out.printf("Medicine ID: %s, Name: %s, Stock: %d%n",
//                         medicine.getId(),
//                         medicine.getMedicineName(),
//                         medicine.getStockQuantity());
//             }
//         }
//     }
// }
