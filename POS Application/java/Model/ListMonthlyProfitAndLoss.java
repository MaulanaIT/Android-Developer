package com.example.project.Model;

public class ListMonthlyProfitAndLoss {

    int
            statisticID;

    String
            statisticDay,
            statisticProfit,
            statisticOutlet;

    public int getStatisticID() {
        return statisticID;
    }

    public void setStatisticID(int statisticID) {
        this.statisticID = statisticID;
    }

    public String getStatisticOutlet() {
        return statisticOutlet;
    }

    public void setStatisticOutlet(String statisticOutlet) {
        this.statisticOutlet = statisticOutlet;
    }

    public String getStatisticDay() {
        return statisticDay;
    }

    public void setStatisticDay(String statisticDay) {
        this.statisticDay = statisticDay;
    }

    public String getStatisticProfit() {
        return statisticProfit;
    }

    public void setStatisticProfit(String statisticProfit) {
        this.statisticProfit = statisticProfit;
    }
}
