package com.wzx.contractmvp.view.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wzx.contractmvp.R;
import com.wzx.contractmvp.adapter.TitleAdapter;
import com.wzx.contractmvp.widget.DropMenuView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述 TODO
 * Created by 王治湘 on 2017/9/14.
 * version 1.0
 */

public class TestViewActivity extends AppCompatActivity{

    @BindView(R.id.dropmenuview)
    DropMenuView dropMenuView;
    private List<String> headers;
    private List<View> popupViews;

    private TitleAdapter firstAdapter;
    private TitleAdapter secondAdapter;

    @Override
    protected void onCreate(Bundle saveInstancestate){
        super.onCreate(saveInstancestate);
        setContentView(R.layout.activity_testview);
        ButterKnife.bind(this);

        headers = new ArrayList<>();
        headers.add("第一");
        headers.add("第二");

        popupViews = new ArrayList<>();
        RecyclerView firstview = new RecyclerView(this);
        firstview.setLayoutManager(new LinearLayoutManager(this));
        firstAdapter = new TitleAdapter(headers);
        firstview.setAdapter(firstAdapter);
        firstAdapter.addOnItemClickListener(position -> {
//            dropMenuView.setTabText(headers.get(position));
            dropMenuView.closeMenu();
        });

        RecyclerView secondview = new RecyclerView(this);
        secondview.setLayoutManager(new LinearLayoutManager(this));
        secondAdapter = new TitleAdapter(headers);
        secondview.setAdapter(secondAdapter);
        secondAdapter.addOnItemClickListener(position -> {
//            dropMenuView.setTabText(headers.get(position));
            dropMenuView.closeMenu();
        });

        popupViews.add(firstview);
        popupViews.add(secondview);

        TextView contentView = new TextView(this);

        dropMenuView.setDropMenuView(headers, popupViews, contentView);

    }

    @Override
    public void onBackPressed() {
        //退出activity前关闭菜单
        if (dropMenuView.isShowing()) {
            dropMenuView.closeMenu();
        } else {
            super.onBackPressed();
        }
    }
}
