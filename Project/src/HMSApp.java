import java.util.*;

//import User.Role;

public class Main {
    private static ArrayList<User> users = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        
        users.add(new Doctor(false, "D001", "John Smith", true, 45));
        users.add(new Doctor(false,"D002", "Emily Clarke", false, 38));
        users.add(new Pharmacist(User.Role.PHARMACIST,"P001", "Mark Lee", true, 29));
        users.add(new Administrator(User.Role.ADMINISTRATOR,"A001", "Sarah Lee", false, 40));
        users.add(new Patient(User.Role.PATIENT, "P1001", "Alice Brown", false, 40));
        users.add(new Patient(User.Role.PATIENT, "P1001", "Bob Stone", false, 40));
        users.add(new Patient(User.Role.PATIENT, "P1001", "Charlie White", false, 40));

        int choice = 1;
        User user;
        System.out.println("██╗  ██╗███╗   ███╗███████╗");
        System.out.println("██║  ██║████╗ ████║██╔════╝");
        System.out.println("███████║██╔████╔██║███████╗");
        System.out.println("██╔══██║██║╚██╔╝██║╚════██║");
        System.out.println("██║  ██║██║ ╚═╝ ██║███████║");
        System.out.println("╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝");
        System.out.println("===========================");
        System.out.println("1: login");
        System.out.println("2: exit");
        System.out.println("===========================");

        while (choice < 2){
            switch (choice){
                case 1:
                    String username, password;

                    System.out.print("Enter your username: ");
                    username = scanner.nextLine();

                    System.out.print("Enter your password: ");
                    password = scanner.nextLine();

                    user.login(username, password);
            }
        }
    }

}
