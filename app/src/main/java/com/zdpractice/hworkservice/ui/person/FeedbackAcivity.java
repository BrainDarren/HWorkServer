package com.zdpractice.hworkservice.ui.person;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;
import com.alibaba.fastjson.JSON;
import com.zdpractice.hworkservice.MyApplication;
import com.zdpractice.hworkservice.R;
import com.zdpractice.hworkservice.model.ChangePwdBean;
import com.zdpractice.hworkservice.model.UserBean;
import com.zdpractice.hworkservice.support.networktool.NetWorkTools;

import org.xutils.common.Callback;
import org.xutils.x;

/**
 * Created by YL on 2016/8/18.
 */
public class FeedbackAcivity extends Activity {
    private EditText content,phone;
    private Button btnok;
    private Toolbar toolbar;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_feedback);

        content = (EditText)findViewById(R.id.et_feedback);
        phone = (EditText)findViewById(R.id.et_phonenum);
        back = (ImageView)findViewById(R.id.comeback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnok = (Button)findViewById(R.id.btn_feedback);
        //提交的点击事件
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content1 = content.getText().toString();
                String pnum = phone.getText().toString();
                if (pnum.matches("1\\d{10}")) {
                    NetWorkTools tools = new NetWorkTools();
                    tools.saveFeedback(MyApplication.userBean.getToken(), MyApplication.userBean.getUserid() + "", java.net.URLEncoder.encode(MyApplication.userBean.getRealName()), java.net.URLEncoder.encode(content1), pnum, "000100140002", new Callback.CommonCallback<String>() {

                        @Override
                        public void onSuccess(String result) {
                            ChangePwdBean bean = JSON.parseObject(result, ChangePwdBean.class);
                            if (bean.getCode().equals("200")) {
                                Toast.makeText(x.app(), "提交意见成功！", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(x.app(), bean.getMessage().toString(), Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            Toast.makeText(x.app(), ex.toString(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });


                }else{
                    Toast.makeText(x.app(), "手机号只能为11位数字", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
