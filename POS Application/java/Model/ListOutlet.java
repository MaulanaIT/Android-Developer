package com.example.project.Model;

public class ListOutlet {

    String
            outletName,
            outletAddress,
            outletContactNumber,
            outletStatus;

    int
            outletID;

    public int getOutletID() {
        return outletID;
    }

    public void setOutletID(int outletID) {
        this.outletID = outletID;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getOutletAddress() {
        return outletAddress;
    }

    public void setOutletAddress(String outletAddress) {
        this.outletAddress = outletAddress;
    }

    public String getOutletContactNumber() {
        return outletContactNumber;
    }

    public void setOutletContactNumber(String outletContactNumber) {
        this.outletContactNumber = outletContactNumber;
    }

    public String getOutletStatus() {
        return outletStatus;
    }

    public void setOutletStatus(String outletStatus) {
        this.outletStatus = outletStatus;
    }
}
