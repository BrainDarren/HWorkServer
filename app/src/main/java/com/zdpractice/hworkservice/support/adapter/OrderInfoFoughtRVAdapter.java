package com.zdpractice.hworkservice.support.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zdpractice.hworkservice.R;
import com.zdpractice.hworkservice.model.OrderBean;
import com.zdpractice.hworkservice.ui.orderinfo.Payment_For_Orders_centent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15813 on 2016/9/19.
 */
public class OrderInfoFoughtRVAdapter extends RecyclerView.Adapter<OrderInfoFoughtRVAdapter.ViewHolder> {

    private View view;
    private Context context;
    private List<OrderBean> orderBeanList;
    private String statues;

    public OrderInfoFoughtRVAdapter(Context context, ArrayList<OrderBean> orderBeanList, String statues){
        this.orderBeanList=orderBeanList;
        this.context=context;
        this.statues=statues;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.orderinfo_recycle_base_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OrderInfoFoughtRVAdapter.ViewHolder holder, final int position) {

        holder.linkname.setText("日常保洁");
        holder.addressarea.setText(orderBeanList.get(position).getAddressArea());
        holder.orderAgreedTime.setText(orderBeanList.get(position).getOrderAgreedTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //跳转到待服务详情界面
                Intent intent=new Intent(context, Payment_For_Orders_centent.class);
                intent.putExtra("OrderBean",orderBeanList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderBeanList.size();
    }


    /**
     * 创建ViewHolder类，加载子布局控件
     */
    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView linkname,addressarea,orderAgreedTime;
        private View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            linkname= (TextView) itemView.findViewById(R.id.item_linkname);
            addressarea= (TextView) itemView.findViewById(R.id.item_addressarea);
            orderAgreedTime= (TextView) itemView.findViewById(R.id.item_orderAgreedTime);
            this.itemView=itemView;
        }
    }

}
