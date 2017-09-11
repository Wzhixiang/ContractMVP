package com.wzx.contractmvp.view.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.wzx.contractmvp.BaseActivity;
import com.wzx.contractmvp.adapter.ZhihuStoryAdapter;
import com.wzx.contractmvp.contract.ContractMain;
import com.wzx.contractmvp.R;
import com.wzx.contractmvp.model.bean.ZhihuStory;
import com.wzx.contractmvp.presenter.MainPresent;
import com.wzx.contractmvp.utils.SnackBarUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 添加头部说明
 */
public class MainActivity extends BaseActivity<ContractMain.MView, MainPresent> implements ContractMain.MView {

    @BindView(R.id.zhihudaily_list)
    RecyclerView mZhihudailyList;
    private ZhihuStoryAdapter mAdapter;
    private ArrayList<ZhihuStory> stories;
    @BindView(R.id.progressbar)
    ProgressBar mProgressbar;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        mZhihudailyList.setLayoutManager(new LinearLayoutManager(this));
        stories = new ArrayList<>();
        mAdapter = new ZhihuStoryAdapter(this, stories);
        mZhihudailyList.setAdapter(mAdapter);
        mAdapter.addOnItemClickListener(position -> {
            SnackBarUtils.showSnackBar(mZhihudailyList, stories.get(position).getTitle());
        });
    }

    @Override
    protected void loadData() {
        mPresenter.requestStorys();
    }

    @Override
    protected MainPresent createPresenter() {
        return new MainPresent();
    }

    @Override
    public void showProgress() {
        mProgressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressbar.setVisibility(View.GONE);
    }


    @Override
    public void getStorysSuccess(ArrayList<ZhihuStory> stories) {
        this.stories.clear();
        this.stories.addAll(stories);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getStorysFail(String msg) {
        SnackBarUtils.showSnackBar(mZhihudailyList, msg);
    }
}
