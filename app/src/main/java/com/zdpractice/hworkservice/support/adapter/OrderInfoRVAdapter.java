package com.zdpractice.hworkservice.support.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zdpractice.hworkservice.R;
import com.zdpractice.hworkservice.model.OrderBean;
import com.zdpractice.hworkservice.ui.orderinfo.Payment_For_Orders_centent;
import com.zdpractice.hworkservice.ui.orderinfo.OrderDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15813 on 2016/8/15.
 */
public class OrderInfoRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private View view;
    private Context context;
    private List<OrderBean> orderBeanList;
    private String statues;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    public OrderInfoRVAdapter(Context context,ArrayList<OrderBean> orderBeanList,String statues){
        this.orderBeanList=orderBeanList;
        this.context=context;
        this.statues=statues;
    }

    @Override
    public int getItemCount() {
        return orderBeanList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_ITEM){
            view= LayoutInflater.from(context).inflate(R.layout.orderinfo_recycle_base_item,parent,false);
            ViewHolder viewHolder=new ViewHolder(view);
            return viewHolder;
        }else if (viewType==TYPE_FOOTER){
            view= LayoutInflater.from(context).inflate(R.layout.item_foot,parent,false);
            return new FootViewHolder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OrderInfoRVAdapter.ViewHolder) {
            ((ViewHolder) holder).linkname.setText("日常保洁");
            ((ViewHolder) holder).addressarea.setText(orderBeanList.get(position).getAddressArea());
            ((ViewHolder) holder).orderAgreedTime.setText(orderBeanList.get(position).getOrderAgreedTime());
            ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=null;
                    switch (statues){
                        case "1":
                            intent=new Intent(context, OrderDetailActivity.class);
                            break;
                        case "2":
                            intent=new Intent(context, Payment_For_Orders_centent.class);
                            break;
                        case "5":
                            Toast.makeText(context,"后台正在处理，请稍等后刷新",Toast.LENGTH_LONG).show();
                            break;
                        case "6":
                            intent=new Intent(context, OrderDetailActivity.class);
                            break;
                    }
                    intent.putExtra("OrderBean",orderBeanList.get(position));
                    context.startActivity(intent);
                }
            });
        }


    }


    /**
     * 创建ViewHolder类，加载子布局控件
     */
    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView linkname,addressarea,orderAgreedTime,item_state;
        private View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            linkname= (TextView) itemView.findViewById(R.id.item_linkname);
            addressarea= (TextView) itemView.findViewById(R.id.item_addressarea);
            orderAgreedTime= (TextView) itemView.findViewById(R.id.item_orderAgreedTime);
            this.itemView=itemView;
        }
    }

     class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View view) {
            super(view);
        }
    }

}
