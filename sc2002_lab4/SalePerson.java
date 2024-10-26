public class SalePerson implements Comparable{
    private String firstName;
    private String lastName;
    private int totalSales;

    public SalePerson(String fname, String lname, int tsales){
        this.firstName = fname;
        this.lastName = lname;
        this.totalSales = tsales;
    }

    public String toString(){
        return this.getLastName()+", "+getFirstName()+": "+getTotalSales();
    }

    public boolean equals(Object o) {
        SalePerson guy = (SalePerson) o;  // Typecast to SalePerson

        // Compare the first and last names
        return firstName == guy.firstName && lastName == guy.lastName;
    }

    public int compareTo(Object o) {
        SalePerson guy = (SalePerson) o;
        if (this.totalSales != guy.totalSales) {
            return guy.totalSales - this.totalSales;
        }
        // Break ties by last name in ascending alphabetical order
        return this.lastName.compareTo(guy.lastName);
    }
    public String getFirstName(){
        return this.firstName;
    }
    public String getLastName(){
        return this.lastName;
    }
    public int getTotalSales(){
        return this.totalSales;
    }
}
