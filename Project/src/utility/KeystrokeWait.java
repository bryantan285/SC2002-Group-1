package utility;

import java.util.Scanner;
/**
 * Utility class for handling user input by waiting for a keystroke.
 * This class provides functionality to pause program execution and 
 * wait for the user to press the Enter key before continuing.
 */
public class KeystrokeWait {
    public static void waitForKeyPress() {
        Scanner scanner = InputHandler.getInstance();
        System.out.println("Press enter to continue...");
        scanner.nextLine();
    }
}
