package com.example.project.Model;

public class ListGiftCard {

    String
            giftCardNumber,
            giftCardValue,
            giftCardExpiryDate,
            giftCardStatus;

    int
            giftCardID;

    public int getGiftCardID() {
        return giftCardID;
    }

    public void setGiftCardID(int giftCardID) {
        this.giftCardID = giftCardID;
    }

    public String getGiftCardNumber() {
        return giftCardNumber;
    }

    public void setGiftCardNumber(String giftCardNumber) {
        this.giftCardNumber = giftCardNumber;
    }

    public String getGiftCardValue() {
        return giftCardValue;
    }

    public void setGiftCardValue(String giftCardValue) {
        this.giftCardValue = giftCardValue;
    }

    public String getGiftCardExpiryDate() {
        return giftCardExpiryDate;
    }

    public void setGiftCardExpiryDate(String giftCardExpiryDate) {
        this.giftCardExpiryDate = giftCardExpiryDate;
    }

    public String getGiftCardStatus() {
        return giftCardStatus;
    }

    public void setGiftCardStatus(String giftCardStatus) {
        this.giftCardStatus = giftCardStatus;
    }
}
