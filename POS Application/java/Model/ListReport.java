package com.example.project.Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ListReport {

    int
            reportID;

    String
            reportOutlet,
            reportPaymentMethod,
            reportTotal,
            reportTax,
            reportPayable,
            reportDate,
            retportItem,
            reportCustomer;

    private SimpleDateFormat
            dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public String getReportOutlet() {
        return reportOutlet;
    }

    public void setReportOutlet(String reportOutlet) {
        this.reportOutlet = reportOutlet;
    }

    public String getReportPaymentMethod() {
        return reportPaymentMethod;
    }

    public void setReportPaymentMethod(String reportPaymentMethod) {
        this.reportPaymentMethod = reportPaymentMethod;
    }

    public String getReportTotal() {
        return reportTotal;
    }

    public void setReportTotal(String reportTotal) {
        this.reportTotal = reportTotal;
    }

    public String getReportTax() {
        return reportTax;
    }

    public void setReportTax(String reportTax) {
        this.reportTax = reportTax;
    }

    public String getReportPayable() {
        return reportPayable;
    }

    public void setReportPayable(String reportPayable) {
        this.reportPayable = reportPayable;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = dateFormat.format(reportDate);
    }

    public String getRetportItem() {
        return retportItem;
    }

    public void setRetportItem(String retportItem) {
        this.retportItem = retportItem;
    }

    public String getReportCustomer() {
        return reportCustomer;
    }

    public void setReportCustomer(String reportCustomer) {
        this.reportCustomer = reportCustomer;
    }
}
