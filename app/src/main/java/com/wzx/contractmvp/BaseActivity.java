package com.wzx.contractmvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * 描述 TODO
 * Created by 王治湘 on 2017/8/21.
 * version 1.0
 */

@SuppressWarnings("unchecked")
public abstract class BaseActivity<V, P extends BasePresenter<V>> extends AppCompatActivity {
    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());

        mPresenter = createPresenter();//创建presenter

        initViews(savedInstanceState);
    }

    protected void onResume() {
        super.onResume();
        mPresenter.attachView((V) this);
        if (mPresenter.isViewAttached())
            loadData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.dispose();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    protected abstract int getLayoutID();

    //初始化布局文件、控件
    protected abstract void initViews(Bundle savedInstanceState);

    //加载数据
    protected abstract void loadData();

    protected abstract P createPresenter();
}
