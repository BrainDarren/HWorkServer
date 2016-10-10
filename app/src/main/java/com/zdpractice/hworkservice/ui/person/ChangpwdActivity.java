package com.zdpractice.hworkservice.ui.person;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.zdpractice.hworkservice.MyApplication;
import com.zdpractice.hworkservice.R;
import com.zdpractice.hworkservice.model.ChangePwdBean;
import com.zdpractice.hworkservice.model.UserBean;
import com.zdpractice.hworkservice.support.networktool.NetWorkTools;
import com.zdpractice.hworkservice.ui.login.Login;

import org.xutils.common.Callback;
import org.xutils.x;

/**
 * Created by YL on 2016/8/24.
 */
public class ChangpwdActivity extends Activity  {
    private EditText oldpwd,newpwd,againpwd;
    private Button btnOK;
    private UserBean userBean;
    private ImageView comeback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_changpwd);
        init();

    }
    public void init(){
        oldpwd= (EditText) this.findViewById(R.id.etOldPwd);
        newpwd= (EditText) this.findViewById(R.id.etNewPwd);
        againpwd= (EditText) this.findViewById(R.id.etagainpwd);
        comeback=(ImageView) this.findViewById(R.id.comeback1);
        comeback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnOK= (Button) this.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String opwd=oldpwd.getText().toString();
                String npwd=newpwd.getText().toString();
                String apwd=againpwd.getText().toString();
                if(npwd.equals(apwd)){
                    NetWorkTools netWorkTools=new NetWorkTools();
                    netWorkTools.changPwd(MyApplication.userBean.getToken(), MyApplication.userBean.getUserid()+"", opwd, npwd, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            ChangePwdBean bean= JSON.parseObject(result,ChangePwdBean.class);
                            if(bean.getCode().equals(200)){
                                Toast.makeText(x.app(),"密码修改成功！",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ChangpwdActivity.this,Login.class);
                                startActivity(intent);

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
                    oldpwd.setText(opwd);
                    newpwd.setText(npwd);
                }else{
                    Toast.makeText(ChangpwdActivity.this,"两次密码输入不一致",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



}
