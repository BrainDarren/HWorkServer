package com.zdpractice.hworkservice.ui.orderinfo;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zdpractice.hworkservice.MyApplication;
import com.zdpractice.hworkservice.R;
import com.zdpractice.hworkservice.model.NoticeParentBean;
import com.zdpractice.hworkservice.model.OrderBean;
import com.zdpractice.hworkservice.model.OrderParentBean;
import com.zdpractice.hworkservice.support.MAPtool.RoutePlanActivity;
import com.zdpractice.hworkservice.support.networktool.NetWorkTools;
import org.xutils.common.Callback;
import java.util.ArrayList;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private ArrayList<Fragment> fragmentArrayList;
    private View view,newnotice;
    private static HomeFragment homeFragment;
    private ImageView iv_Contact,iv_Navigation;
    private ArrayList<OrderBean> list=null;
    private TextView tv_Class,tv_Time,tv_Address,newnotice_title;


    public static HomeFragment newInstance(){
        if (homeFragment==null){
            homeFragment=new HomeFragment();
        }
        return homeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list=new ArrayList<OrderBean>();
        //加载数据
        downLoadData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.home_base,container,false);
        //联系雇主iv
        iv_Contact= (ImageView) view.findViewById(R.id.home_ivcontact);
        iv_Navigation= (ImageView) view.findViewById(R.id.home_ivnavigation);

        //服务类别 tv
        tv_Class= (TextView) view.findViewById(R.id.order_waitservice_class);
        tv_Address= (TextView) view.findViewById(R.id.order_waitservice_address);
        tv_Time= (TextView) view.findViewById(R.id.order_waitservice_time);
        //最新通知Layout
        newnotice=view.findViewById(R.id.home_newnotice);
        //最新通知标题
        newnotice_title= (TextView) view.findViewById(R.id.newnotice_title);
        //给最新通知标题赋值
        downLoadNewNotice();
        newnotice.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent=null;
        switch (view.getId()){
            //打电话联系雇主
            case R.id.home_ivcontact:
                intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                //url:统一资源定位符
                //uri:统一资源标示符（更广）
                intent.setData(Uri.parse("tel:" + this.list.get(0).getContactMobile().toString()));
                //开启系统拨号器
                startActivity(intent);
                break;
            //最新通知layout
            case R.id.home_newnotice:
                //跳转到通知Activity
//                intent=new Intent(getActivity(), NoticeActivity.class);
//                startActivity(intent);
                break;
            //TODO 导航
            case R.id.home_ivnavigation:
                //判断定位服务是否开启 添加导航方法
                if(MyApplication.locationBean.getLatitude()!=null && MyApplication.locationBean.getLontitude()!=null){
                    intent=new Intent(getActivity(),RoutePlanActivity.class);
                    intent.putExtra("bean",list.get(0));
                    startActivity(intent);
                    break;
                }else {
                    Toast.makeText(getActivity(),"请切换为在线状态",Toast.LENGTH_SHORT).show();
                }
        }
    }

    /**
     * 从网络获取订单信息
     */
    public void downLoadData() {
        NetWorkTools netWorkTools = NetWorkTools.newInstance();
        netWorkTools.requestNextOrder(MyApplication.userBean.getToken(), MyApplication.userBean.getUserid() + "", new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                OrderParentBean orderParentBean = JSON.parseObject(result, OrderParentBean.class);
                if(orderParentBean.getData()==null){
                    LinearLayout linearLayout= (LinearLayout) view.findViewById(R.id.home_next_order);
                    linearLayout.setVisibility(View.GONE);
                    LinearLayout promptLayout= (LinearLayout) view.findViewById(R.id.home_prompt_layout);
                    promptLayout.setVisibility(View.VISIBLE);
                }else {
                    list.addAll(orderParentBean.getData());
                    LinearLayout linearLayout= (LinearLayout) view.findViewById(R.id.home_next_order);
                    linearLayout.setVisibility(View.VISIBLE);
                    LinearLayout promptLayout= (LinearLayout) view.findViewById(R.id.home_prompt_layout);
                    promptLayout.setVisibility(View.GONE);
                    tv_Class.setText("日常保洁");
                    tv_Time.setText(list.get(0).getOrderAgreedTime().toString());
                    tv_Address.setText(list.get(0).getAddressArea().toString());
                    iv_Contact.setOnClickListener(HomeFragment.this);
                    iv_Navigation.setOnClickListener(HomeFragment.this);
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


    /**
     * 从网络获取最新通知
     */
    public void downLoadNewNotice(){
        NetWorkTools netWorkTools=NetWorkTools.newInstance();
        netWorkTools.requestNewSysNoticeInfo(MyApplication.userBean.getToken(), MyApplication.userBean.getUserid()+"", new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                NoticeParentBean noticeParentBean=JSON.parseObject(result,NoticeParentBean.class);
                String title=noticeParentBean.getData().get(0).getTitle();
                newnotice_title.setText(title);

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
