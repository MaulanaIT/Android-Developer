package com.example.project.Model;

import android.widget.TextView;

public class ListSales {

    String
            salesDate,
            saledCustomer,
            salesOutlet,
            salesMethod,
            salesItem,
            salesTotal,
            salesTax,
            salesPayable;

    int
            salesID;

    public String getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(String salesDate) {
        this.salesDate = salesDate;
    }

    public String getSaledCustomer() {
        return saledCustomer;
    }

    public void setSaledCustomer(String saledCustomer) {
        this.saledCustomer = saledCustomer;
    }

    public String getSalesOutlet() {
        return salesOutlet;
    }

    public void setSalesOutlet(String salesOutlet) {
        this.salesOutlet = salesOutlet;
    }

    public String getSalesMethod() {
        return salesMethod;
    }

    public void setSalesMethod(String salesMethod) {
        this.salesMethod = salesMethod;
    }

    public String getSalesItem() {
        return salesItem;
    }

    public void setSalesItem(String salesItem) {
        this.salesItem = salesItem;
    }

    public String getSalesTotal() {
        return salesTotal;
    }

    public void setSalesTotal(String salesTotal) {
        this.salesTotal = salesTotal;
    }

    public String getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(String salesTax) {
        this.salesTax = salesTax;
    }

    public String getSalesPayable() {
        return salesPayable;
    }

    public void setSalesPayable(String salesPayable) {
        this.salesPayable = salesPayable;
    }

    public int getSalesID() {
        return salesID;
    }

    public void setSalesID(int salesID) {
        this.salesID = salesID;
    }
}
