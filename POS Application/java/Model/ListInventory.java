package com.example.project.Model;

public class ListInventory {

    String
            inventoryProductCode,
            inventoryName,
            inventoryQuantity;

    int
            inventoryID;

    public int getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(int inventoryID) {
        this.inventoryID = inventoryID;
    }

    public String getInventoryProductCode() {
        return inventoryProductCode;
    }

    public void setInventoryProductCode(String inventoryProductCode) {
        this.inventoryProductCode = inventoryProductCode;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
    }

    public String getInventoryQuantity() {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(String inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }
}
