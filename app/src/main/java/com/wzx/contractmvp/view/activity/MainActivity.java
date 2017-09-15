package com.wzx.contractmvp.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.wzx.contractmvp.R;
import com.wzx.contractmvp.adapter.ZhihuStoryAdapter;
import com.wzx.contractmvp.contract.ContractMain;
import com.wzx.contractmvp.model.bean.TopStories;
import com.wzx.contractmvp.model.bean.ZhiHuDaily;
import com.wzx.contractmvp.model.bean.ZhihuStory;
import com.wzx.contractmvp.presenter.MainPresent;
import com.wzx.contractmvp.utils.GlideImageLoader;
import com.wzx.contractmvp.utils.SnackBarUtils;
import com.wzx.contractmvp.view.BaseActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

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

    @BindView(R.id.top_news)
    Banner topnew;
    private List<String> titles;
    private List<String> images;

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

        titles = new ArrayList<>();
        images = new ArrayList<>();

        //设置样式
        //1. BannerConfig.NOT_INDICATOR   不显示指示器和标题
        //2. BannerConfig.CIRCLE_INDICATOR   显示圆形指示器
        //3. BannerConfig.NUM_INDICATOR    显示数字指示器
        //4. BannerConfig.NUM_INDICATOR_TITLE   显示数字指示器和标题
        //5. BannerConfig.CIRCLE_INDICATOR_TITLE    显示圆形指示器和标题（垂直显示）
        //6. BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE   显示圆形指示器和标题（水平显示）

        topnew.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);

        //设置轮播样式（没有标题默认为右边,有标题时默认左边）
        //BannerConfig.LEFT    指示器居左
        //BannerConfig.CENTER    指示器居中
        //BannerConfig.RIGHT    指示器居右
        topnew.setIndicatorGravity(BannerConfig.CENTER);

        //设置是否自动轮播（不设置则默认自动）
        topnew.isAutoPlay(true);

        //设置是否允许手动滑动轮播图（默认true）
        topnew.setViewPagerIsScroll(true);

        //设置轮播图片间隔时间（不设置默认为2000）
        topnew.setDelayTime(5000);

        //设置图片加载器
        topnew.setImageLoader(new GlideImageLoader());


        topnew.setOnBannerListener(position -> {

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
    protected void onStart() {
        super.onStart();
        //开始轮播
        topnew.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        topnew.stopAutoPlay();
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
    public void getStorysSuccess(ZhiHuDaily zhiHuDaily) {
        this.stories.clear();
        this.stories.addAll(zhiHuDaily.getStories());
        mAdapter.notifyDataSetChanged();

        titles.clear();
        images.clear();

        for (TopStories stoty : zhiHuDaily.getTop_stories()) {
            titles.add(stoty.getTitle());
            images.add(stoty.getImage());
        }

        //设置轮播要显示的标题和图片对应（如果不传默认不显示标题）
        topnew.setBannerTitles(titles);

        //所有设置参数方法都放在此方法之前执行
        topnew.setImages(images);
//        topnew.update(images, titles);
        //banner设置方法全部调用完毕时最后调用
        topnew.start();
    }

    @Override
    public void getStorysFail(String msg) {
        SnackBarUtils.showSnackBar(mZhihudailyList, msg);
    }
}
