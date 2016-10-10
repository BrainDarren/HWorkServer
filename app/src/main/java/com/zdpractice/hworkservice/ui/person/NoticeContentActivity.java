package com.zdpractice.hworkservice.ui.person;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.zdpractice.hworkservice.MyApplication;
import com.zdpractice.hworkservice.R;
import com.zdpractice.hworkservice.model.NoticeBean;
import com.zdpractice.hworkservice.support.networktool.NetWorkTools;

public class NoticeContentActivity extends Activity {

    private WebView wv ;
    private NoticeBean result1;
    private ImageView comeback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_content1);
        comeback = (ImageView) findViewById(R.id.comeback);
        //返回按钮的点击事件
        comeback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //加载webview控件并得到
        wv= (WebView) findViewById(R.id.wv_NoticeContent12);

        Intent intent=getIntent();
        result1=new NoticeBean();
        result1= (NoticeBean) intent.getSerializableExtra("NoticeContent");
        //webview加载html代码
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient());

        wv.loadUrl(NetWorkTools.url+ "/notice/getNoticeInfo" +
                "?noticeid=" + result1.getNoticeid()+"&token="+ (MyApplication.userBean.getToken()));

    }

}
