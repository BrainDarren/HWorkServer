package com.zdpractice.hworkservice.support.networktool;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.zdpractice.hworkservice.MainActivity;
import com.zdpractice.hworkservice.MyApplication;
import com.zdpractice.hworkservice.model.UploadBean;
import com.zdpractice.hworkservice.model.UserParentBean;
import com.zdpractice.hworkservice.support.location.locationService;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by 15813 on 2016/8/15.
 */
public class NetWorkTools {
    public static String url="http://192.168.2.240:8080/exj/removte";
    private RequestParams requestParams;
    private static NetWorkTools netWorkTools;
    private locationService service;

    public static NetWorkTools newInstance(){
        if (netWorkTools==null){
            netWorkTools=new NetWorkTools();
        }
        return netWorkTools;
    }

    /**
     * 登录请求
     * @param name 用户名
     * @param pwd 密码
     * @param callback 接口
     */
    public void requestLog(String name, String pwd,Callback.CommonCallback<String> callback) {
        //TODO 登录URL
        requestParams=new RequestParams(url);
        //添加参数
        requestParams.addBodyParameter("method","app/login/artistLogin");
        requestParams.addBodyParameter("loginname",name);
        requestParams.addBodyParameter("password",pwd);
        getJson(requestParams,callback);
        requestParams=null;
    }

    /**
     * 获取竞单列表请求
     * @param token token
     * @param userid 用户id
     */
    public void requestCompeteOrder(String token,String userid,Callback.CommonCallback<String> callback){
        //TODO 获取竞单URL
        requestParams=new RequestParams(url);
        //添加参数
        requestParams.addBodyParameter("method","order/getFightingOrderList");
        requestParams.addBodyParameter("token",token);
        requestParams.addBodyParameter("userid",userid);
        getJson(requestParams, callback);
        requestParams=null;
    }

    /**
     * 获取下一单请求
     * @param token token
     * @param userid 用户id
     */
    public void requestNextOrder(String token,String userid,Callback.CommonCallback<String> callback){
        //TODO 获取竞单URL
        requestParams=new RequestParams(url);
        //添加参数
        requestParams.addBodyParameter("method","order/getMyNextOrder");
        requestParams.addBodyParameter("token",token);
        requestParams.addBodyParameter("userid",userid);
        getJson(requestParams, callback);
        requestParams=null;
    }


    /**
     * 获取已抢单列表
     * @param token token
     * @param userid 用户id
     */
    public void requestWorkingOrder(String token,String userid,Callback.CommonCallback<String> callback){
        //TODO 获取已抢单URL
        requestParams=new RequestParams(url);
        //添加参数
        requestParams.addBodyParameter("method","order/getFoughtOrderList");
        requestParams.addBodyParameter("token",token);
        requestParams.addBodyParameter("userid",userid);
        getJson(requestParams, callback);
        requestParams=null;
    }

    /**
     * 发送历史订单、竞单中、已完成竞单列表请求
     * @param token token
     * @param userid 用户id
     * @param pageNo 页数
     * @param pageSize 每页的数量
     * @param servicestate 服务状态(不是必须) -1代表未完成 1代表已完成
     * @param status 订单状态(不是必须) 1-竟单中；2-已生效等待服务；3-取消；4-超时；5-阿姨确认等待支付；6-已完成支付
     * @param yMonth 月份(不是必须)
     * @param providerJudgeLevel 评价(不是必须)
     */
    public void requestEverOrder(String token,String userid,String pageNo,
                                 String pageSize,String servicestate,
                                 String status,String yMonth,String providerJudgeLevel,Callback.CommonCallback<String> callback){
        //TODO 获取历史订单URL
        requestParams=new RequestParams(url);
        //添加参数
        requestParams.addBodyParameter("method","order/getMyOrderList");
        requestParams.addBodyParameter("token",token);
        requestParams.addBodyParameter("userid",userid);
        requestParams.addBodyParameter("pageNo",pageNo);
        requestParams.addBodyParameter("pageSize",pageSize);
        requestParams.addBodyParameter("servicestate",servicestate);
        requestParams.addBodyParameter("status",status);
        requestParams.addBodyParameter("yMonth",yMonth);
        requestParams.addBodyParameter("providerJudgeLevel",providerJudgeLevel);
        getJson(requestParams, callback);
        requestParams=null;
    }

