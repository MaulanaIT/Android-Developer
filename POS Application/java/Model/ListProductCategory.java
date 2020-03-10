package com.example.project.Model;

public class ListProductCategory {

    String
            productCategoryName,
            productCategoryStatus;

    int
            productCategoryID;

    public int getProductCategoryID() {
        return productCategoryID;
    }

    public void setProductCategoryID(int productCategoryID) {
        this.productCategoryID = productCategoryID;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public String getProductCategoryStatus() {
        return productCategoryStatus;
    }

    public void setProductCategoryStatus(String productCategoryStatus) {
        this.productCategoryStatus = productCategoryStatus;
    }
}
