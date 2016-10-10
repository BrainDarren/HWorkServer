package com.zdpractice.hworkservice;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.service.XGPushService;
import com.zdpractice.hworkservice.model.UploadBean;
import com.zdpractice.hworkservice.support.networktool.NetWorkTools;
import com.zdpractice.hworkservice.ui.orderinfo.HomeFragment;
import com.zdpractice.hworkservice.ui.orderinfo.OrderBaseFragment;
import com.zdpractice.hworkservice.ui.person.PersonFragment;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView ivHome,ivOrder,ivPersonal,toolbar_icon;
    private Toolbar toolbar;
    private TextView tvTitle;
    private View lyHome,lyOrder,lyPersonal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        init();

        //初始化Activity是应该进入首页
        ivHome.setImageResource(R.mipmap.icon_homeblue);
        ivOrder.setImageResource(R.mipmap.icon_ordergray);
        ivPersonal.setImageResource(R.mipmap.icon_myselfgray);
        getSupportFragmentManager().beginTransaction().replace(R.id.lay_base, HomeFragment.newInstance()).commit();

        lyHome.setOnClickListener(this);
        lyOrder.setOnClickListener(this);
        lyPersonal.setOnClickListener(this);

    }

    public void init(){
        tvTitle= (TextView) this.findViewById(R.id.tv_title);
        ivHome= (ImageView) this.findViewById(R.id.ivHome);
        ivOrder= (ImageView) this.findViewById(R.id.ivOrder);
        ivPersonal= (ImageView) this.findViewById(R.id.ivPersonal);
        lyHome= this.findViewById(R.id.main_home);
        lyOrder= this.findViewById(R.id.main_order);
        lyPersonal=this.findViewById(R.id.main_personal);
        toolbar= (Toolbar) this.findViewById(R.id.main_toolbar);
        toolbar_icon= (ImageView) this.findViewById(R.id.toolbar_icon);

        //加载toolbar的上下线menu按钮
        toolbar.inflateMenu(R.menu.toolbar_upload);
        toolbar.setOnMenuItemClickListener(new OnClickMenu());
        //获取头像
        getImage(toolbar_icon);
//---------------------------信鸽推送---------------------------------
//      开启logcat输出，方便debug，发布时请关闭
//        XGPushConfig.enableDebug(this, true);
//      如果需要知道注册是否成功，请使用registerPush(getApplicationContext(), XGIOperateCallback)带callback版本
//      如果需要绑定账号，请使用registerPush(getApplicationContext(),account)版本
//      具体可参考详细的开发指南
//      传递的参数为ApplicationContext
//        Context context = getApplicationContext();
//        // 注册接口
//        XGPushManager.registerPush(getApplicationContext(),MyApplication.userBean.getUserid()+"",
//                new XGIOperateCallback() {
//                    @Override
//                    public void onSuccess(Object data, int flag) {
//                        Log.w(Constants.LogTag,
//                                "+++ register push sucess. token:" + data);
//                    }
//
//                    @Override
//                    public void onFail(Object data, int errCode, String msg) {
//                        Log.w(Constants.LogTag,
//                                "+++ register push fail. token:" + data
//                                        + ", errCode:" + errCode + ",msg:"
//                                        + msg);
//                    }
//                });
        // 2.36（不包括）之前的版本需要调用以下2行代码
//        Intent service = new Intent(context, XGPushService.class);
//        context.startService(service);
        // 其它常用的API：
        // 绑定账号（别名）注册：registerPush(context,account)或registerPush(context,account, XGIOperateCallback)，其中account为APP账号，可以为任意字符串（qq、openid或任意第三方），业务方一定要注意终端与后台保持一致。
        // 取消绑定账号（别名）：registerPush(context,"*")，即account="*"为取消绑定，解绑后，该针对该账号的推送将失效
        // 反注册（不再接收消息）：unregisterPush(context)
        // 设置标签：setTag(context, tagName)
        // 删除标签：deleteTag(context, tagName)
//--------------------------------------------------------------------
    }

    private void getImage(ImageView imageView) {

        ImageOptions imageOptions = new ImageOptions.Builder()
                .setSize(DensityUtil.dip2px(40), DensityUtil.dip2px(40))
                .setRadius(DensityUtil.dip2px(50))
//              如果ImageView的大小不是定义为wrap_content, 不要crop.
//              .setCrop(true)
//              很多时候设置了合适的scaleType也不需要它.
//              加载中或错误图片的ScaleType
                .setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.mipmap.ic_launcher)
                .setFailureDrawableId(R.mipmap.ic_launcher)
                .build();
//      imageView.setImageResource(R.mipmap.ic_launcher);
        x.image().bind(imageView, MyApplication.userBean.getIcon(),imageOptions);
    }

    /**
     * Navigation 栏的点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.main_home:
                tvTitle.setText("首页");
                ivHome.setImageResource(R.mipmap.icon_homeblue);
                ivOrder.setImageResource(R.mipmap.icon_ordergray);
                ivPersonal.setImageResource(R.mipmap.icon_myselfgray);
                getSupportFragmentManager().beginTransaction().replace(R.id.lay_base, HomeFragment.newInstance()).commit();
                break;
            case R.id.main_order:
                tvTitle.setText("订单");
                ivOrder.setImageResource(R.mipmap.icon_order);
                ivHome.setImageResource(R.mipmap.icon_homegray);
                ivPersonal.setImageResource(R.mipmap.icon_myselfgray);
                getSupportFragmentManager().beginTransaction().replace(R.id.lay_base, OrderBaseFragment.newInstance()).commit();
                break;
            case R.id.main_personal:
                tvTitle.setText("我的");
                ivPersonal.setImageResource(R.mipmap.icon_myselfblue);
                ivHome.setImageResource(R.mipmap.icon_homegray);
                ivOrder.setImageResource(R.mipmap.icon_ordergray);
                getSupportFragmentManager().beginTransaction().replace(R.id.lay_base, PersonFragment.newInstance()).commit();
                break;
        }
    }

    /**
     * Toolbar 的Menu点击事件
     */
    class OnClickMenu implements Toolbar.OnMenuItemClickListener{

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            NetWorkTools netWorkTools=NetWorkTools.newInstance();
            String longitude="";
            String latitude="";
            switch (item.getItemId()){
                case R.id.toolbar_online:
                    toolbar.getMenu().clear();
                    toolbar.inflateMenu(R.menu.toolbar_offline);
                    // 注册接口
                    XGPushManager.registerPush(getApplicationContext(),MyApplication.userBean.getUserid()+"",
                            new XGIOperateCallback() {
                                @Override
                                public void onSuccess(Object data, int flag) {
                                    Log.w(Constants.LogTag,
                                            "+++ register push sucess. token:" + data);
                                }

                                @Override
                                public void onFail(Object data, int errCode, String msg) {
                                    Log.w(Constants.LogTag,
                                            "+++ register push fail. token:" + data
                                                    + ", errCode:" + errCode + ",msg:"
                                                    + msg);
                                }
                            });
                    //在子线程中开启定位服务,给location实体类赋值
                    netWorkTools.locationtool(true);
                    break;
                case R.id.toolbar_offline:
                    toolbar.getMenu().clear();
                    toolbar.inflateMenu(R.menu.toolbar_upload);
                    // 注册接口
                    XGPushManager.unregisterPush(getApplicationContext());
                    //开启定位服务,给location实体类赋值
                    netWorkTools.locationtool(false);
                    break;

            }
            return false;
        }


    }
}
