package com.zdpractice.hworkservice;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;

import com.baidu.mapapi.SDKInitializer;
import com.zdpractice.hworkservice.model.LocationBean;
import com.zdpractice.hworkservice.model.UserBean;
import com.zdpractice.hworkservice.support.location.locationService;
import com.zdpractice.hworkservice.ui.guidance.FirstGuidanceActivity;
import com.zdpractice.hworkservice.ui.guidance.YinDaoSharedHelper;

import org.xutils.x;

/**
 * Created by 15813 on 2016/8/16.
 */
public class MyApplication extends Application {

    /**
     * 此应用的token，在登陆页面赋值
     */
    public static UserBean userBean;
    public static locationService service;
    public static LocationBean locationBean;
    public Vibrator mVibrator;
    private YinDaoSharedHelper yinDaoSharedHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        SDKInitializer.initialize(getApplicationContext());
        userBean=new UserBean();
        locationBean=new LocationBean();
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        service=new locationService(getApplicationContext());
    }

}
