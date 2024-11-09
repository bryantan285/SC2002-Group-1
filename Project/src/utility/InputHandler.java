package utility;

import java.util.Scanner;

public class InputHandler {
    private static Scanner scannerInstance;

    private InputHandler() {
        
    }

    public static Scanner getInstance() {
        if (scannerInstance == null) {
            scannerInstance = new Scanner(System.in);
        }
        return scannerInstance;
    }
}
