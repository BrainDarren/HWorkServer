package com.zdpractice.hworkservice.ui.person;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.zdpractice.hworkservice.MyApplication;
import com.zdpractice.hworkservice.R;
import com.zdpractice.hworkservice.model.ChangePwdBean;
import com.zdpractice.hworkservice.model.NoticeBean;
import com.zdpractice.hworkservice.model.NoticeParentBean;
import com.zdpractice.hworkservice.support.networktool.NetWorkTools;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YL on 2016/8/18.
 */
public class NoticeActivity extends Activity {
    private NoticeParentBean bean1;
    private ImageView comeback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_notice);


        NetWorkTools tools = new NetWorkTools();
        final ListView lv_notice = (ListView) this.findViewById(R.id.person_notice_lv);
        final noticeAdapter adapter = new noticeAdapter();

        tools.requestNoticeList( MyApplication.userBean.getToken(),MyApplication.userBean.getUserid()+"","1","10", new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                final NoticeParentBean bean = JSON.parseObject(result, NoticeParentBean.class);
                List<NoticeBean> ls = new ArrayList<NoticeBean>();

            ls.addAll(bean.getData());
            adapter.getdata(ls);
            lv_notice.setAdapter(adapter);
            lv_notice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Intent intent = new Intent(NoticeActivity.this, NoticeContentActivity.class);
                    intent.putExtra("NoticeContent",bean.getData().get(i));
                    startActivity(intent);
                }
            });
                comeback = (ImageView) findViewById(R.id.comeback1);
                comeback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });
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


    class noticeAdapter extends BaseAdapter {
        List<NoticeBean> bean;

        public void getdata(List<NoticeBean> bean) {
            this.bean = bean;
        }

        @Override
        public int getCount() {
            return bean.size() ;
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
            view = getLayoutInflater().inflate(R.layout.person_notice_lv, null);
            TextView title = (TextView) view.findViewById(R.id.tvTitle);
            TextView pushtime = (TextView) view.findViewById(R.id.tvPushtime);
            title.setText(bean.get(i).getTitle());
            pushtime.setText(bean.get(i).getPushtime());
            return view;
        }
    }

}
