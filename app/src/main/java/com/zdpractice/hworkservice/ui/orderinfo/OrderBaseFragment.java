package com.zdpractice.hworkservice.ui.orderinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zdpractice.hworkservice.R;
import com.zdpractice.hworkservice.support.adapter.OrderInfoVPAdapter;
import com.zdpractice.hworkservice.ui.competeorder.CompeteFragment;

import java.util.ArrayList;

/**
 * Created by 15813 on 2016/8/18.
 */
public class OrderBaseFragment extends Fragment {

    private View view;
    private TabLayout tl_Orderinfo;
    private ViewPager vp_Orderinfo;
    private static OrderBaseFragment orderBaseFragment;
    private CompeteFragment competeFragment;
    private OrderEverFragment orderEverFragment;
    private FoughtFragment foughtFragment;
    private WaitforpayFragment waitforpayFragment;
    private ArrayList<Fragment> fragmentArrayList;

    public static OrderBaseFragment newInstance(){
        if (orderBaseFragment==null){
            orderBaseFragment=new OrderBaseFragment();
        }
        return orderBaseFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.orderinfo_base,container,false);
        tl_Orderinfo= (TabLayout) view.findViewById(R.id.tl_Orderinfo);
        vp_Orderinfo= (ViewPager) view.findViewById(R.id.vp_Orderinfo);

        competeFragment=CompeteFragment.newInstance("1");//竞单订单fragment
        foughtFragment=FoughtFragment.newInstance("2");//处于待服务状态的订单fragment
        waitforpayFragment=WaitforpayFragment.newInstance("5");//处于待支付状态的订单fragment
        orderEverFragment=OrderEverFragment.newInstance("6");//历史订单fragment

        //加入数组
        fragmentArrayList=new ArrayList<Fragment>();
        fragmentArrayList.add(competeFragment);
        fragmentArrayList.add(foughtFragment);
        fragmentArrayList.add(waitforpayFragment);
        fragmentArrayList.add(orderEverFragment);

        vp_Orderinfo.setAdapter(new OrderInfoVPAdapter(getChildFragmentManager(),fragmentArrayList));
        tl_Orderinfo.setupWithViewPager(vp_Orderinfo);
        tl_Orderinfo.setTabMode(TabLayout.MODE_FIXED);
        tl_Orderinfo.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorTableLayoutSelect));
        return view;
    }

}
