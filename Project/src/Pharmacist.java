class Prescription {
    private int patientID;
    private String medicineName;
    private int totalDoses;
    private int dosesTaken;
    private int dosesPerDay;
    private boolean isActive;

    // Constructor
    public Prescription(int patientID, String medicineName, int totalDoses, int dosesPerDay, boolean isActive) {
        this.patientID = patientID;
        this.medicineName = medicineName;
        this.totalDoses = totalDoses;
        this.dosesTaken = 0; // Initialized to 0 at start
        this.dosesPerDay = dosesPerDay;
        this.isActive = isActive;
    }

    // Method to renew prescription
    public void renewPrescription(int additionalDoses) {
        if (isActive && totalDoses <= 100) {
            totalDoses += additionalDoses;
            System.out.println("Prescription renewed successfully. Total doses: " + totalDoses);
        } else {
            System.out.println("Prescription can't be renewed as it is either inactive or has reached the max number of doses.");
        }
    }

    // Method to display prescription details
    public void displayPrescriptionDetails() {
        System.out.println("Patient ID: " + this.patientID);
        System.out.println("Medicine Name: " + this.medicineName);
        System.out.println("Total Doses: " + this.totalDoses);
        System.out.println("Doses Taken: " + this.dosesTaken);
        System.out.println("Doses per day: " + this.dosesPerDay);
        System.out.println("Active: " + this.isActive);
    }

    // Method to check if the prescription is active
    public void checkActive() {
        if (isActive) {
            System.out.println("Prescription is active.");
        } else {
            System.out.println("Prescription is inactive.");
        }
    }

    // Method to cancel prescription
    public void cancelPrescription() {
        if (isActive) {
            isActive = false;
            System.out.println("Prescription is cancelled.");
        } else {
            System.out.println("Prescription is already inactive.");
        }
    }

    // Method to track doses taken
    public void trackDoses() {
        if (dosesTaken < totalDoses) {
            dosesTaken++;
            System.out.println("Dose taken. Total doses taken: " + dosesTaken);
        } else {
            System.out.println("All doses have been taken.");
        }
    }

    // Method to calculate remaining doses
    public void remainingDoses(Scanner scanner) {
        System.out.print("Enter the total number of doses you have taken since beginning treatment: ");
        dosesTaken = scanner.nextInt();

        if (dosesTaken < totalDoses) {
            int remainingDoses = totalDoses - dosesTaken;
            System.out.println("Remaining doses to be taken: " + remainingDoses);
        } else {
            System.out.println("All doses have been taken.");
        }
    }

    // Method to calculate adherence rate
    public void adherenceRate(Scanner scanner) {
        System.out.print("Enter the total number of doses you have taken since beginning treatment: ");
        dosesTaken = scanner.nextInt();

        double adhereRate = ((double) dosesTaken / totalDoses) * 100;
        System.out.println("Adherence rate: " + adhereRate + "%");
    }

    // Main method to manage multiple prescriptions
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Prescription> prescriptions = new ArrayList<>();

        int choice;
        do {
            System.out.println("\nPrescription Management System:");
            System.out.println("1. Add New Prescription");
            System.out.println("2. Select Prescription to Manage");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Add a new prescription
                    System.out.print("Enter patient ID: ");
                    int patientID = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter medicine name: ");
                    String medicineName = scanner.nextLine();
                    System.out.print("Enter total doses prescribed: ");
                    int totalDoses = scanner.nextInt();
                    System.out.print("Enter doses per day: ");
                    int dosesPerDay = scanner.nextInt();
                    System.out.print("Is the prescription active? (true/false): ");
                    boolean isActive = scanner.nextBoolean();

                    // Create a new Prescription object and add it to the list
                    Prescription newPrescription = new Prescription(patientID, medicineName, totalDoses, dosesPerDay, isActive);
                    prescriptions.add(newPrescription);
                    System.out.println("New prescription added successfully.");
                    break;

                case 2:
                    // Select a prescription to manage
                    if (prescriptions.isEmpty()) {
                        System.out.println("No prescriptions available. Please add a prescription first.");
                        break;
                    }

                    System.out.println("Select a prescription:");
                    for (int i = 0; i < prescriptions.size(); i++) {
                        System.out.println((i + 1) + ". Prescription for Patient ID: " + prescriptions.get(i).patientID);
                    }
                    System.out.print("Enter your choice: ");
                    int prescriptionIndex = scanner.nextInt() - 1;
                    scanner.nextLine(); // Consume newline

                    if (prescriptionIndex < 0 || prescriptionIndex >= prescriptions.size()) {
                        System.out.println("Invalid selection. Please try again.");
                        break;
                    }

                    Prescription selectedPrescription = prescriptions.get(prescriptionIndex);

                    int action;
                    do {
                        System.out.println("\nManage Prescription for Patient ID: " + selectedPrescription.patientID);
                        System.out.println("1. Display Prescription Details");
                        System.out.println("2. Renew Prescription");
                        System.out.println("3. Track Dose");
                        System.out.println("4. Check Remaining Doses");
                        System.out.println("5. Check Adherence Rate");
                        System.out.println("6. Check if Active");
                        System.out.println("7. Cancel Prescription");
                        System.out.println("0. Back to Main Menu");
                        System.out.print("Enter your choice: ");
                        action = scanner.nextInt();

                        switch (action) {
                            case 1:
                                selectedPrescription.displayPrescriptionDetails();
                                break;
                            case 2:
                                System.out.print("Enter additional doses to renew prescription: ");
                                int additionalDoses = scanner.nextInt();
                                selectedPrescription.renewPrescription(additionalDoses);
                                break;
                            case 3:
                                selectedPrescription.trackDoses();
                                break;
                            case 4:
                                selectedPrescription.remainingDoses(scanner);
                                break;
                            case 5:
                                selectedPrescription.adherenceRate(scanner);
                                break;
                            case 6:
                                selectedPrescription.checkActive();
                                break;
                            case 7:
                                selectedPrescription.cancelPrescription();
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
                    System.out.println("Exiting Prescription Management System.");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }
}
