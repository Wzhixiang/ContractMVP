package com.example.mvplib;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 描述 TODO 基础Presenter，关联view
 * Created by 王治湘 on 2017/8/21.
 * version 1.0
 */

public abstract class BasePresenter<V> {

    protected Reference<V> mViewRef;//View接口类型的弱引用

    //将所有正在处理的Subscription都添加到CompositeSubscription中。统一退出的时候注销观察
    private CompositeDisposable mCompositeDisposable;

    /**
     * presenter和view关联
     *
     * @param view
     */
    public void attachView(V view) {
        mViewRef = new WeakReference<>(view);//建立关系
    }

    public void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    //在界面退出等需要解绑观察者的情况下调用此方法统一解绑，防止Rx造成的内存泄漏
    public void dispose() {
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }

    protected V getView() {
        return mViewRef.get();
    }

    /**
     * 判断presenter和view是否关联
     *
     * @return
     */
    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    /**
     * presenter和view解除关联
     */
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }

        dispose();
    }
}
