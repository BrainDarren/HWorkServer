package com.zdpractice.hworkservice.support.MAPtool;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteLine;
import com.baidu.mapapi.search.route.BikingRoutePlanOption;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.zdpractice.hworkservice.MyApplication;
import com.zdpractice.hworkservice.R;
import com.zdpractice.hworkservice.model.OrderBean;

import java.util.ArrayList;
import java.util.List;



/**
 * 此demo用来展示如何进行驾车、步行、公交路线搜索并在地图使用RouteOverlay、TransitOverlay绘制
 * 同时展示如何进行节点浏览并弹出泡泡
 */
public class RoutePlanActivity extends Activity implements BaiduMap.OnMapClickListener, OnGetRoutePlanResultListener, View.OnClickListener {
    // 浏览路线节点相关

    // 浏览路线节点相关
    int nodeIndex = -1; // 节点索引,供浏览节点时使用
    RouteLine route = null;
    OverlayManager routeOverlay = null;
    boolean useDefaultIcon = false;
    private TextView popupText = null; // 泡泡view
    private mapbean b;

    // 地图相关，使用继承MapView的MyRouteMapView目的是重写touch事件实现泡泡处理
    // 如果不处理touch事件，则无需继承，直接使用MapView即可
    MapView mMapView = null;    // 地图View
    BaiduMap mBaidumap = null;
    // 搜索相关
    RoutePlanSearch mSearch = null;    // 搜索模块，也可去掉地图模块独立使用

    TransitRouteResult nowResult = null;
    DrivingRouteResult nowResultd  = null;
    LinearLayout map_ll;
    ImageView map_up,map_drown;
     private  ListView map_lv;
    private sAdapter  adapter;
    private List<mapbean> list;
    private Toolbar map_toolbar;
    private LinearLayout walk, transit, bike, oo, is_walk, is_transit, is_bike;
    boolean isrung = false;
    private TextView way, message,i_bike,i_walk,i_transit;
    int ii;
    PlanNode stNode;
    PlanNode enNode;
    boolean falg=false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routeplan);
        CharSequence titleLable = "路线规划功能";
        setTitle(titleLable);

        is_bike = (LinearLayout) findViewById(R.id.map_bike);
        is_transit = (LinearLayout) findViewById(R.id.map_transit);
        is_walk = (LinearLayout) findViewById(R.id.map_walk);

        i_bike = (TextView) findViewById(R.id.i_bike);
        i_transit = (TextView) findViewById(R.id.i_transit);
        i_walk = (TextView) findViewById(R.id.i_walk);

        // 初始化地图
        mMapView = (MapView) findViewById(R.id.map);
        mBaidumap = mMapView.getMap();
