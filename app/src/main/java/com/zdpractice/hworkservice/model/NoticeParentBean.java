package com.zdpractice.hworkservice.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/18.
 */

public class NoticeParentBean implements Serializable{
    private String  code;
    private ArrayList<NoticeBean> data;
    private String  data1;
    private String  data2;
    private String  message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<NoticeBean> getData() {
        return data;
    }

    public void setData(ArrayList<NoticeBean> data) {
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
