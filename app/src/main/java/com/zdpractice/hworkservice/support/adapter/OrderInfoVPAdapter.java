package com.zdpractice.hworkservice.support.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;

/**
 * 通过ViewPagerAdapter添加Fragment
 * Created by 15813 on 2016/8/18.
 */
public class OrderInfoVPAdapter extends FragmentPagerAdapter {
    //TODO 可以同意使用一个Fragment来承载订单管理中所有选项卡的RecycleView
    String[] title={"拍单","服务","待付","历史"};
    private ArrayList<Fragment> fragmentArrayList;

    public OrderInfoVPAdapter(FragmentManager fm,ArrayList<Fragment> fragmentArrayList) {
        super(fm);
        this.fragmentArrayList=fragmentArrayList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
