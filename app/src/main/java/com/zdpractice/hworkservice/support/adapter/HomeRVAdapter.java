package com.zdpractice.hworkservice.support.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 15813 on 2016/8/17.
 */
public class HomeRVAdapter extends RecyclerView.Adapter<HomeRVAdapter.MyViewHolder>{

    private View view;
    private Context context;


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        view= LayoutInflater.from(context).inflate(R.)
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView imageView;
        private TextView replise;
        private View itemView;

        public MyViewHolder(View itemView) {
            super(itemView);

        }
    }
}
