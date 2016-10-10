package com.zdpractice.hworkservice.model;

/**
 * Created by 15813 on 2016/8/15.
 */
public class UserBean {
    private  long	userid;
    private  String	wechatUnioID;
    private  String	wechatOpenId;
    private  String	mobile;	//手机
    private  String	realName	;	//真实姓名
    private  int	sex;	//性别
    private  int	age;	//年龄
    private  int	experience;	//工作年龄
    private  String	password	;	//密码
    private  String	province;	//省份
    private  String	city;	//城市
    private  String	address;	//住址
    private  String	icon;	//头像
    private  int	userType;	//用户类型
    private  int	balance;	//账户余额
    private  int	status;	//状态
    private String token;//token


    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getWechatUnioID() {
        return wechatUnioID;
    }

    public void setWechatUnioID(String wechatUnioID) {
        this.wechatUnioID = wechatUnioID;
    }

    public String getWechatOpenId() {
        return wechatOpenId;
    }

    public void setWechatOpenId(String wechatOpenId) {
        this.wechatOpenId = wechatOpenId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
