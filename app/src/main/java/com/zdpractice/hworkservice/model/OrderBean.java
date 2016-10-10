package com.zdpractice.hworkservice.model;

import java.io.Serializable;

/**
 * Created by 15813 on 2016/8/15.
 */
public class OrderBean implements Serializable{
    private  long	oid;	//编号
    private  int	ordertype;	//竟单类型
    private  int	contendcount;	//竟单数量
    private  int	weeknum;	//服务周数
    private  int	servicestate;	//服务状态	0未完成1完成
    private  int	servercount;	//总服务次数
    private  int	usecount;		//已服务次数
    private  String	orderno;	//订单编号
    private  String	creationDate;	//创建时间
    private  long	customerId;	//消费者
    private  String	addressArea;	//住址区域和楼盘名
    private  String	addressString;	//门牌号
    private  int	confirmDistance;	//提交地点与服务地点距离
    private  String	baiduMapLng;	//百度经度
    private  String	baiduMapLat;	//百度纬度
    private  String	orderAgreedTime;	//预约时间
    private  double	ordernum;	//预约数量/服务次数
    private  String	orderDurationTime;		//预计结束时间
    private  String	serviceclass;	//服务种类
    private  String	linkman;	//联系人
    private  String	contactMobile	;	//联系电话
    private  String	customerRemark;	//业务补充说明
    private  int	state	;	//1竟单中2已生效待服务3取消4超时5阿姨确认等待支付6已完成支付
    private  long	providerId	;	//阿姨ID
    private  String	confirmDate;	//成交时间
    private  double	orderprice;	//单价
    private  double	confirmnum;	//实际数量
    private  double	gold;	//金币
    private  double	payAmount;	//订单总价（单价*小时）
    private  String	paymentDate;	//支付时间
    private  double	paymoney;	//实付金额
    private  String	payid;	//支付ID
    private  int	providerJudgeLevel	;	//订单评价 1不太满意2有待提高3基本满意4我很满意5非常满意

    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }

    public int getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(int ordertype) {
        this.ordertype = ordertype;
    }

    public int getContendcount() {
        return contendcount;
    }

    public void setContendcount(int contendcount) {
        this.contendcount = contendcount;
    }

    public int getWeeknum() {
        return weeknum;
    }

    public void setWeeknum(int weeknum) {
        this.weeknum = weeknum;
    }

    public int getServicestate() {
        return servicestate;
    }

    public void setServicestate(int servicestate) {
        this.servicestate = servicestate;
    }

    public int getServercount() {
        return servercount;
    }

    public void setServercount(int servercount) {
        this.servercount = servercount;
    }

    public int getUsecount() {
        return usecount;
    }

    public void setUsecount(int usecount) {
        this.usecount = usecount;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }



    public String getAddressArea() {
        return addressArea;
    }

    public void setAddressArea(String addressArea) {
        this.addressArea = addressArea;
    }

    public String getAddressString() {
        return addressString;
    }

    public void setAddressString(String addressString) {
        this.addressString = addressString;
    }

    public int getConfirmDistance() {
        return confirmDistance;
    }

    public void setConfirmDistance(int confirmDistance) {
        this.confirmDistance = confirmDistance;
    }

    public String getBaiduMapLng() {
        return baiduMapLng;
    }

    public void setBaiduMapLng(String baiduMapLng) {
        this.baiduMapLng = baiduMapLng;
    }

    public String getBaiduMapLat() {
        return baiduMapLat;
    }

    public void setBaiduMapLat(String baiduMapLat) {
        this.baiduMapLat = baiduMapLat;
    }

    public String getOrderAgreedTime() {
        return orderAgreedTime;
    }

    public void setOrderAgreedTime(String orderAgreedTime) {
        this.orderAgreedTime = orderAgreedTime;
    }

    public double getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(double ordernum) {
        this.ordernum = ordernum;
    }

    public String getOrderDurationTime() {
        return orderDurationTime;
    }

    public void setOrderDurationTime(String orderDurationTime) {
        this.orderDurationTime = orderDurationTime;
    }

    public String getServiceclass() {
        return serviceclass;
    }

    public void setServiceclass(String serviceclass) {
        this.serviceclass = serviceclass;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getCustomerRemark() {
        return customerRemark;
    }

    public void setCustomerRemark(String customerRemark) {
        this.customerRemark = customerRemark;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getProviderId() {
        return providerId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public void setProvideId(long provideId) {
        this.providerId = provideId;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public double getOrderprice() {
        return orderprice;
    }

    public void setOrderprice(double orderprice) {
        this.orderprice = orderprice;
    }

    public double getConfirmnum() {
        return confirmnum;
    }

    public void setConfirmnum(double confirmnum) {
        this.confirmnum = confirmnum;
    }

    public double getGold() {
        return gold;
    }

    public void setGold(double gold) {
        this.gold = gold;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getPaymoney() {
        return paymoney;
    }

    public void setPaymoney(double paymoney) {
        this.paymoney = paymoney;
    }

    public String getPayid() {
        return payid;
    }

    public void setPayid(String payid) {
        this.payid = payid;
    }

    public int getProviderJudgeLevel() {
        return providerJudgeLevel;
    }

    public void setProviderJudgeLevel(int providerJudgeLevel) {
        this.providerJudgeLevel = providerJudgeLevel;
    }
}
