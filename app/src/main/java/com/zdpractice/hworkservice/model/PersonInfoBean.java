package com.zdpractice.hworkservice.model;

/**
 * Created by 15813 on 2016/9/26.
 */
public class PersonInfoBean {

    private String balance;
    private String finishedOrderAmout;
    private String goodJudgeAmount;

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBalance() {
        return balance;
    }

    public void setGoodJudgeAmount(String goodJudgeAmount) {
        this.goodJudgeAmount = goodJudgeAmount;
    }

    public String getGoodJudgeAmount() {
        return goodJudgeAmount;
    }

    public void setFinishedOrderAmout(String finishedOrderAmout) {
        this.finishedOrderAmout = finishedOrderAmout;
    }

    public String getFinishedOrderAmout() {
        return finishedOrderAmout;
    }
}
