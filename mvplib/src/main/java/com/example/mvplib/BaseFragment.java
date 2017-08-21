package com.example.mvplib;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 描述 TODO
 * Created by 王治湘 on 2017/8/21.
 * version 1.0
 */

public abstract class BaseFragment<V, P extends BasePresenter<V>> extends Fragment {

    protected P mPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();//创建presenter

        initVariables();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutID(), container, false);
        initViews(savedInstanceState);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.attachView((V) this);
        loadData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    protected abstract void initVariables();

    protected abstract int getLayoutID();

    protected abstract void initViews(@Nullable Bundle savedInstanceState);

    protected abstract void loadData();

    protected abstract P createPresenter();

}
