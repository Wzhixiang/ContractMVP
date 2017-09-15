package com.wzx.contractmvp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wzx.contractmvp.R;
import com.wzx.contractmvp.adapter.viewholder.TitleHolder;
import com.wzx.contractmvp.interfaces.ItemClickListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by PandaQ on 2016/10/19.
 * email : 767807368@qq.com
 */

public class TitleAdapter extends RecyclerView.Adapter {

    private List<String> mStories;

    public TitleAdapter(List<String> stories) {
        mStories = stories;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zhihudaily_item, parent, false);
        return new TitleHolder(view, mClicklistener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        TitleHolder holder = (TitleHolder) viewHolder;

        holder.setTitle(mStories.get(position));
    }

    @Override
    public int getItemCount() {
        return mStories.size();
    }

    private ItemClickListener mClicklistener;

    public void addOnItemClickListener(ItemClickListener clicklistener) {
        mClicklistener = clicklistener;
    }
}
