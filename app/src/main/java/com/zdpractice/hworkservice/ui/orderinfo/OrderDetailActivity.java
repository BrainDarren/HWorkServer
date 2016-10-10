package com.zdpractice.hworkservice.ui.orderinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.zdpractice.hworkservice.R;
import com.zdpractice.hworkservice.model.OrderBean;

/**
 * Created by 15813 on 2016/8/15.
 */
public class OrderDetailActivity extends AppCompatActivity {
    private TextView linkname, address, time, serviceclass, confirmnum, paymoney, providerJudgeLevel,tvdefaultlv;
    private OrderBean bean;
    private ImageView img_icon;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.orderinfo_fought_details);

        Intent intent = getIntent();
        bean = (OrderBean) intent.getSerializableExtra("OrderBean");
        init();
    }

    public void init() {
        img_icon= (ImageView) findViewById(R.id.toolbar_icon_order);
        img_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        linkname = (TextView) findViewById(R.id.order_linkname);
        address = (TextView) findViewById(R.id.order_address);
        time = (TextView) findViewById(R.id.order_time);
        serviceclass = (TextView) findViewById(R.id.order_serviceclass);
        confirmnum = (TextView) findViewById(R.id.order_confirmnum);
        paymoney = (TextView) findViewById(R.id.order_paymoney);
        providerJudgeLevel = (TextView) findViewById(R.id.order_providerJudgeLevel);
        tvdefaultlv= (TextView) this.findViewById(R.id.tv_default_level);
        linkname.setText(bean.getLinkman());
        time.setText(bean.getOrderAgreedTime());
        address.setText(bean.getAddressArea());
        if(bean.getServiceclass().equals("0001000300010001")){
            serviceclass.setText("日常服务");
        }else {
            serviceclass.setText("其他");
        }
        confirmnum.setText(bean.getConfirmnum() + "");
        paymoney.setText(bean.getPaymoney() + "");
        ratingBar= (RatingBar) findViewById(R.id.order_provider_RatingBar);
        int level=bean.getProviderJudgeLevel();
        switch (level){
            case 0:
                tvdefaultlv.setVisibility(View.VISIBLE);
                tvdefaultlv.setText("暂无评价");
                ratingBar.setVisibility(View.GONE);
                break;
            case 1:
                ratingBar.setNumStars(1);
                ratingBar.setVisibility(View.VISIBLE);
                break;
            case 2:
                ratingBar.setNumStars(2);
                ratingBar.setVisibility(View.VISIBLE);
                break;
            case 3:
                ratingBar.setNumStars(3);
                ratingBar.setVisibility(View.VISIBLE);
                break;
            case 4:
                ratingBar.setNumStars(4);
                ratingBar.setVisibility(View.VISIBLE);
                break;
            case 5:
                ratingBar.setNumStars(5);
                ratingBar.setVisibility(View.VISIBLE);
                break;
        }

    }
}
