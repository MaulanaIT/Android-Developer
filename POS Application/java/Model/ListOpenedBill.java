package com.example.project.Model;

public class ListOpenedBill {

    int
            billID;

    String
            billDate,
            billCustomer,
            billOutlet,
            billRefNumber,
            billItem,
            billTotal,
            billTax,
            billPayable;

    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getBillCustomer() {
        return billCustomer;
    }

    public void setBillCustomer(String billCustomer) {
        this.billCustomer = billCustomer;
    }

    public String getBillOutlet() {
        return billOutlet;
    }

    public void setBillOutlet(String billOutlet) {
        this.billOutlet = billOutlet;
    }

    public String getBillRefNumber() {
        return billRefNumber;
    }

    public void setBillRefNumber(String billRefNumber) {
        this.billRefNumber = billRefNumber;
    }

    public String getBillItem() {
        return billItem;
    }

    public void setBillItem(String billItem) {
        this.billItem = billItem;
    }

    public String getBillTotal() {
        return billTotal;
    }

    public void setBillTotal(String billTotal) {
        this.billTotal = billTotal;
    }

    public String getBillTax() {
        return billTax;
    }

    public void setBillTax(String billTax) {
        this.billTax = billTax;
    }

    public String getBillPayable() {
        return billPayable;
    }

    public void setBillPayable(String billPayable) {
        this.billPayable = billPayable;
    }
}