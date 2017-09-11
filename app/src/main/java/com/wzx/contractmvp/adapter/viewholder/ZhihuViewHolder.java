package com.wzx.contractmvp.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wzx.contractmvp.R;
import com.wzx.contractmvp.interfaces.ItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述 TODO
 * Created by 王治湘 on 2017/9/10.
 * version 1.0
 */

public class ZhihuViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.news_image)
    ImageView mNewsImage;
    @BindView(R.id.news_title)
    TextView mNewsTitle;
    @BindView(R.id.cv_zhihu_item)
    CardView mCvZhihuItem;

    private Context mContext;
    private ItemClickListener mListener;

    public ZhihuViewHolder(View view, ItemClickListener listener) {
        super(view);
        ButterKnife.bind(this, view);

        mContext = itemView.getContext();
        this.mListener = listener;


        if (mListener != null) {
            mCvZhihuItem.setOnClickListener(v -> mListener.onItemClick(getLayoutPosition()));
        }
    }

    public void setTitle(String title){
        mNewsTitle.setText(title);
    }

    public void setImage(String url){
        Glide.with(mContext)
                .load(url)
                .into(mNewsImage);
    }
}
