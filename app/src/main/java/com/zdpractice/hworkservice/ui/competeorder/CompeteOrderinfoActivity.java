package com.zdpractice.hworkservice.ui.competeorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zdpractice.hworkservice.MyApplication;
import com.zdpractice.hworkservice.R;
import com.zdpractice.hworkservice.model.ChangePwdBean;
import com.zdpractice.hworkservice.model.OrderBean;
import com.zdpractice.hworkservice.support.networktool.NetWorkTools;

import org.xutils.common.Callback;

public class CompeteOrderinfoActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private TextView tv_back,tv_fight;
    private EditText editPrice;
    private OrderBean orderBean;
    private String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_compete_orderinfo);
        init();
    }

    public void init(){
        toolbar= (Toolbar) this.findViewById(R.id.tb_competeorderinfo);
        toolbar.setTitle("订单详情");
        tv_back= (TextView) this.findViewById(R.id.tv_back);
        tv_fight= (TextView) this.findViewById(R.id.competeorder_tv_compete);
        editPrice= (EditText) this.findViewById(R.id.et_fight_price);
        //抢单按钮点击事件
        tv_fight.setOnClickListener(this);
        //返回点击事件
        tv_back.setOnClickListener(this);
        Intent intent=getIntent();
        orderBean= (OrderBean) intent.getSerializableExtra("orderbean");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_back:
                this.finish();
                break;
            case R.id.competeorder_tv_compete:
                price=editPrice.getText().toString();
                if(price==null || price.equals("")){
                    Toast.makeText(getApplicationContext(),"价格不能为空",Toast.LENGTH_LONG).show();
                    break;
                }else{
                    NetWorkTools.newInstance().requestConfirmOrder(MyApplication.userBean.getToken(), orderBean.getOrderno() + "", price, MyApplication.userBean.getUserid()+"", new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            ChangePwdBean bean= JSON.parseObject(result,ChangePwdBean.class);
                            if (bean.getCode().equals("200")){
                                Toast.makeText(getApplicationContext(),"开始竞单",Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(getApplicationContext(),"竞单失败,请检查网络或重试",Toast.LENGTH_LONG).show();
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
                break;
        }
    }
}
