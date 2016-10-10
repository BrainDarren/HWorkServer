package com.zdpractice.hworkservice.ui.guidance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zdpractice.hworkservice.MainActivity;
import com.zdpractice.hworkservice.MyApplication;
import com.zdpractice.hworkservice.R;
import com.zdpractice.hworkservice.model.UserParentBean;
import com.zdpractice.hworkservice.support.networktool.NetWorkTools;
import com.zdpractice.hworkservice.ui.login.Login;

import org.xutils.common.Callback;
import org.xutils.x;

/**
 * Created by 15813 on 2016/8/15.
 */
public class GuidanceActivity extends Activity{

    private SharedPreferences sharedPreferences;
    private Intent intent;
    private UserParentBean userParentBean;
    private YinDaoSharedHelper yinDaoSharedHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题栏
        //取消状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //判断用户是不是第一次进入应用  引导页
        yinDaoSharedHelper =new YinDaoSharedHelper(getApplicationContext());
        String cishu=yinDaoSharedHelper.read();
        if(cishu.equals("")){
            intent=new Intent(GuidanceActivity.this,FirstGuidanceActivity.class);
            startActivity(intent);
            this.finish();
        }else if(cishu.equals("123")){
            //如果用户不是第一次登陆APP
            setContentView(R.layout.login_guidance);
            init();
            //判断用户是不是第一次登陆
            if(sharedPreferences==null){
                //第一次登陆跳转到LoginActivity
                intent=new Intent(GuidanceActivity.this,Login.class);
                startActivity(intent);
                finish();
            }else {
                //判断用户是否勾选过自动登陆
                if (sharedPreferences.getBoolean("自动登陆",false)){
                    //进行登陆请求
                    requestLogin();
                } else{
                    intent=new Intent(GuidanceActivity.this,Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        }



    }

    public void init(){
        //加载登录页的布局，获取复选框的状态
        //得到sharedpreference文件
        sharedPreferences =getSharedPreferences("AllData", Context.MODE_PRIVATE);
    }

//    /**
//     * 判断用户是否是第一次登陆应用
//     */
//    private void judge(){
//        //引导页
//        yinDaoSharedHelper =new YinDaoSharedHelper(getApplicationContext());
//        String cishu=yinDaoSharedHelper.read();
//        if(!cishu.equals("")){
//            //跳转到初次登陆应用的引导页
//            Intent intent=new Intent(GuidanceActivity.this,FirstGuidanceActivity.class);
//            startActivity(intent);
//            this.finish();
//        }
//    }

    public void requestLogin(){
        NetWorkTools.newInstance().requestLog(sharedPreferences.getString("用户名", ""), sharedPreferences.getString("密码", ""), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                userParentBean=new UserParentBean();
                userParentBean= JSON.parseObject(result,UserParentBean.class);
                MyApplication.userBean=userParentBean.getData();
                if(MyApplication.userBean.getToken()!=null) {
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    intent = new Intent(GuidanceActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(x.app(),userParentBean.getMessage().toString(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getApplicationContext(),"网络连接异常，或用户名密码不正确",Toast.LENGTH_LONG).show();
                intent = new Intent(GuidanceActivity.this, Login.class);
                startActivity(intent);
                finish();
//                intent = new Intent(GuidanceActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
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
