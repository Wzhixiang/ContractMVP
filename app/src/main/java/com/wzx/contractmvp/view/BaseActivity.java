package com.wzx.contractmvp.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.wzx.contractmvp.R;
import com.wzx.contractmvp.presenter.BasePresenter;


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
        mPresenter.clearDisposable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();

        //判断隐藏软键盘是否弹出
        if (getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED) {
            //隐藏软键盘
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }
    }

    /**
     * Requests given permission.
     * If the permission has been denied previously, a Dialog will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    protected void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            showAlertDialog("请求授权", rationale,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(BaseActivity.this,
                                    new String[]{permission}, requestCode);
                        }
                    }, "确定", null, "拒绝");
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    /**
     * This method shows dialog with given title & message.
     * Also there is an option to pass onClickListener for positive & negative button.
     *
     * @param title                         - dialog title
     * @param message                       - dialog message
     * @param onPositiveButtonClickListener - listener for positive button
     * @param positiveText                  - positive button text
     * @param onNegativeButtonClickListener - listener for negative button
     * @param negativeText                  - negative button text
     */
    protected void showAlertDialog(@Nullable String title, @Nullable String message,
                                   @Nullable DialogInterface.OnClickListener onPositiveButtonClickListener,
                                   @NonNull String positiveText,
                                   @Nullable DialogInterface.OnClickListener onNegativeButtonClickListener,
                                   @NonNull String negativeText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, onPositiveButtonClickListener);
        builder.setNegativeButton(negativeText, onNegativeButtonClickListener);
    }


    protected abstract int getLayoutID();

    //初始化布局文件、控件
    protected abstract void initViews(Bundle savedInstanceState);

    //加载数据
    protected abstract void loadData();

    protected abstract P createPresenter();
}
