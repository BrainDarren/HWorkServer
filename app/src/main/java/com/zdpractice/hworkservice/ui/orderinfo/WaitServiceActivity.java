package com.zdpractice.hworkservice.ui.orderinfo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.zdpractice.hworkservice.R;
import com.zdpractice.hworkservice.model.OrderBean;
import com.zdpractice.hworkservice.support.MAPtool.RoutePlanActivity;

/**
 * Created by 刘海风 on 2016/8/26.
 */

public class WaitServiceActivity extends AppCompatActivity {
    private TextView order_waitservice_state,order_waitservice_address,order_waitservice_time;
    private Button order_waitservice_contact,order_waitservice_navigation;
    private OrderBean bean;
    String phonenumber;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_waitservice);
        Intent intent=getIntent();
        bean= (OrderBean) intent.getSerializableExtra("OrderBean");
        init();
    }
    public void init(){
        order_waitservice_state= (TextView) this.findViewById(R.id.order_waitservice_state);
        order_waitservice_address= (TextView) this.findViewById(R.id.order_waitservice_address);
        order_waitservice_time= (TextView) this.findViewById(R.id.order_waitservice_time);
        order_waitservice_contact= (Button) this.findViewById(R.id.order_waitservice_contact);
        order_waitservice_navigation= (Button) this.findViewById(R.id.order_waitservice_navigation);
        order_waitservice_state.setText(bean.getServiceclass().toString());
        order_waitservice_address.setText(bean.getAddressArea().toString());
        order_waitservice_time.setText(bean.getOrderAgreedTime().toString());
        phonenumber=bean.getContactMobile().toString();
        order_waitservice_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //添加打电话的方法
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                //url:统一资源定位符
                //uri:统一资源标示符（更广）
                intent.setData(Uri.parse("tel:" + phonenumber));
                //开启系统拨号器
                startActivity(intent);


            }
        });
        order_waitservice_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断定位服务是否开启 添加导航方法
                Intent intent=new Intent(WaitServiceActivity.this,RoutePlanActivity.class);
                intent.putExtra("bean",bean);
                startActivity(intent);
            }
        });

    }

}