    /**
     *发送抢单请求
     * @param token
     * @param orderId 订单id
     * @param price 价格
     * @param providerId 服务提供者id
     */
    public void requestConfirmOrder(String token,String orderId,String price,
                                    String providerId,Callback.CommonCallback<String> callback){
        //TODO 抢单URL
        requestParams=new RequestParams(url);
        requestParams.addBodyParameter("method","order/grabOrder");
        //添加参数
        requestParams.addBodyParameter("token",token);
        requestParams.addBodyParameter("orderId",orderId);
        requestParams.addBodyParameter("price",price);
        requestParams.addBodyParameter("providerId",providerId);
        getJson(requestParams, callback);
        requestParams=null;
    }


    /**
     *发送支付信息及服务数量请求
     * @param token
     * @param orderno
     * @param confirmnum 服务数量
     * @param payType 支付方式
     * @param baiduMapLng 经度
     * @param baiduMapLat 纬度
     * @param callback
     */
    public void requestPayInfo(String token,String orderno,String confirmnum,
                               String payType,String baiduMapLng,String baiduMapLat,
                               Callback.CommonCallback<String> callback){
        requestParams=new RequestParams(url);
        requestParams.addBodyParameter("method","order/submitPayInfo");
        requestParams.addBodyParameter("token",token);
        requestParams.addBodyParameter("orderno",orderno);
        requestParams.addBodyParameter("confirmnum",confirmnum);
        requestParams.addBodyParameter("payType",payType);
        requestParams.addBodyParameter("baiduMapLng",baiduMapLng);
        requestParams.addBodyParameter("baiduMapLat",baiduMapLat);
        getJson(requestParams, callback);
        requestParams=null;

    }

    /**
     * 发送月收入请求
     * @param token token
     * @param orderno 订单号
     * @param yMonth 月份
     * @param userid 用户id
     */
    public void requestMonthIncome(String token,
                                   String orderno,String yMonth,String userid,Callback.CommonCallback<String> callback) {
        requestParams = new RequestParams(url);

        //添加参数
        requestParams.addBodyParameter("method", "provider/getMyIncomeThisMonth");
        requestParams.addBodyParameter("token", token);
        requestParams.addBodyParameter("orderId", orderno);
        requestParams.addBodyParameter("price", yMonth);
        requestParams.addBodyParameter("providerId", userid);
        getJson(requestParams, callback);
        requestParams=null;
    }

    /**
     *修改密码
     */
    public void changPwd(String token, String userid, String pwd, String oldpwd,Callback.CommonCallback<String> callback ){
        requestParams=new RequestParams(url);
        requestParams.addBodyParameter("method","provider/saveEditPwd");
        requestParams.addBodyParameter("token",token);
        requestParams.addBodyParameter("userid",userid);
        requestParams.addBodyParameter("password",pwd);
        requestParams.addBodyParameter("password",oldpwd);
        getJson(requestParams, callback);
        requestParams=null;
    }

    /**
     * 获取最新通知信息
     * @param token
     * @param userid
     * @param callback
     */
    public void requestNewSysNoticeInfo(String token, String userid,Callback.CommonCallback<String> callback ){
        requestParams=new RequestParams(url);
        requestParams.addBodyParameter("method","notice/getNewSysNoticeInfo");
        requestParams.addBodyParameter("token",token);
        requestParams.addBodyParameter("userid",userid);
        getJson(requestParams, callback);
        requestParams=null;
    }

    /**
     * 请求通知列表
     * @param token
     * @param userid
     * @param pageNo
     * @param pageSize
     * @param callback
     */
    public void requestNoticeList(String token, String userid,String pageNo,String pageSize,Callback.CommonCallback<String> callback ){
        requestParams=new RequestParams(url);
        requestParams.addBodyParameter("method","notice/searchNoticeList");
        requestParams.addBodyParameter("token",token);
        requestParams.addBodyParameter("userid",userid);
        requestParams.addBodyParameter("pageNo",pageNo);
        requestParams.addBodyParameter("pageSize",pageSize);
        getJson(requestParams, callback);
        requestParams=null;
    }

    /**
     * 提交意见反馈
     * @param token
     * @param userid
     * @param nickname
     * @param content
     * @param contact
     * @param feedbacktype
     * @param callback
     */
    public void saveFeedback(String token,String userid,String nickname,String content,String contact,String feedbacktype,Callback.CommonCallback<String> callback){
        requestParams=new RequestParams(url);
        requestParams.addBodyParameter("method","feedback/saveFeedback");
        requestParams.addBodyParameter("token",token);
        requestParams.addBodyParameter("userid",userid);
        requestParams.addBodyParameter("content",content);
        requestParams.addBodyParameter("contact",contact);
        requestParams.addBodyParameter("nickname",nickname);
        requestParams.addBodyParameter("feedbacktype",feedbacktype);
        getJson(requestParams, callback);

    }

