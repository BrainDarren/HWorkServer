package com.zdpractice.hworkservice.ui.orderinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
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
 * Created by 15813 on 2016/8/18.
 */
public class OrderEverFragment extends Fragment{

    private View view;
    private RecyclerView rvFoughtOrder;
    private OrderInfoRVAdapter orderInfoRVAdapter;
    private static final String TAG_STATUES="statues";
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<OrderBean> list=new ArrayList<OrderBean>();
    /**
     * 订单状态标识符
     */
    private String statues;
    private boolean isLoading=false;
    private int startPage=1;
    private int lastVisibleItem;

    public static OrderEverFragment newInstance(String statues){
        Bundle args=new Bundle();
        args.putString(TAG_STATUES,statues);
        OrderEverFragment orderEverFragment=new OrderEverFragment();
        orderEverFragment.setArguments(args);
        return orderEverFragment;
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
        final LinearLayoutManager mLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rvFoughtOrder.setLayoutManager(mLayoutManager);
        //TODO 要把联网获取的订单数据传递到适配器
        list.clear();
        downLoadData("1");
        //设置刷新监听事件
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.competeorder_srl);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                startPage=1;
                downLoadData(startPage+"");
            }
        });


        //设置RecycleView滑动监听事件
        rvFoughtOrder.addOnScrollListener(new RecyclerView.OnScrollListener(){

            //当滑动状态发生变化
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //如果想实现手指向上滑动再触发加载事件，如下
                /*if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mLayoutManager.getItemCount()) {
                    Log.d("test", "loading executed");

                    boolean isRefreshing = swipeRefreshLayout.isRefreshing();
                    if (isRefreshing) {
                        orderInfoRVAdapter.notifyItemRemoved(orderInfoRVAdapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        downLoadData(++startPage+"");
                    }
                }*/

            }

            //当滑动时
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //当recycleview滑动到底部的时候自动加载更多
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
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
                        downLoadData(++startPage+"");
                    }
                }
            }
        });
        return view;
    }


    /**
     * 从网络获取订单信息
     */
    public void downLoadData(String page){
        Log.v("******",page);
        NetWorkTools netWorkTools=NetWorkTools.newInstance();
        String token=MyApplication.userBean.getToken();
        String userid=MyApplication.userBean.getUserid()+"";
        if(Integer.parseInt(page)<6){
            netWorkTools.requestEverOrder(token,userid,page,"10",null,statues,null,null, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    OrderParentBean orderParentBean= JSON.parseObject(result,OrderParentBean.class);
                    if(orderParentBean.getData()==null){
                        Toast.makeText(getActivity(),"没有数据",Toast.LENGTH_LONG).show();
                    }else {
                        list.addAll(orderParentBean.getData());
                        orderInfoRVAdapter=new OrderInfoRVAdapter(getActivity(),list,"6");
                        rvFoughtOrder.setAdapter(orderInfoRVAdapter);
                        swipeRefreshLayout.setRefreshing(false);
                        isLoading=false;
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
        }else {
            Toast.makeText(getActivity(),"不能显示更多",Toast.LENGTH_SHORT).show();
        }

    }

}
