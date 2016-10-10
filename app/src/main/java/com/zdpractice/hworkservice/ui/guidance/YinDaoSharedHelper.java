package com.zdpractice.hworkservice.ui.guidance;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by syz on 2016/8/24.
 */

public class YinDaoSharedHelper {
    private Context mContext;


    public YinDaoSharedHelper(Context mContext) {
        this.mContext = mContext;
    }


    //定义一个保存数据的方法
    public void save(String n) {
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("cishu",n);
        editor.commit();
    }

    //定义一个读取SP文件的方法
    public String read() {
        String data;
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        data=sp.getString("cishu","");
        return data;
    }
}
