package interfaces.boundary;

import java.util.Scanner;

import utility.InputHandler;

public interface IKeystrokeWait {
    public static void waitForKeyPress() {
        Scanner scanner = InputHandler.getInstance();
        System.out.println("Press enter to continue...");
        scanner.nextLine();
    }
}
