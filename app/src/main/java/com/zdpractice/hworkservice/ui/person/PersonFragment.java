package com.zdpractice.hworkservice.ui.person;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.zdpractice.hworkservice.MyApplication;
import com.zdpractice.hworkservice.R;
import com.zdpractice.hworkservice.model.ChangePwdBean;
import com.zdpractice.hworkservice.model.PersonInfoBean;
import com.zdpractice.hworkservice.model.PersonInfoParentBean;
import com.zdpractice.hworkservice.support.networktool.NetWorkTools;
import com.zdpractice.hworkservice.ui.login.Login;

import org.xutils.common.Callback;

import java.text.SimpleDateFormat;


/**
 * Created by 15813 on 2016/8/15.
 */
public class PersonFragment extends Fragment {
    private static PersonFragment personfragment;
    private TextView tvfeedback,tvwallet,tvchangpwd,tvnotice,tvusername,tvorder,tvmoney,tvgood;
    private View view,lyOutlogin;
    private PersonInfoBean bean;

    public static PersonFragment newInstance() {
        if (personfragment==null){
            personfragment=new PersonFragment();
        }
        return personfragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.lay_person, container, false);
        //显示用户名
        tvusername= (TextView) view.findViewById(R.id.tvUsername);
        tvfeedback = (TextView)view.findViewById(R.id.tvFeedback);
        tvwallet = (TextView)view.findViewById(R.id.tvWallet);
        tvchangpwd = (TextView)view.findViewById(R.id.tvChangpwd);
        tvnotice = (TextView)view.findViewById(R.id.tvNotice);
        //本月订单
        tvorder= (TextView) view.findViewById(R.id.tv_month_order);
        //本月收益
        tvmoney= (TextView) view.findViewById(R.id.tv_month_money);
        //本月好评
        tvgood= (TextView) view.findViewById(R.id.tv_month_good);
        //退出登录linearlayout
        lyOutlogin=view.findViewById(R.id.lyOutlogin);
        //填入用户名
        tvusername.setText(MyApplication.userBean.getRealName());
        //获取基本信息
        getPersonInfo();
        //本月收益
        getMonthMoney();
        tvfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),FeedbackAcivity.class);
                startActivity(intent);

            }
        });
        tvwallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),BillActivity.class);
                startActivity(intent);

            }
        });
        tvchangpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ChangpwdActivity.class);
                startActivity(intent);

            }
        });
        tvnotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),NoticeActivity.class);
                startActivity(intent);

            }
        });
        //退出登录事件
        lyOutlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //把sharedpreference文件存储的用户名以及密码置为空，并跳转到登录Activity
                SharedPreferences sp=getActivity().getSharedPreferences("AllData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                editor.putString("用户名","");
                editor.putString("密码","");
                editor.commit();
                Intent intent=new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;

    }

    /**
     * 获取个人的信息
     */
    private void getPersonInfo(){
        //获取当前年月
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
        String month=sdf.format(new java.util.Date());
        NetWorkTools.newInstance().requestPersonalInfo(month, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                PersonInfoParentBean parentBean= JSON.parseObject(result,PersonInfoParentBean.class);
                bean=parentBean.getData().get(0);
                //填入本月订单
                tvorder.setText(bean.getFinishedOrderAmout());
                //填入本月好评数
                tvgood.setText(bean.getGoodJudgeAmount());

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
     * 获取月收入总额
     */
    private void getMonthMoney(){
        //获取当前年月
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
        String month=sdf.format(new java.util.Date());
        NetWorkTools.newInstance().requestMonthmoey(month, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ChangePwdBean bean=JSON.parseObject(result,ChangePwdBean.class);
                String MonthMoney=bean.getData();
                //填入本月收益
                tvmoney.setText(MonthMoney);
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
