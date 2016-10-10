package com.zdpractice.hworkservice.support.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zdpractice.hworkservice.ui.competeorder.CompeteFragment;

import java.util.ArrayList;

/**
 * Created by 15813 on 2016/8/17.
 */
public class HomeVPAdapter extends FragmentPagerAdapter{

    private ArrayList<Fragment> fragmentArrayList;

    public HomeVPAdapter(FragmentManager fm,ArrayList<Fragment> fragmentArrayList) {
        super(fm);
        this.fragmentArrayList=fragmentArrayList;
    }

    @Override
    public Fragment getItem(int position) {
//        return new CompeteFragment();
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }



}
