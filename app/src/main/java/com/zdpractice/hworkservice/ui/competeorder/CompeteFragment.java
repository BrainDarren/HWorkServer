package com.zdpractice.hworkservice.ui.competeorder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.zdpractice.hworkservice.MyApplication;
import com.zdpractice.hworkservice.R;
import com.zdpractice.hworkservice.model.OrderBean;
import com.zdpractice.hworkservice.model.OrderParentBean;
import com.zdpractice.hworkservice.support.adapter.CompeteOrderRVAdapter;
import com.zdpractice.hworkservice.support.adapter.HomeRVAdapter;
import com.zdpractice.hworkservice.support.adapter.OrderInfoRVAdapter;
import com.zdpractice.hworkservice.support.networktool.NetWorkTools;

import org.xutils.common.Callback;

import java.util.ArrayList;

/**
 * Created by 15813 on 2016/8/17.
 */
public class CompeteFragment extends Fragment{

    //显示订单信息的Fragment
    private View view;
    //查看订单信息的RecycleView
    private RecyclerView rvCompeteOrder;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String TAG_STATUES="statues";
    private ArrayList<OrderBean> list=new ArrayList<OrderBean>();
    /**
     * 订单状态标识符
     */
    private String statues;

    public static CompeteFragment newInstance(String statues){
        Bundle args=new Bundle();
        args.putString(TAG_STATUES,statues);
        CompeteFragment competeFragment=new CompeteFragment();
        competeFragment.setArguments(args);
        return competeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            this.statues=getArguments().getString(TAG_STATUES);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.competeorder_show,container,false);
        rvCompeteOrder= (RecyclerView) view.findViewById(R.id.rvCompeteOrder);
        rvCompeteOrder.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        //TODO SwipeRefreshLayout
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.sfl_CompeteOrder);
        //刷新事件
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                downLoadData();
            }
        });
        //TODO 要把联网获取的订单数据传递到适配器
        downLoadData();
        return view;
    }

    /**
     * 从网络获取订单信息
     */
    public void downLoadData(){
        NetWorkTools netWorkTools=NetWorkTools.newInstance();
        netWorkTools.requestCompeteOrder(MyApplication.userBean.getToken(), MyApplication.userBean.getUserid()+"",new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //每次调用时要首先清除数据
                list.clear();
                if (MyApplication.locationBean.getLatitude()!=null){
                    OrderParentBean orderParentBean=JSON.parseObject(result,OrderParentBean.class);
                    list.addAll(orderParentBean.getData());
                    rvCompeteOrder.setAdapter(new CompeteOrderRVAdapter(getActivity(),list));
                    swipeRefreshLayout.setRefreshing(false);
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
}
