import java.util.Arrays;
import java.util.Comparator;
public class Plane {

    private PlaneSeat[] seat = new PlaneSeat[12];
    private int numEmptySeat;

    public Plane(){
        this.numEmptySeat = 12;
        for (int i = 0; i < 12; i++){
            seat[i] =  new PlaneSeat(i+1);
        }
    };

    private PlaneSeat[] sortSeats(){
        PlaneSeat[] sorted = Arrays.copyOf(seat, seat.length); // Create a copy to avoid modifying the original array
        Arrays.sort(sorted, new Comparator<PlaneSeat>() {
            public int compare(PlaneSeat s1, PlaneSeat s2) {
                if (!s1.isOccupied() && !s2.isOccupied()) return 0; // Both seats are empty
                if (!s1.isOccupied()) return 1;  // Place empty seats at the end
                if (!s2.isOccupied()) return -1; // Place empty seats at the end
                return Integer.compare(s1.getCustomerID(), s2.getCustomerID()); // Compare by customerID
            }
        });
        return sorted;
    }

    public void showNumEmptySeats(){
        System.out.printf("There are %d empty seats\n",this.numEmptySeat);
    }

    public void showEmptySeats(){
        for (PlaneSeat s : seat) {
            if (!s.isOccupied()) System.out.println("SeatID "+s.getSeatID());
        }
    }

    public void showAssignedSeats(boolean bySeatId){
        if (bySeatId)
            for (PlaneSeat s : seat) {
                if (s.isOccupied()) 
                    System.out.printf("SeatID %d assigned to CustomerID %d\n", s.getSeatID(), s.getCustomerID());
            }
        else {
            PlaneSeat[] sortedSeats = sortSeats();
            for (PlaneSeat s : sortedSeats) {
                if (s.isOccupied()) 
                System.out.printf("SeatID %d assigned to CustomerID %d\n", s.getSeatID(), s.getCustomerID());
            }
        }
    }

    public void assignSeat(int seatId, int cust_id){
        if (numEmptySeat>0 && !seat[seatId-1].isOccupied()){
            seat[seatId-1].assign(cust_id);
            numEmptySeat--;
            System.out.println("Seat Assigned!");
        }
        else {
            System.out.println("Seat already assigned to a customer.");
        }
    }
    
    public void unAssignSeat(int seatID) {
        seat[seatID-1].unAssign();
        numEmptySeat++;
        System.out.println("Seat Unassigned!");
    }
}
