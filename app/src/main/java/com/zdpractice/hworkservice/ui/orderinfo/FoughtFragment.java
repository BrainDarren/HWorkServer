package com.zdpractice.hworkservice.ui.orderinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.zdpractice.hworkservice.MyApplication;
import com.zdpractice.hworkservice.R;
import com.zdpractice.hworkservice.model.OrderBean;
import com.zdpractice.hworkservice.model.OrderParentBean;
import com.zdpractice.hworkservice.support.adapter.OrderInfoRVAdapter;
import com.zdpractice.hworkservice.support.networktool.NetWorkTools;

import org.xutils.common.Callback;

import java.util.ArrayList;

/**
 * Created by 15813 on 2016/8/17.
 */
public class FoughtFragment extends Fragment {

    private View view;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<OrderBean> list=new ArrayList<OrderBean>();
    //处于竞单中状态的订单列表的RecycleView
    private RecyclerView rvFoughtOrder;
    private OrderInfoRVAdapter orderInfoRVAdapter;
    private static final String TAG_STATUES="statues";
    /**
     * 订单状态标识符
     */
    private String statues;
    private boolean isLoading=false;

    public static FoughtFragment newInstance(String statues){
        Bundle args=new Bundle();
        args.putString(TAG_STATUES,statues);
        FoughtFragment foughtFragment=new FoughtFragment();
        foughtFragment.setArguments(args);
        return foughtFragment;
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
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.competeorder_srl);
        rvFoughtOrder= (RecyclerView) view.findViewById(R.id.rvFoughtOrder);
        //TODO 要把联网获取的订单数据传递到适配器
        list.clear();
        downLoadData();
        final LinearLayoutManager mLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rvFoughtOrder.setLayoutManager(mLayoutManager);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                downLoadData();
            }
        });

        rvFoughtOrder.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //当recycleview滑动到底部的时候自动加载更多
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = mLayoutManager.getItemCount();
                //lastVisibleItem >= totalItemCount - 4 表示剩下4个item自动加载，各位自由选择
                // dy>0 表示向下滑动
                boolean isRefreshing = swipeRefreshLayout.isRefreshing();
                if (isRefreshing) {
                    orderInfoRVAdapter.notifyItemRemoved(orderInfoRVAdapter.getItemCount());
                    return;
                }

                if (lastVisibleItem+1 == totalItemCount  && dy > 0) {
                    if (!isLoading) {
                        isLoading = true;
                        downLoadData();
                    }
                }
            }
        });
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
                if (orderParentBean.getData()==null){
                    orderInfoRVAdapter.notifyItemRemoved(orderInfoRVAdapter.getItemCount());
                }else {
                    list.addAll(orderParentBean.getData());
                    orderInfoRVAdapter=new OrderInfoRVAdapter(getActivity(),list,"2");
                    rvFoughtOrder.setAdapter(orderInfoRVAdapter);
                    swipeRefreshLayout.setRefreshing(false);
                    isLoading=false;
                    orderInfoRVAdapter.notifyItemRemoved(orderInfoRVAdapter.getItemCount());
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
