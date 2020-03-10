package com.example.project.Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ListExpenses {

    String
            expensesNumber,
            expensesCategory,
            expensesOutlet,
            expensesAmount,
            expensesDate;

    int
            expensesID;

    private SimpleDateFormat
            dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public int getExpensesID() {
        return expensesID;
    }

    public void setExpensesID(int expensesID) {
        this.expensesID = expensesID;
    }

    public String getExpensesNumber() {
        return expensesNumber;
    }

    public void setExpensesNumber(String expensesNumber) {
        this.expensesNumber = expensesNumber;
    }

    public String getExpensesCategory() {
        return expensesCategory;
    }

    public void setExpensesCategory(String expensesCategory) {
        this.expensesCategory = expensesCategory;
    }

    public String getExpensesOutlet() {
        return expensesOutlet;
    }

    public void setExpensesOutlet(String expensesOutlet) {
        this.expensesOutlet = expensesOutlet;
    }

    public String getExpensesDate() {
        return expensesDate;
    }

    public void setExpensesDate(Date expensesDate) {
        this.expensesDate = dateFormat.format(expensesDate);
    }

    public String getExpensesAmount() {
        return expensesAmount;
    }

    public void setExpensesAmount(String expensesAmount) {
        this.expensesAmount = expensesAmount;
    }
}
