package com.example.project.Model;

import java.util.Date;

public class ListDebit {

    int
            debitID;

    String
            debitDate,
            debitOutlet,
            debitCustomer,
            debitPayable,
            debitUnpaid,
            debitReturn;

    Date
            date;

    public int getDebitID() {
        return debitID;
    }

    public void setDebitID(int debitID) {
        this.debitID = debitID;
    }

    public String getDebitDate() {
        return debitDate;
    }

    public void setDebitDate(String debitDate) {
        this.debitDate = debitDate;
    }

    public String getDebitOutlet() {
        return debitOutlet;
    }

    public void setDebitOutlet(String debitOutlet) {
        this.debitOutlet = debitOutlet;
    }

    public String getDebitCustomer() {
        return debitCustomer;
    }

    public void setDebitCustomer(String debitCustomer) {
        this.debitCustomer = debitCustomer;
    }

    public String getDebitPayable() {
        return debitPayable;
    }

    public void setDebitPayable(String debitPayable) {
        this.debitPayable = debitPayable;
    }

    public String getDebitUnpaid() {
        return debitUnpaid;
    }

    public void setDebitUnpaid(String debitUnpaid) {
        this.debitUnpaid = debitUnpaid;
    }

    public String getDebitReturn() {
        return debitReturn;
    }

    public void setDebitReturn(String debitReturn) {
        this.debitReturn = debitReturn;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
