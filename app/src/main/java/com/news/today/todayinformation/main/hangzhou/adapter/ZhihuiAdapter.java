package com.news.today.todayinformation.main.hangzhou.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.news.today.todayinformation.R;
import com.news.today.todayinformation.main.shanghai.adapter.ShanghaiAdapter;
import com.news.today.todayinformation.main.shanghai.dto.ShangHaiDetailBean;
import com.news.today.todayinformation.main.shanghai.dto.ShanghaiBean;
import com.news.today.todayinformation.main.shanghai.view.ShanghaiDetailActivity;

import java.util.ArrayList;

/**
 * Created by anson on 2018/12/3.
 */
public class ZhihuiAdapter extends RecyclerView.Adapter {

    private final ArrayList<ShangHaiDetailBean.XiaoHuaBean> mData;

    public ZhihuiAdapter( ArrayList<ShangHaiDetailBean.XiaoHuaBean> data) {
        mData = data;
    }

    //  创建View 然后进行缓存
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shanghai_fragment, null);
        ShanghaiViewHolder viewHolder = new ShanghaiViewHolder(inflate);
        return viewHolder;
    }

    // 绑定数据
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ShangHaiDetailBean.XiaoHuaBean xiaoHuaBean = mData.get(position);
        if (holder instanceof ShanghaiViewHolder) {
            ((ShanghaiViewHolder) holder).mTv.setText(xiaoHuaBean.content);
            ((ShanghaiViewHolder) holder).mIv.setVisibility(View.GONE);
        }
    }

    // 条目的数量
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // 缓存View  内存友好设计
    public class ShanghaiViewHolder extends RecyclerView.ViewHolder {

        public TextView mTv;
        public ImageView mIv;

        public ShanghaiViewHolder(View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.item_shanghai_tv);
            mIv = itemView.findViewById(R.id.item_shanghai_iv);
        }
    }

}
