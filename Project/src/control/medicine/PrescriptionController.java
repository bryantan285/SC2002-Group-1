package control.medicine;

import entity.medicine.Prescription;
import java.util.Scanner;
import repository.medicine.MedicineRepository;
import repository.medicine.PrescriptionRepository;

public class PrescriptionController {
    private final PrescriptionRepository prescriptionRepository;
    private final MedicineRepository medicineRepository;
    public static void main(String[] args) {
        
    }

    public PrescriptionController() {
        this.prescriptionRepository = PrescriptionRepository.getInstance();
        this.medicineRepository = MedicineRepository.getInstance();
    }

    // Method to renew prescription
    public void renewPrescription(Prescription prescription, int additionalDoses) {
        boolean isActive = prescription.getIsActive();
        int totalDoses = prescription.getTotalDoses();
        if (isActive && totalDoses <= 100) {
            totalDoses += additionalDoses;
            System.out.println("Prescription renewed successfully. Total doses: " + totalDoses);
        } else {
            System.out.println("Prescription can't be renewed as it is either inactive or has reached the max number of doses.");
        }
    }

    // Method to display prescription details
    public void displayPrescriptionDetails(Prescription prescription) {
        System.out.println("Patient ID: " + prescription.getId());
        System.out.println("Medicine Name: " + medicineRepository.get(prescription.getMedicineId()).getMedicineName());
        System.out.println("Total Doses: " + prescription.getTotalDoses());
        System.out.println("Doses Taken: " + prescription.getDosesTaken());
        System.out.println("Doses per day: " + prescription.getDosesPerDay());
        System.out.println("Active: " + prescription.getIsActive());
    }
    // Method to check if the prescription is active
    public void checkActive(Prescription prescription) {
        if (prescription.getIsActive()) {
            System.out.println("Prescription is active.");
        } else {
            System.out.println("Prescription is inactive.");
        }
    }

    // Method to cancel prescription
    public void cancelPrescription(Prescription prescription) {
        boolean isActive = prescription.getIsActive();
        if (isActive) {
            prescription.setIsActive(false);
            System.out.println("Prescription is cancelled.");
        } else {
            System.out.println("Prescription is already inactive.");
        }
    }

    // Method to track doses taken
    public void trackDoses(Prescription prescription) {
        int dosesTaken = prescription.getDosesTaken();
        int totalDoses = prescription.getTotalDoses();
        if (dosesTaken < totalDoses) {
            dosesTaken++;
            System.out.println("Dose taken. Total doses taken: " + dosesTaken);
        } else {
            System.out.println("All doses have been taken.");
        }
    }

    // Method to calculate remaining doses
    public void remainingDoses(Prescription prescription,Scanner scanner) {
        System.out.print("Enter the total number of doses you have taken since beginning treatment: ");
        int dosesTaken = scanner.nextInt();
        int totalDoses = prescription.getTotalDoses();
        if (dosesTaken < totalDoses) {
            int remainingDoses = totalDoses - dosesTaken;
            System.out.println("Remaining doses to be taken: " + remainingDoses);
        } else {
            System.out.println("All doses have been taken.");
        }
    }

    // Method to calculate adherence rate
    public void adherenceRate(Prescription prescription, Scanner scanner) {
        System.out.print("Enter the total number of doses you have taken since beginning treatment: ");
        int dosesTaken = scanner.nextInt();

        double adhereRate = ((double) dosesTaken / prescription.getTotalDoses()) * 100;
        System.out.println("Adherence rate: " + adhereRate + "%");
    }
}
