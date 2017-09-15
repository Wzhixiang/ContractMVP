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

public class TitleHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.news_title)
    TextView mNewsTitle;

    private ItemClickListener mListener;

    public TitleHolder(View view, ItemClickListener listener) {
        super(view);
        ButterKnife.bind(this, view);

        this.mListener = listener;

        if (mListener != null) {
            itemView.setOnClickListener(v -> mListener.onItemClick(getLayoutPosition()));
        }
    }

    public void setTitle(String title){
        mNewsTitle.setText(title);
    }
}