    /**
     * 个人基本情况
     */
    public void requestPersonalInfo(String yMonth, Callback.CommonCallback<String> callback ){
        requestParams=new RequestParams(url);
        requestParams.addBodyParameter("method","provider/getMyServiceInfoThisMonth");
        requestParams.addBodyParameter("token",MyApplication.userBean.getToken());
        requestParams.addBodyParameter("yMonth",yMonth);
        requestParams.addBodyParameter("userid",MyApplication.userBean.getUserid()+"");
        getJson(requestParams, callback);
        requestParams=null;
    }

    /**
     * 月收入总额
     */
    public void requestMonthmoey(String yMonth, Callback.CommonCallback<String> callback ){
        requestParams=new RequestParams(url);
        requestParams.addBodyParameter("method","provider/getMyIncomeThisMonth");
        requestParams.addBodyParameter("token",MyApplication.userBean.getToken());
        requestParams.addBodyParameter("yMonth",yMonth);
        requestParams.addBodyParameter("userid",MyApplication.userBean.getUserid()+"");
        getJson(requestParams, callback);
        requestParams=null;
    }


    /**
     * 用户申请上线接单
     * @param token
     * @param isonline
     * @param userid
     * @param baidulng 维度
     * @param baidulat 经度
     * @param callback
     */
    public void requestUploadCoordinate(String token, String isonline,String userid,String baidulng,String baidulat,Callback.CommonCallback<String> callback){
        requestParams=new RequestParams(url);
        requestParams.addBodyParameter("method","provider/uploadCoordinate");
        requestParams.addBodyParameter("token",token);
        requestParams.addBodyParameter("isonline",isonline);
        requestParams.addBodyParameter("employeeid",userid);
        requestParams.addBodyParameter("baidulng",baidulng);
        requestParams.addBodyParameter("baidulat",baidulat);
        getJson(requestParams, callback);
        requestParams=null;
    }


    /**
     * 判断定位服务是否开启
     */
    public boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    /**
     * 控制定位服务的启动与停止
     * @param falg
     */
    public void locationtool(Boolean falg){
        service=MyApplication.service;
        if(falg){
            service.registerListener(mListener);
            service.setLocationOption(service.getDefaultLocationClientOption());
            service.start();
        }else{
            service.unregisterListener(mListener);
            MyApplication.locationBean.setLatitude(null);
            MyApplication.locationBean.setLontitude(null);
            MyApplication.locationBean.setAddress(null);
            MyApplication.locationBean.setCity(null);
            service.stop();
        }
    }

    /**
     * 开启定位时服务的监听器
     */
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {

            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                String latitude=""+location.getLatitude();
                String log=""+location.getLongitude();
                //如果坐标bean为空，赋值
                if (MyApplication.locationBean.getLatitude()==null){
                    MyApplication.locationBean.setLatitude(latitude);
                    MyApplication.locationBean.setLontitude(log);
                    MyApplication.locationBean.setAddress(location.getDistrict());
                    MyApplication.locationBean.setCity(location.getCity());
                    OnlineUpline("1",latitude,log);
                }else if(MyApplication.locationBean.getLatitude()!=null && !latitude.equals(MyApplication.locationBean.getLatitude())){
                    OnlineUpline("1",latitude,log);
                }
            }

        }
    };




    /**
     * 发送post请求
     * @param requestParams
     * @param callback
     */
    protected void getJson(RequestParams requestParams,Callback.CommonCallback<String> callback){
        x.http().post(requestParams,callback);
    }


    //访问网络申请上下线
    public void OnlineUpline(String isonline,String latitude,String longitude){
        NetWorkTools.newInstance().requestUploadCoordinate(MyApplication.userBean.getToken(),isonline,MyApplication.userBean.getUserid()+"",
                latitude,longitude, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        UploadBean uploadBean= JSON.parseObject(result,UploadBean.class);
                        if (uploadBean.getCode().equals("200")){
                            Log.v("Online","在线状态");
                        }else {
                            Log.v("Online","离线状态");
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
    }


}
