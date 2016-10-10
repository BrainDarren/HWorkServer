package com.zdpractice.hworkservice.ui.orderinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.zdpractice.hworkservice.MyApplication;
import com.zdpractice.hworkservice.R;
import com.zdpractice.hworkservice.model.OrderParentBean;
import com.zdpractice.hworkservice.support.adapter.OrderInfoRVAdapter;
import com.zdpractice.hworkservice.support.networktool.NetWorkTools;

import org.xutils.common.Callback;

/**
 * Created by 15813 on 2016/8/29.
 */
public class WaitforpayFragment extends Fragment {

    private View view;
    private RecyclerView rvFoughtOrder;
    private OrderInfoRVAdapter orderInfoRVAdapter;
    private static final String TAG_STATUES="statues";
    /**
     * 订单状态标识符
     */
    private String statues;


    public static WaitforpayFragment newInstance(String statues){
        Bundle args=new Bundle();
        args.putString(TAG_STATUES,statues);
        WaitforpayFragment waitforpayFragment=new WaitforpayFragment();
        waitforpayFragment.setArguments(args);
        return waitforpayFragment;
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
        view=inflater.inflate(R.layout.competeorder_fought,container,false);
        rvFoughtOrder= (RecyclerView) view.findViewById(R.id.rvFoughtOrder);
        //TODO 要把联网获取的订单数据传递到适配器
        downLoadData();
        rvFoughtOrder.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        return view;
    }

    /**
     * 从网络获取订单信息
     */
    public void downLoadData(){
        NetWorkTools netWorkTools=NetWorkTools.newInstance();
        netWorkTools.requestEverOrder(MyApplication.userBean.getToken(), MyApplication.userBean.getUserid()+"","1","10",null,statues,null,null, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                OrderParentBean orderParentBean= JSON.parseObject(result,OrderParentBean.class);
                orderInfoRVAdapter=new OrderInfoRVAdapter(getActivity(),orderParentBean.getData(),"5");
                rvFoughtOrder.setAdapter(orderInfoRVAdapter);
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
