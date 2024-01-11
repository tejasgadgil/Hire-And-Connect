package com.example.hireconnect;

public class TraderModel {

    String traderName;
    String phNo;
    String costUB;
    String costLB;

    public TraderModel(String traderName, String phNo, String costUB, String costLB) {
        this.traderName = traderName;
        this.phNo = phNo;
        this.costUB = costUB;
        this.costLB = costLB;
    }

    public String getTraderName() {
        return traderName;
    }

    public String getPhNo() {
        return phNo;
    }

    public String getCostUB() {
        return costUB;
    }

    public String getCostLB() {
        return costLB;
    }

    public String getRange(){
        return costLB + "-" + costUB;
    }
}
