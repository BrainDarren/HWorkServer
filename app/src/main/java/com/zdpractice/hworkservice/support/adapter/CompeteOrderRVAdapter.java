package com.zdpractice.hworkservice.support.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.zdpractice.hworkservice.MyApplication;
import com.zdpractice.hworkservice.R;
import com.zdpractice.hworkservice.model.ChangePwdBean;
import com.zdpractice.hworkservice.model.OrderBean;
import com.zdpractice.hworkservice.support.networktool.NetWorkTools;
import com.zdpractice.hworkservice.ui.competeorder.CompeteOrderinfoActivity;
import org.xutils.common.Callback;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by 15813 on 2016/8/19.
 */
public class CompeteOrderRVAdapter extends RecyclerView.Adapter<CompeteOrderRVAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private ArrayList<OrderBean> list;
    private int position;
    private String price;
    private MyViewHolder holder;

    public CompeteOrderRVAdapter(Context context, ArrayList<OrderBean> list){
        this.context=context;
        this.list=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(this.context).inflate(R.layout.competeorder_rv_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        this.holder=holder;
        this.position=position;
        //如果传过来的数据集合不为空
        if(list.size()>0){
            if (MyApplication.locationBean.getLatitude()!=null){
                //把雇主的纬度与经度转化为LatLng类型
                LatLng latLng=new LatLng(Double.parseDouble(list.get(position).getBaiduMapLat()),
                        Double.parseDouble(list.get(position).getBaiduMapLng()));
                //把服务者的纬度与经度转化为LatLng类型
                LatLng latLng1=new LatLng(Double.parseDouble(MyApplication.locationBean.getLatitude()),
                        Double.parseDouble(MyApplication.locationBean.getLontitude()));
                //获得直线距离
                Double distance=DistanceUtil.getDistance(latLng,latLng1);
                if(distance!=null){
                    //转为不保留小数
                    BigDecimal b   =   new   BigDecimal(distance/1000);
                    //四舍五入
                    double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
                    holder.tvLong.setText(f1+"千米");
                }
            }

            holder.tvAddress.setText(list.get(position).getAddressArea());
            holder.tvTime.setText(list.get(position).getOrderAgreedTime());
            if (list.get(position).getServiceclass()!=" "){
                holder.tvClass.setText("日常服务");
            }
            holder.tvNum.setText(list.get(position).getOrdernum()+"");
            holder.itemView.setOnClickListener(this);
            holder.tvCompete.setOnClickListener(this);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.competeorder_tv_compete:
//                Toast.makeText(context,"即将开始抢单",Toast.LENGTH_LONG).show();
                //TODO 访问网络请求抢单
                price=holder.edtFight.getText().toString();
                if(price==null || price.equals("")){
                    Toast.makeText(context,"价格不能为空",Toast.LENGTH_LONG).show();
                    break;
                }else {
                    NetWorkTools.newInstance().requestConfirmOrder(MyApplication.userBean.getToken(), list.get(position).getOrderno() + "", price, MyApplication.userBean.getUserid() + "", new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            ChangePwdBean bean = JSON.parseObject(result, ChangePwdBean.class);
                            if (bean.getCode().equals("200")) {
                                Toast.makeText(context, "开始竞单", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "竞单失败,请检查网络或重试", Toast.LENGTH_LONG).show();
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
                }
                break;

            case R.id.lycompeteorder:
                Intent intent =new Intent(context, CompeteOrderinfoActivity.class);
                intent.putExtra("orderbean",list.get(this.position));
                context.startActivity(intent);
                break;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvAddress,tvTime,tvClass,tvCompete,tvNum,tvLong;
        private View itemView;
        private EditText edtFight;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
            tvAddress= (TextView) itemView.findViewById(R.id.competeorder_address);
            tvTime =(TextView) itemView.findViewById(R.id.competeorder_servertime);
            tvClass=(TextView) itemView.findViewById(R.id.competeorder_style);
            tvCompete=(TextView) itemView.findViewById(R.id.competeorder_tv_compete);
            edtFight= (EditText) itemView.findViewById(R.id.competeorder_edit_price);
            tvNum=(TextView) itemView.findViewById(R.id.competeorder_servernum);
            tvLong=(TextView) itemView.findViewById(R.id.competeorder_long);
        }
    }
}
