package com.zdpractice.hworkservice.model;

/**
 * Created by Administrator on 2016/8/18.
 */

public class UpgradeParentBean {
    private String  code;
    private UpgradeBean data;
    private String  data1;
    private String  data2;
    private String  message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UpgradeBean getData() {
        return data;
    }

    public void setData(UpgradeBean data) {
        this.data = data;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
