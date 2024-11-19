package entity.medicine;

import entity.EntityObject;

/**
 * Represents a medicine item in the system.
 * The Medicine class stores details such as the name, stock quantity,
 * unit cost, dosage, and low stock threshold for a specific medicine.
 */
public class Medicine extends EntityObject {

    private String id;
    private String medicineName;
    private int stockQuantity;
    private float unitCost;
    private double dosage; // in mg
    private int lowStockThreshold;

    /**
     * Default constructor for the Medicine class. Initializes an empty Medicine object.
     */
    public Medicine() {
        
    }

    /**
     * Constructor to initialize a Medicine object with specific details.
     * 
     * @param medicineName The name of the medicine.
     * @param stockQuantity The available stock quantity of the medicine.
     * @param unitCost The unit cost of the medicine.
     * @param dosage The dosage of the medicine in milligrams (mg).
     * @param lowStockThreshold The stock level at which the medicine is considered to have low stock.
     */
    public Medicine(String medicineName, int stockQuantity, float unitCost, double dosage, int lowStockThreshold) {
        this.medicineName = medicineName;
        this.stockQuantity = stockQuantity;
        this.unitCost = unitCost;
        this.dosage = dosage;
        this.lowStockThreshold = lowStockThreshold;
    }

    /**
     * Restocks the medicine by the specified quantity.
     * 
     * @param quantity The quantity to restock.
     */
    public void restock(int quantity) {
        stockQuantity += quantity;
    }

    /**
     * Checks if the medicine is available in stock (i.e., if the stock quantity is greater than zero).
     * 
     * @return true if the medicine is available in stock, false otherwise.
     */
    public boolean checkAvailability() {
        return stockQuantity > 0;
    }

    /**
     * Retrieves the unique ID of the medicine.
     * 
     * @return The unique identifier of the medicine.
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Sets the unique ID for the medicine.
     * 
     * @param id The new unique identifier for the medicine.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets the name of the medicine.
     * 
     * @param medicineName The new name for the medicine.
     */
    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    /**
     * Retrieves the stock quantity of the medicine.
     * 
     * @return The available stock quantity of the medicine.
     */
    public int getStockQuantity() {
        return stockQuantity;
    }

    /**
     * Sets the stock quantity of the medicine.
     * Ensures the stock quantity is not negative.
     * 
     * @param stockQuantity The new stock quantity for the medicine.
     * @throws IllegalArgumentException if the new stock quantity is negative.
     */
    public void setStockQuantity(int stockQuantity) {
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("New quantity cannot be negative.");
        }
        this.stockQuantity = stockQuantity;
    }

    /**
     * Retrieves the dosage of the medicine in milligrams (mg).
     * 
     * @return The dosage of the medicine in mg.
     */
    public double getDosage() {
        return dosage;
    }

    /**
     * Sets the dosage of the medicine in milligrams (mg).
     * 
     * @param dosage The new dosage for the medicine in mg.
     */
    public void setDosage(double dosage) {
        this.dosage = dosage;
    }

    /**
     * Retrieves the low stock threshold for the medicine.
     * 
     * @return The stock level at which the medicine is considered to have low stock.
     */
    public int getLowStockThreshold() {
        return lowStockThreshold;
    }

    /**
     * Sets the low stock threshold for the medicine.
     * Ensures the threshold is not negative.
     * 
     * @param newLevel The new low stock threshold for the medicine.
     * @throws IllegalArgumentException if the new threshold is negative.
     */
    public void setLowStockThreshold(int newLevel) {
        if (newLevel < 0) {
            throw new IllegalArgumentException("Low stock threshold cannot be negative.");
        }
        this.lowStockThreshold = newLevel;
    }

    /**
     * Retrieves the name of the medicine.
     * 
     * @return The name of the medicine.
     */
    public String getMedicineName() {
        return medicineName;
    }

    /**
     * Increases the stock quantity of the medicine by the specified value.
     * 
     * @param value The value by which to increase the stock quantity.
     */
    public void incStock(int value) {
        this.stockQuantity += value;
    }

    /**
     * Decreases the stock quantity of the medicine by the specified value.
     * Ensures the stock quantity does not become negative.
     * 
     * @param value The value by which to decrease the stock quantity.
     * @throws IllegalArgumentException if the new stock quantity would be negative.
     */
    public void decStock(int value) {
        if (stockQuantity - value < 0) {
            throw new IllegalArgumentException("New quantity cannot be negative.");
        }
        this.stockQuantity -= value;
    }

    /**
     * Returns a string representation of the medicine, including its ID, name, stock quantity,
     * unit cost, dosage, and low stock threshold. If the stock is below the low stock threshold,
     * a warning is included in the string.
     * 
     * @return A string representation of the medicine.
     */
    @Override
    public String toString() {
        String str = "ID: " + id + "\n" +
                    "Name: " + medicineName + "\n" +
                    "Stock Quantity: " + stockQuantity + "\n" +
                    "Unit Cost: $" + unitCost + "\n" +
                    "Dosage: " + dosage + " mg\n" +
                    "Low Stock Threshold: " + lowStockThreshold;
        if (lowStockThreshold > stockQuantity) {
            str = str + "\nWARNING: LOW STOCK!";
        }
        return str;
    }

    /**
     * Retrieves the unit cost of the medicine.
     * 
     * @return The unit cost of the medicine.
     */
    public float getUnitCost() {
        return unitCost;
    }

    /**
     * Sets the unit cost of the medicine.
     * 
     * @param unitCost The new unit cost for the medicine.
     */
    public void setUnitCost(float unitCost) {
        this.unitCost = unitCost;
    }
}
