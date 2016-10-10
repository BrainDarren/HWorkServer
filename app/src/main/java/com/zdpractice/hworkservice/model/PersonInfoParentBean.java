package com.zdpractice.hworkservice.model;

import java.util.ArrayList;

/**
 * Created by 15813 on 2016/9/26.
 */
public class PersonInfoParentBean {
    private String code;
    private ArrayList<PersonInfoBean> data;
    private String message;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setData(ArrayList<PersonInfoBean> data) {
        this.data = data;
    }

    public ArrayList<PersonInfoBean> getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
