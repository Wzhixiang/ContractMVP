package com.wzx.contractmvp.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wzx.contractmvp.R;
import com.wzx.contractmvp.adapter.viewholder.ZhihuViewHolder;
import com.wzx.contractmvp.interfaces.ItemClickListener;
import com.wzx.contractmvp.model.bean.ZhihuStory;

import java.util.ArrayList;


/**
 * Created by PandaQ on 2016/10/19.
 * email : 767807368@qq.com
 */

public class ZhihuStoryAdapter extends RecyclerView.Adapter {

    private Activity mActivity;
    private ArrayList<ZhihuStory> mStories;

    public ZhihuStoryAdapter(Activity activity, ArrayList<ZhihuStory> stories) {
        mActivity = activity;
        mStories = stories;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.zhihudaily_item, parent, false);
        return new ZhihuViewHolder(view, mClicklistener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        ZhihuViewHolder holder = (ZhihuViewHolder) viewHolder;

        holder.setTitle(mStories.get(position).getTitle());
        holder.setImage(mStories.get(position).getImages().get(0));
    }

    @Override
    public int getItemCount() {
        return mStories.size();
    }

    public void addItem(ArrayList<ZhihuStory> stories) {
        int position = mStories.size();
        mStories.addAll(stories);
        notifyItemInserted(position);
    }

    private ItemClickListener mClicklistener;

    public void addOnItemClickListener(ItemClickListener clicklistener) {
        mClicklistener = clicklistener;
    }
}