//        mBtnPre = (Button) findViewById(R.id.pre);
//        mBtnNext = (Button) findViewById(R.id.next);
//        mBtnPre.setVisibility(View.INVISIBLE);
//        mBtnNext.setVisibility(View.INVISIBLE);
        //接受首届传过来的订单bean
        Intent intent=getIntent();
        OrderBean bean = (OrderBean) intent.getSerializableExtra("bean");
        // 地图点击事件处理
        stNode = PlanNode.withLocation(new LatLng(Double.parseDouble(MyApplication.locationBean.getLatitude()),Double.parseDouble(MyApplication.locationBean.getLontitude())));
        enNode = PlanNode.withLocation(new LatLng(Double.parseDouble(bean.getBaiduMapLat()),Double.parseDouble(bean.getBaiduMapLng())));
        map_toolbar= (Toolbar) findViewById(R.id.map_toolbar);
        mBaidumap.setOnMapClickListener(this);
        // 初始化搜索模块，注册事件监听
        mSearch = RoutePlanSearch.newInstance();
        oo= (LinearLayout) findViewById(R.id.oo);
        mSearch.setOnGetRoutePlanResultListener(this);
        walk= (LinearLayout) findViewById(R.id.map_walk);
        walk.setOnClickListener(this);
        transit= (LinearLayout) findViewById(R.id.map_transit);
        transit.setOnClickListener(this);
        way= (TextView) findViewById(R.id.way);
        message= (TextView) findViewById(R.id.message);
        bike= (LinearLayout) findViewById(R.id.map_bike);
        bike.setOnClickListener(this);
        map_ll= (LinearLayout) findViewById(R.id.map_lL1);
        map_up= (ImageView) findViewById(R.id.up);
        map_up.setOnClickListener(this);
        map_drown= (ImageView) findViewById(R.id.drown);
        map_drown.setOnClickListener(this);
        map_lv= (ListView) findViewById(R.id.iiiii);
        map_toolbar.setTitle("路线");
        map_toolbar.setNavigationIcon(R.mipmap.map_back);
        map_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

        //默认方式
        mSearch.bikingSearch((new BikingRoutePlanOption())
                .from(stNode).to(enNode));

        is_transit.setBackgroundColor(getResources().getColor(R.color.colorToolbar));
        i_transit.setTextColor(Color.WHITE);
        is_walk.setBackgroundColor(getResources().getColor(R.color.colorToolbar));
        i_walk.setTextColor(Color.WHITE);
        is_bike.setBackgroundColor(Color.WHITE);
        i_bike.setTextColor(Color.BLACK);
    }



    /**
     * 切换路线图标，刷新地图使其生效
     * 注意： 起终点图标使用中心对齐.
     */
    public void changeRouteIcon(View v) {
        if (routeOverlay == null) {
            return;
        }

        useDefaultIcon = !useDefaultIcon;
        routeOverlay.removeFromMap();
        routeOverlay.addToMap();
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(RoutePlanActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            nodeIndex = -1;
//            mBtnPre.setVisibility(View.VISIBLE);
//            mBtnNext.setVisibility(View.VISIBLE);
            route = result.getRouteLines().get(0);
            list=new ArrayList<mapbean>();

            for(int i=0;i<route.getAllStep().size();i++){
                b=new mapbean();
                b.setNodeLocation(((WalkingRouteLine.WalkingStep)route.getAllStep().get(i)).getEntrance().getLocation());
               b.setNodeTitle(((WalkingRouteLine.WalkingStep)route.getAllStep().get(i)).getInstructions());
                list.add(b);}
            adapter=new sAdapter(RoutePlanActivity.this,list);
            map_lv.setAdapter(adapter);
            int time = route.getDuration();
            if ( time / 3600 == 0 ) {
                way.setText( "大约需要：" + time / 60 + "分钟" );
            } else {
                way.setText( "大约需要：" + time / 3600 + "小时" + (time % 3600) / 60 + "分钟" );
            }
            message.setText("距离大约是：" + route.getDistance());
            WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(mBaidumap);
            mBaidumap.setOnMarkerClickListener(overlay);
            routeOverlay = overlay;
            overlay.setData(result.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
        }

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult result) {

        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(RoutePlanActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            nodeIndex = -1;



            if (result.getRouteLines().size() > 1 ) {
                nowResult = result;

                MyTransitDlg myTransitDlg = new MyTransitDlg(RoutePlanActivity.this,
                        result.getRouteLines(),
                        RouteLineAdapter.Type.TRANSIT_ROUTE);
                myTransitDlg.setOnItemInDlgClickLinster(new OnItemInDlgClickListener() {
                    public void onItemClick(int position) {
                        route = nowResult.getRouteLines().get(position);
                        list=new ArrayList<mapbean>();
                        for(int i=0;i<route.getAllStep().size();i++){
                            b=new mapbean();
                            b.setNodeTitle(((TransitRouteLine.TransitStep)route.getAllStep().get(i)).getInstructions());
                            b.setNodeLocation(((TransitRouteLine.TransitStep) route.getAllStep().get(i)).getEntrance().getLocation());
                         list.add(b);}
                        adapter=new sAdapter(RoutePlanActivity.this,list);
                        map_lv.setAdapter(adapter);
                        int time = route.getDuration();
                        if ( time / 3600 == 0 ) {
                            way.setText( "大约需要：" + time / 60 + "分钟" );
                        } else {
                            way.setText( "大约需要：" + time / 3600 + "小时" + (time % 3600) / 60 + "分钟" );
                        }
                        message.setText("距离大约是：" + route.getDistance());
                        TransitRouteOverlay overlay = new MyTransitRouteOverlay(mBaidumap);
                        mBaidumap.setOnMarkerClickListener(overlay);
                        routeOverlay = overlay;
                        overlay.setData(nowResult.getRouteLines().get(position));
                        overlay.addToMap();
                        overlay.zoomToSpan();
                    }

                });
                myTransitDlg.show();



            } else if ( result.getRouteLines().size() == 1 ) {
                // 直接显示
                route = result.getRouteLines().get(0);
                list=new ArrayList<mapbean>();
                for(int i=0;i<route.getAllStep().size();i++){
                    b=new mapbean();
                    b.setNodeTitle(((TransitRouteLine.TransitStep)route.getAllStep().get(i)).getInstructions());
                    b.setNodeLocation(((TransitRouteLine.TransitStep) route.getAllStep().get(i)).getEntrance().getLocation());

                    list.add(b);}
                adapter=new sAdapter(RoutePlanActivity.this,list);
                map_lv.setAdapter(adapter);
                int time = route.getDuration();
                if ( time / 3600 == 0 ) {
                  way.setText( "大约需要：" + time / 60 + "分钟" );
                } else {
                    way.setText( "大约需要：" + time / 3600 + "小时" + (time % 3600) / 60 + "分钟" );
                }
                message.setText("距离大约是：" + route.getDistance());

                TransitRouteOverlay overlay = new MyTransitRouteOverlay(mBaidumap);
                mBaidumap.setOnMarkerClickListener(overlay);
                routeOverlay = overlay;
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();

            } else {
                Log.d("transitresult", "结果数<0" );
                return;
            }


        }
    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(RoutePlanActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            nodeIndex = -1;


            if (result.getRouteLines().size() > 1 ) {
                nowResultd = result;

                MyTransitDlg myTransitDlg = new MyTransitDlg(RoutePlanActivity.this,
                        result.getRouteLines(),
                        RouteLineAdapter.Type.DRIVING_ROUTE);
                myTransitDlg.setOnItemInDlgClickLinster(new OnItemInDlgClickListener() {
                    public void onItemClick(int position) {
                        route = nowResultd.getRouteLines().get(position);

                        list=new ArrayList<mapbean>();
                        for(int i=0;i<route.getAllStep().size();i++){
                            b=new mapbean();
                            b.setNodeTitle(((DrivingRouteLine.DrivingStep)route.getAllStep().get(i)).getInstructions());
                            b.setNodeLocation(((DrivingRouteLine.DrivingStep) route.getAllStep().get(i)).getEntrance().getLocation());

                            list.add(b);}
                        adapter=new sAdapter(RoutePlanActivity.this,list);
                        map_lv.setAdapter(adapter);

                        DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaidumap);
                        mBaidumap.setOnMarkerClickListener(overlay);
                        routeOverlay = overlay;
                        overlay.setData(nowResultd.getRouteLines().get(position));
                        overlay.addToMap();
                        overlay.zoomToSpan();
                    }

                });
                myTransitDlg.show();

            } else if ( result.getRouteLines().size() == 1 ) {
                route = result.getRouteLines().get(0);
                list=new ArrayList<mapbean>();
                for(int i=0;i<route.getAllStep().size();i++){
                    b=new mapbean();
                    b.setNodeLocation(((DrivingRouteLine.DrivingStep)route.getAllStep().get(i)).getEntrance().getLocation());
                    b.setNodeTitle(((DrivingRouteLine.DrivingStep)route.getAllStep().get(i)).getInstructions());

                    list.add(b);}
                adapter=new sAdapter(RoutePlanActivity.this,list);
                map_lv.setAdapter(adapter);
                DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaidumap);
                routeOverlay = overlay;
                mBaidumap.setOnMarkerClickListener(overlay);
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();

            }

        }
    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {
        if (bikingRouteResult == null || bikingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(RoutePlanActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (bikingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }
        if (bikingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
            nodeIndex = -1;

            route = bikingRouteResult.getRouteLines().get(0);

                list=new ArrayList<mapbean>();
            for(int i=0;i<route.getAllStep().size();i++){
                b=new mapbean();
                b.setNodeLocation(((BikingRouteLine.BikingStep)route.getAllStep().get(i)).getEntrance().getLocation());
                b.setNodeTitle(((BikingRouteLine.BikingStep)route.getAllStep().get(i)).getInstructions());

                list.add(b);}
            adapter=new sAdapter(RoutePlanActivity.this,list);
            map_lv.setAdapter(adapter);
            int time = route.getDuration();
            if ( time / 3600 == 0 ) {
                way.setText( "时间：" + time / 60 + "分钟" );
            } else {
                way.setText( "时间：" + time / 3600 + "小时" + (time % 3600) / 60 + "分钟" );
            }
            message.setText("距离：" + route.getDistance()+"米");
            BikingRouteOverlay overlay = new MyBikingRouteOverlay(mBaidumap);
            routeOverlay = overlay;
            mBaidumap.setOnMarkerClickListener(overlay);
            overlay.setData(bikingRouteResult.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
        }
    }

    @Override
    public void onClick(View view) {


// 重置浏览节点的路线数据
        route = null;
//        mBtnPre.setVisibility(View.INVISIBLE);
//        mBtnNext.setVisibility(View.INVISIBLE);
        mBaidumap.clear();
            isrung=true;
        // 处理搜索按钮响应
//        EditText editSt = (EditText) findViewById(R.id.start);
//        EditText editEn = (EditText) findViewById(R.id.end);
        // 设置起终点信息，对于tranist search 来说，城市名无意义


        // 实际使用中请对起点终点城市进行正确的设定
        if (view.getId() == R.id.map_transit) {
            mSearch.transitSearch((new TransitRoutePlanOption())
                    .from(stNode).city(MyApplication.userBean.getCity()+"").to(enNode));
            is_transit.setBackgroundColor(Color.WHITE);
            i_transit.setTextColor(Color.BLACK);
            is_walk.setBackgroundColor(getResources().getColor(R.color.colorToolbar));
            i_walk.setTextColor(Color.WHITE);
            is_bike.setBackgroundColor(getResources().getColor(R.color.colorToolbar));
            i_bike.setTextColor(Color.WHITE);

            map_up.setVisibility(View.VISIBLE);

        } else if (view.getId() == R.id.map_walk) {
           mSearch.walkingSearch((new WalkingRoutePlanOption())
                    .from(stNode).to(enNode));

            is_transit.setBackgroundColor(getResources().getColor(R.color.colorToolbar));
            i_transit.setTextColor(Color.WHITE);
            is_walk.setBackgroundColor(Color.WHITE);
            i_walk.setTextColor(Color.BLACK);
            is_bike.setBackgroundColor(getResources().getColor(R.color.colorToolbar));
            i_bike.setTextColor(Color.WHITE);
            map_up.setVisibility(View.VISIBLE);

        } else if (view.getId() == R.id.map_bike) {

            mSearch.bikingSearch((new BikingRoutePlanOption())
                    .from(stNode).to(enNode));

            is_transit.setBackgroundColor(getResources().getColor(R.color.colorToolbar));
            i_transit.setTextColor(Color.WHITE);
            is_walk.setBackgroundColor(getResources().getColor(R.color.colorToolbar));
            i_walk.setTextColor(Color.WHITE);
            is_bike.setBackgroundColor(Color.WHITE);
            i_bike.setTextColor(Color.BLACK);

            map_up.setVisibility(View.VISIBLE);
        }else if(view.getId()==R.id.up){
            map_up.setVisibility(View.GONE);
            map_ll.setVisibility(View.VISIBLE);
            map_drown.setVisibility(View.VISIBLE);
            oo.setVisibility(View.GONE);
        }else if(view.getId()==R.id.drown)
        {
            oo.setVisibility(View.VISIBLE);
            map_up.setVisibility(View.VISIBLE);
            map_ll.setVisibility(View.GONE);
            map_drown.setVisibility(View.GONE);
        }

    }


    // 定制RouteOverly
    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
            }
            return null;
        }
    }

    private class MyWalkingRouteOverlay extends WalkingRouteOverlay {

        public MyWalkingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
            }
            return null;
        }
    }

    private class MyTransitRouteOverlay extends TransitRouteOverlay {

        public MyTransitRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
            }
            return null;
        }
    }

    private class MyBikingRouteOverlay extends BikingRouteOverlay {
        public  MyBikingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
            }
            return null;
        }


    }

    @Override
    public void onMapClick(LatLng point) {
        mBaidumap.hideInfoWindow();
        map_ll.setVisibility(View.GONE);
        map_drown.setVisibility(View.VISIBLE);

    }

    @Override
    public boolean onMapPoiClick(MapPoi poi) {
        return false;
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mSearch.destroy();
        mMapView.onDestroy();
        super.onDestroy();
    }

    // 响应DLg中的List item 点击
    interface OnItemInDlgClickListener {
        public void onItemClick(int position);
    }

    // 供路线选择的Dialog
    class MyTransitDlg extends Dialog {

        private List<? extends RouteLine> mtransitRouteLines;
        private ListView transitRouteList;
        private  RouteLineAdapter mTransitAdapter;

        OnItemInDlgClickListener onItemInDlgClickListener;

        public MyTransitDlg(Context context, int theme) {
            super(context, theme);
        }

        public MyTransitDlg(Context context, List< ? extends RouteLine> transitRouteLines,  RouteLineAdapter.Type
                type) {
            this( context, 0);
            mtransitRouteLines = transitRouteLines;


            mTransitAdapter = new  RouteLineAdapter( context, mtransitRouteLines , type);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_transit_dialog);

            transitRouteList = (ListView) findViewById(R.id.transitList);
            transitRouteList.setAdapter(mTransitAdapter);

            transitRouteList.setOnItemClickListener( new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onItemInDlgClickListener.onItemClick( position);

                  dismiss();

                }
           });
        }

        public void setOnItemInDlgClickLinster( OnItemInDlgClickListener itemListener) {
            onItemInDlgClickListener = itemListener;
        }

    }


    class sAdapter extends BaseAdapter{
        Context context;
        List<mapbean> lt;
        private LayoutInflater mInflater;
        public  sAdapter(Context context,List<mapbean> list){
            this.context=context;
            this.lt=list;
            mInflater=LayoutInflater.from(context);

        }
        @Override
        public int getCount() {
            return lt.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0 ;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view=mInflater.inflate(R.layout.swet,null);
            TextView tv= (TextView) view.findViewById(R.id.map_tv_step1);
            tv.setText(lt.get(i).getNodeTitle());
            return view;
        }
    }
}
