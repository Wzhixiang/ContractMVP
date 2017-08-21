package com.example.mvplib;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * 描述 TODO 基础Presenter，关联view
 * Created by 王治湘 on 2017/8/21.
 * version 1.0
 */

public abstract class BasePresenter<V> {

    protected Reference<V> mViewRef;//View接口类型的弱引用

    /**
     * presenter和view关联
     * @param view
     */
    public void attachView(V view){
        mViewRef=new WeakReference<>(view);//建立关系
    }

    protected V getView(){
        return mViewRef.get();
    }

    /**
     * 判断presenter和view是否关联
     * @return
     */
    public boolean isViewAttached(){
        return mViewRef!=null&&mViewRef.get()!=null;
    }

    /**
     * presenter和view解除关联
     */
    public void detachView(){
        if (mViewRef!=null){
            mViewRef.clear();
            mViewRef=null;
        }
    }
}
