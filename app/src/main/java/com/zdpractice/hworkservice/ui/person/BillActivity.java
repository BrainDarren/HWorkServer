package com.zdpractice.hworkservice.ui.person;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zdpractice.hworkservice.MyApplication;
import com.zdpractice.hworkservice.R;
import com.zdpractice.hworkservice.model.ChangePwdBean;
import com.zdpractice.hworkservice.model.OrderBean;
import com.zdpractice.hworkservice.model.OrderParentBean;
import com.zdpractice.hworkservice.support.networktool.NetWorkTools;

import org.xutils.common.Callback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Administrator on 2016/9/12.
 */

public class BillActivity extends AppCompatActivity {
    private ListView lv;
    private AnimalAdapter adapter;
    private OrderParentBean orderParentBean;
    private ArrayList<OrderBean> orderBeens;
    private Toolbar tbgoback;
    private SwipeRefreshLayout srl;
    private String date;
    private ImageView img_icon;
    private TextView tvTotal;
    private ChangePwdBean changePwdBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.bill_list);
        srl = (SwipeRefreshLayout) findViewById(R.id.srl_bill);
        tbgoback = (Toolbar) findViewById(R.id.toolbar_bill);
        img_icon = (ImageView) findViewById(R.id.toolbar_icon_bill);
        //点导航栏图标退出
        img_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tvTotal = (TextView) findViewById(R.id.Total_monthly_income);
        tbgoback.inflateMenu(R.menu.bill_toolbar_menu_date);
        lv = (ListView) findViewById(R.id.bill_ListView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //获取当前年月
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        date = sdf.format(new java.util.Date());
        //下拉刷新监听事件
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NetWorkTools.newInstance().requestEverOrder
                        (MyApplication.userBean.getToken(), MyApplication.userBean.getUserid() + "", "1",
                                "20", null, "6", date, null, new Callback.CommonCallback<String>() {
                                    @Override
                                    public void onSuccess(String result) {
                                        orderParentBean = JSON.parseObject(result, OrderParentBean.class);
                                        orderBeens = orderParentBean.getData();
                                        adapter = new AnimalAdapter(getApplicationContext(), orderBeens);
                                        lv.setAdapter(adapter);
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
                NetWorkTools.newInstance().requestMonthmoey(date, new Callback.CacheCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        changePwdBean = JSON.parseObject(result, ChangePwdBean.class);
                        tvTotal.setText(changePwdBean.getData() + "元");
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

                    @Override
                    public boolean onCache(String result) {
                        return false;
                    }
                });
                //关闭动画
                srl.setRefreshing(false);
            }
        });
        //选择月份查询对应菜单事件
        tbgoback.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            String results = "";

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Calendar cale1 = Calendar.getInstance();
                DatePickerDialog picker = new DatePickerDialog(BillActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        //这里获取到的月份需要加上1哦~
                        int month = monthOfYear + 1;
                        if (month < 10) {
                            results = year + "-0" + month;
                        } else {
                            results = year + "-" + month;
                        }
                        NetWorkTools.newInstance().requestEverOrder
                                (MyApplication.userBean.getToken(), MyApplication.userBean.getUserid() + "", "1",
                                        "20", null, "6", results, null, new Callback.CommonCallback<String>() {

                                            @Override
                                            public void onSuccess(String result) {
                                                orderParentBean = JSON.parseObject(result, OrderParentBean.class);
                                                orderBeens = orderParentBean.getData();
                                                if (orderBeens.size() == 0) {
                                                    adapter = new AnimalAdapter(getApplicationContext(), orderBeens);
                                                    lv.setAdapter(adapter);
                                                    Toast.makeText(getApplicationContext(), results + "没有完成订单!", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    adapter = new AnimalAdapter(getApplicationContext(), orderBeens);
                                                    lv.setAdapter(adapter);
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
                        NetWorkTools.newInstance().requestMonthmoey(results, new Callback.CacheCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                changePwdBean = JSON.parseObject(result, ChangePwdBean.class);
                                tvTotal.setText(changePwdBean.getData()+"元");
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

                            @Override
                            public boolean onCache(String result) {
                                return false;
                            }
                        });
                    }
                }
                        , cale1.get(Calendar.YEAR)
                        , cale1.get(Calendar.MONTH)
                        , cale1.get(Calendar.DAY_OF_MONTH));
                picker.setCancelable(false);
                picker.show();
                return true;
            }
        });
    }

    public class AnimalAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<OrderBean> orderBeen;

        public AnimalAdapter() {
            super();
        }

        public AnimalAdapter(Context context, ArrayList<OrderBean> orderBeen) {
            this.context = context;
            this.orderBeen = orderBeen;
        }

        @Override
        public int getCount() {
            return orderBeen.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(context).inflate(R.layout.order_history_list_content, viewGroup, false);
            TextView tvIndent_serviceclass = (TextView) view.findViewById(R.id.indent_serviceclass);
            TextView tvIndent_creationDate = (TextView) view.findViewById(R.id.indent_creationDate);
            TextView tvIndent_addressArea = (TextView) view.findViewById(R.id.indent_addressArea);
            TextView tvIndent_servicestate = (TextView) view.findViewById(R.id.indent_servicestate);
            tvIndent_serviceclass.setText(orderBeen.get(i).getServiceclass());
            tvIndent_serviceclass.setTextColor(Color.rgb(0, 0, 0));
            tvIndent_creationDate.setText(orderBeen.get(i).getCreationDate());
            tvIndent_creationDate.setTextColor(Color.rgb(0, 0, 0));
            tvIndent_addressArea.setText(orderBeen.get(i).getAddressArea());
            tvIndent_addressArea.setTextColor(Color.rgb(0, 0, 0));
            tvIndent_servicestate.setTextColor(Color.rgb(255, 0, 0));
            tvIndent_servicestate.setText(orderBeen.get(i).getPaymoney() + "元");
            return view;
        }
    }
}
