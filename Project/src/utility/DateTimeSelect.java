package utility;

import control.appointment.AppointmentController;
import control.user.UnavailableDateController;
import entity.appointment.Appointment;
import entity.user.Doctor;
import entity.user.UnavailableDate;
import exception.InvalidInputException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

public class DateTimeSelect {
    public static LocalDateTime selectNewDateAndTime(Doctor doctor) {
        List<LocalDateTime> availableSlots;
    
        while (true) {
            try {
                LocalDate selectedDate;
                while (true) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    System.out.print("Enter the date (dd-MM-yyyy) to view available slots (today or future dates only, enter nothing to go back): ");
                    String dateInput = InputHandler.getInstance().nextLine().trim();
                    
                    if (dateInput.isEmpty()) {
                        return null;
                    }
                    
                    try {
                        selectedDate = LocalDate.parse(dateInput,formatter);
                        if (selectedDate.isBefore(LocalDate.now())) {
                            System.out.println("Invalid date. The new date must be today or in the future.");
                        } else {
                            break;
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
                    }
                }
                
                final LocalDate selectDate = selectedDate;
                
                List<Appointment> doctorAppointments = AppointmentController.getDoctorAppts(doctor);
                List<UnavailableDate> unavailableDates = UnavailableDateController.getUnavailableDates(doctor);
                
                List<LocalDateTime> bookedSlots = doctorAppointments.stream()
                        .filter(appt -> appt.getApptDateTime().toLocalDate().isEqual(selectDate))
                        .map(Appointment::getApptDateTime)
                        .toList();
                
                List<LocalDateTime> blockedSlots = unavailableDates.stream()
                        .filter(unavailable -> unavailable.getDate().toLocalDate().isEqual(selectDate))
                        .map(UnavailableDate::getDate)
                        .toList();
                
                availableSlots = AppointmentController.generateAllSlotsForDay(selectedDate);
                
                List<LocalDate> blockedDates = blockedSlots.stream()
                .map(LocalDateTime::toLocalDate)
                .toList();
            
            availableSlots = availableSlots.stream()
                .filter(slot -> !bookedSlots.contains(slot) &&
                                !blockedDates.contains(slot.toLocalDate()))
                .collect(Collectors.toList());
            
                
                if (!availableSlots.isEmpty()) {
                    break;
                }
                
                System.out.println("No available time slots for the selected date. Please choose another date.");
            } catch (InvalidInputException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    
        System.out.println("Available time slots on the selected date:");
        for (int i = 0; i < availableSlots.size(); i++) {
            System.out.println((i + 1) + ". " + DateFormat.formatWithTime(availableSlots.get(i)));
        }
    
        int slotChoice;
        while (true) {
            System.out.print("Choose an available slot number: ");
            try {
                slotChoice = Integer.parseInt(InputHandler.getInstance().nextLine());
                if (slotChoice < 1 || slotChoice > availableSlots.size()) {
                    System.out.println("Invalid choice. Please choose a valid slot number.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid slot number.");
            }
        }
    
        return availableSlots.get(slotChoice - 1);
    }
    
}
