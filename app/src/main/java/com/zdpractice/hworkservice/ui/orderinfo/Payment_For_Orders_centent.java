package com.zdpractice.hworkservice.ui.orderinfo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.zdpractice.hworkservice.MyApplication;
import com.zdpractice.hworkservice.R;
import com.zdpractice.hworkservice.model.ChangePwdBean;
import com.zdpractice.hworkservice.model.OrderBean;
import com.zdpractice.hworkservice.support.networktool.NetWorkTools;
import org.xutils.common.Callback;

/**
 * Created by Administrator on 2016/9/3.
 */

public class Payment_For_Orders_centent extends AppCompatActivity {
    private OrderBean orderBean;
    private int i = 1, j;
    private Button  btn_submit;
    private ImageView img_reduce, img_add,img_icon;
    private EditText etservice_number;
    private TextView tvtotal_prices;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.payment_for_orders_centent);
        Intent intent = getIntent();
        Toolbar tbgoback= (Toolbar) findViewById(R.id.toolbar_obligation);
        //点导航栏图标退出
        img_icon= (ImageView) findViewById(R.id.toolbar_icon_obligation);
        img_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        orderBean = (OrderBean) intent.getSerializableExtra("OrderBean");
        TextView tvorder_number = (TextView) findViewById(R.id.order_number_obligation);
        tvorder_number.setText("" + orderBean.getOrderno());
        TextView tvorder_status = (TextView) findViewById(R.id.order_status_obligation);
        tvorder_status.setText("订单状态：待付款！");
        TextView tvorder_type = (TextView) findViewById(R.id.order_typ_obligation);
        if(orderBean.getServiceclass().equals("0001000300010001")) {
            tvorder_type.setText("订单类型：日常保洁");
        }
        TextView tvservice_time = (TextView) findViewById(R.id.service_time_obligation);
        tvservice_time.setText(orderBean.getOrderAgreedTime());
        TextView tvreservation_number = (TextView) findViewById(R.id.reservation_number_obligation);
        tvreservation_number.setText("" + orderBean.getOrdernum());
        TextView tvservice_site = (TextView) findViewById(R.id.service_site_obligation);
        tvservice_site.setText("" + orderBean.getAddressArea());
        TextView tvunit_price = (TextView) findViewById(R.id.unit_price_obligation);
        tvunit_price.setText("" + orderBean.getOrderprice());
        tvtotal_prices = (TextView) findViewById(R.id.total_prices_obligation);
        img_reduce = (ImageView) findViewById(R.id.reduce_obligation);
        etservice_number = (EditText) findViewById(R.id.service_number_obligation);
        img_add = (ImageView) findViewById(R.id.img_add_obligation);
        btn_submit = (Button) findViewById(R.id.btn_submit_obligation);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //EditText内容发生改变时监听事件 改变前 改变中 改变后
        etservice_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() != 0 && j < 100) {
                    j = Integer.parseInt(editable.toString());
                }else{
                    Toast.makeText(Payment_For_Orders_centent.this, "请输入1~100以内整数！", Toast.LENGTH_SHORT).show();
                    j=0;
                }
                if (i != j) {
                    i = j;
                    tvtotal_prices.setText("" + (i * orderBean.getOrderprice()));
                }
            }
        });

        img_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i > 1) {
                    i--;
                    etservice_number.setText(i + "");
                    tvtotal_prices.setText("" + (i * orderBean.getOrderprice()));
                }
            }
        });
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                etservice_number.setText(i + "");
                tvtotal_prices.setText("" + (i * orderBean.getOrderprice()));
            }
        });
        tvtotal_prices.setText("" + (i * orderBean.getOrderprice()));
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String confirmnum=etservice_number.getText().toString();
                NetWorkTools.newInstance().requestPayInfo(MyApplication.userBean.getToken(), orderBean.getOrderno(), confirmnum,
                        "2", MyApplication.locationBean.getLontitude(), MyApplication.locationBean.getLatitude(),
                        new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                ChangePwdBean bean=JSON.parseObject(result, ChangePwdBean.class);
                                if(bean.getCode().equals("200")){
                                    Toast.makeText(getApplicationContext(),"提交支付账单成功",Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(getApplicationContext(),"支付失败",Toast.LENGTH_LONG).show();
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
        });
    }
}
