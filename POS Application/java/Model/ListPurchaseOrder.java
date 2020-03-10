package com.example.project.Model;

public class ListPurchaseOrder {

    String
            purchaseOrderNumber,
            purchaseOrderOutlet,
            purchaseOrderSupplier,
            purchaseOrderDate,
            purchaseOrderStatus;

    int
            purchaseOrderID,
            purchaseOrderStatusCode;

    public int getPurchaseOrderID() {
        return purchaseOrderID;
    }

    public void setPurchaseOrderID(int purchaseOrderID) {
        this.purchaseOrderID = purchaseOrderID;
    }

    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public void setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    public String getPurchaseOrderOutlet() {
        return purchaseOrderOutlet;
    }

    public void setPurchaseOrderOutlet(String purchaseOrderOutlet) {
        this.purchaseOrderOutlet = purchaseOrderOutlet;
    }

    public String getPurchaseOrderSupplier() {
        return purchaseOrderSupplier;
    }

    public void setPurchaseOrderSupplier(String purchaseOrderSupplier) {
        this.purchaseOrderSupplier = purchaseOrderSupplier;
    }

    public String getPurchaseOrderDate() {
        return purchaseOrderDate;
    }

    public void setPurchaseOrderDate(String purchaseOrderDate) {
        this.purchaseOrderDate = purchaseOrderDate;
    }

    public String getPurchaseOrderStatus() {
        return purchaseOrderStatus;
    }

    public void setPurchaseOrderStatus(String purchaseOrderStatus) {
        this.purchaseOrderStatus = purchaseOrderStatus;
    }

    public int getPurchaseOrderStatusCode() {
        return purchaseOrderStatusCode;
    }

    public void setPurchaseOrderStatusCode(int purchaseOrderStatusCode) {
        this.purchaseOrderStatusCode = purchaseOrderStatusCode;
    }
}
