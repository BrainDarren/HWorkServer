package com.zdpractice.hworkservice.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.zdpractice.hworkservice.MainActivity;
import com.zdpractice.hworkservice.MyApplication;
import com.zdpractice.hworkservice.R;
import com.zdpractice.hworkservice.model.UserParentBean;
import com.zdpractice.hworkservice.support.networktool.AES;
import com.zdpractice.hworkservice.support.networktool.NetWorkTools;
import org.xutils.common.Callback;
import org.xutils.x;

/**
 * Created by 15813 on 2016/8/15.
 */

/*
* 连服务器查询 bug不能退回到登陆界面
* */
public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText username;
    private EditText userpwd;
    private CheckBox cb_Remember,cb_AutoLogin;
    private Button btn_login;
    private ImageView name_clear,pwd_clear;
    private SharedPreferences pregerences;
    private NetWorkTools netWorkTools;
    private String realname;
    private String realpwd;
    private UserParentBean userParentBean;
    private String provisionalpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        pregerences =getSharedPreferences("AllData", Context.MODE_PRIVATE);
        init();
    }

    /**
     * 初始化控件以及方法
     */
    public void init(){

        username= (EditText) findViewById(R.id.username);
        userpwd= (EditText) findViewById(R.id.userpwd);
        cb_Remember= (CheckBox) findViewById(R.id.cb_Remember);
        cb_AutoLogin= (CheckBox) findViewById(R.id.cb_AutoLogin);
        btn_login= (Button) findViewById(R.id.btn_Login);
        name_clear= (ImageView) findViewById(R.id.name_clear);
        pwd_clear= (ImageView) findViewById(R.id.mima_clear);

        //注册CheckBox状态改变事件
        Is_cb_remember_Checked();
        Is_cb_zddl_Checked();

        //判断用户以前是否记住过密码
        if(pregerences.getBoolean("记住密码",false)){
            cb_Remember.setChecked(true);
            username.setText(pregerences.getString("用户名",""));
            userpwd.setText(pregerences.getString("密码",""));
        }else{
            cb_Remember.setChecked(false);
        }

        String pwd=userpwd.getText().toString();
        if(pwd.length()>0){
            btn_login.setEnabled(true);
            pwd_clear.setVisibility(View.VISIBLE);
            btn_login.setBackground(getResources().getDrawable(R.drawable.login_btn_selector));
        }

        userpwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                String l=editable.toString();
                if(l.length()>0){
                    btn_login.setEnabled(true);
                    pwd_clear.setVisibility(View.VISIBLE);
                    btn_login.setBackground(getResources().getDrawable(R.drawable.login_btn_selector));
                }else{
                    pwd_clear.setVisibility(View.GONE);
                    btn_login.setEnabled(false);
                    btn_login.setBackground(getResources().getDrawable(R.drawable.login_btn_click_before));
                }
            }
        });
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String l=editable.toString();
                if(l.length()>0){
                    name_clear.setVisibility(View.VISIBLE);
                }else{
                    name_clear.setVisibility(View.GONE);
                }
            }
        });

        btn_login.setOnClickListener(this);

        name_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username.setText("");
            }
        });
        pwd_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userpwd.setText("");
            }
        });


    }

    //单击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Login:
                realname = username.getText().toString();
                //获取未加密的临时密码
                provisionalpwd= userpwd.getText().toString();
                if (realname.matches("1\\d{10}")) {
                    if (provisionalpwd.matches("\\w{6,10}")) {

                        //联网判定用户是否否在
                        //访问网络给Application的UserBean赋值
                        netWorkTools=NetWorkTools.newInstance();
                        //给密码加密
                        realpwd = AES.encode(provisionalpwd);
                        //登录
                        requestLogin();
                        //如果记住密码，将用户名与密码存入sharedperference文件
                        if(cb_Remember.isChecked()) {
                            SharedPreferences.Editor editor = pregerences.edit();
                            editor.putString("用户名", realname);
                            //将未加密过的密码存入SharedPreference
                            editor.putString("密码", realpwd);
                            editor.commit();
                        }

                    } else {
                        Toast.makeText(Login.this, "密码必须为6至10为的数字、字母、下划线组合", Toast.LENGTH_SHORT).show();
                        userpwd.setText("");
                    }
                } else {
                    Toast.makeText(Login.this, "用户名必须为手机号", Toast.LENGTH_SHORT).show();
                    username.setText("");
                    userpwd.setText("");
                }
                break;

        }

    }

    /**
     * 监听记住密码的事件
     */
    public void Is_cb_remember_Checked(){
        cb_Remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cb_Remember.isChecked()){
                    pregerences.edit().putBoolean("记住密码",true).commit();

                }else{
                    pregerences.edit().putBoolean("记住密码",false).commit();
                }
            }
        });
    }

    /**
     * 监听自动登陆的事件
     */
    public void Is_cb_zddl_Checked(){
        cb_AutoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cb_AutoLogin.isChecked()){
                    pregerences.edit().putBoolean("自动登陆",true).commit();
                }else{
                    pregerences.edit().putBoolean("自动登陆",true).commit();
                }
            }
        });
    }

    /**
     * 登录方法
     */
    public void requestLogin(){
        netWorkTools.requestLog(realname, realpwd, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                userParentBean=new UserParentBean();
                userParentBean= JSON.parseObject(result,UserParentBean.class);
                MyApplication.userBean=userParentBean.getData();
                if(MyApplication.userBean.getToken()!=null) {
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(x.app(),userParentBean.getMessage().toString(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
              Toast.makeText(getApplicationContext(),"请检查网络连接是否正常",Toast.LENGTH_LONG).show();
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
//http://192.168.0.158:8080/exj/removte
//用户名或手机号：15932698423 密码：698423