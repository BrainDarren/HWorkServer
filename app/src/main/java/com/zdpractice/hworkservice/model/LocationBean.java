package com.zdpractice.hworkservice.model;

/**
 * Created by 刘海风 on 2016/8/29.
 */

public class LocationBean {
    private String latitude;//纬度
    private String lontitude;//经度
    private  String address;//地址
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLontitude() {
        return lontitude;
    }

    public void setLontitude(String lontitude) {
        this.lontitude = lontitude;
    }
}
