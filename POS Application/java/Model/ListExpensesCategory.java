package com.example.project.Model;

public class ListExpensesCategory {

    String
            expensesCategoryName,
            expensesCategoryStatus;

    int
            expensesCategoryID;

    public int getExpensesCategoryID() {
        return expensesCategoryID;
    }

    public void setExpensesCategoryID(int expensesCategoryID) {
        this.expensesCategoryID = expensesCategoryID;
    }

    public String getExpensesCategoryName() {
        return expensesCategoryName;
    }

    public void setExpensesCategoryName(String expensesCategoryName) {
        this.expensesCategoryName = expensesCategoryName;
    }

    public String getExpensesCategoryStatus() {
        return expensesCategoryStatus;
    }

    public void setExpensesCategoryStatus(String expensesCategoryStatus) {
        this.expensesCategoryStatus = expensesCategoryStatus;
    }
}
