import java.util.Scanner;

public class PlaneApp {
    public static void main(String[] args) {
        Plane p = new Plane();
        Scanner sc = new Scanner(System.in);
        int choice;
        int seatID, customerID;

        System.out.println("(1) Show number of empty seats");
        System.out.println("(2) Show the list of empty seats");
        System.out.println("(3) Show the list of seat assignments by seat ID");
        System.out.println("(4) Show the list of seat assignments by customer ID");
        System.out.println("(5) Assign a customer to a seat");
        System.out.println("(6) Remove a seat assignment");
        System.out.println("(7) Exit");

        System.out.print("Enter the number of your choice: ");
        choice = sc.nextInt();
        while (choice < 7){
            switch (choice){
                case 1:
                    p.showNumEmptySeats();
                    break;
                case 2:
                    p.showEmptySeats();
                    break;
                case 3:
                    p.showAssignedSeats(true);
                    break;
                case 4:
                    p.showAssignedSeats(false);
                    break;
                case 5:
                    System.out.println("Assigning Seat ..");
                    System.out.print("Please enter SeatID: ");
                    seatID = sc.nextInt();
                    System.out.print("Please enter Customer ID: ");
                    customerID = sc.nextInt();
                    p.assignSeat(seatID, customerID);
                    break;
                case 6:
                    System.out.print("Enter SeatID to unassign customer from: ");
                    seatID = sc.nextInt();
                    p.unAssignSeat(seatID);
            }
            System.out.println();
            System.out.print("Enter the number of your choice: ");
            choice = sc.nextInt();
        }
    }
}
