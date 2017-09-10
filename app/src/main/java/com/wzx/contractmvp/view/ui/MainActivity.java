package com.wzx.contractmvp.view.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.example.mvplib.BaseActivity;
import com.wzx.contractmvp.contract.ContractMain;
import com.wzx.contractmvp.R;
import com.wzx.contractmvp.presenter.PresenterMain;

/**
 * 添加头部说明
 */
public class MainActivity extends BaseActivity<ContractMain.MView, PresenterMain> implements ContractMain.MView {

    private TextView textView;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        textView = (TextView) findViewById(R.id.tv_change_text);
        textView.setOnClickListener(v->mPresenter.requestText());
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected PresenterMain createPresenter() {
        return new PresenterMain();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showText(String string) {
        textView.setText(string);
    }
}
