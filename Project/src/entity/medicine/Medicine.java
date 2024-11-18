package entity.medicine;
import entity.EntityObject;

// Medicine Class
public class Medicine extends EntityObject {
    private String id;
    private String medicineName;
    private int stockQuantity;
    private float unitCost;
    private double dosage; // in mg
    private int lowStockThreshold;

    public Medicine() {
        
    }

    public Medicine(String medicineName, int stockQuantity, float unitCost, double dosage, int lowStockThreshold) {
        this.medicineName = medicineName;
        this.stockQuantity = stockQuantity;
        this.unitCost = unitCost;
        this.dosage = dosage;
        this.lowStockThreshold = lowStockThreshold;
    }

    public void restock(int quantity) {
        stockQuantity += quantity;
    }

    public boolean checkAvailability() {
        return stockQuantity > 0;
    }
    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("New quantity cannot be negative.");
        }
        this.stockQuantity = stockQuantity;
    }

    public double getDosage() {
        return dosage;
    }

    public void setDosage(double dosage) {
        this.dosage = dosage;
    }

    public int getLowStockThreshold() {
        return lowStockThreshold;
    }

    public void setLowStockThreshold(int newLevel) {
        if (newLevel < 0) {
            throw new IllegalArgumentException("Low stock threshold cannot be negative.");
        }
        this.lowStockThreshold = newLevel;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void incStock(int value) {
        this.stockQuantity += value;
    }

    public void decStock(int value) {
        if (stockQuantity - value < 0) {
            throw new IllegalArgumentException("New quantity cannot be negative.");
        }
        this.stockQuantity -= value;
    }

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
    
    
    

    public float getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(float unitCost) {
        this.unitCost = unitCost;
    }
    
}
