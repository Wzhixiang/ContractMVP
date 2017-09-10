package com.wzx.contractmvp.presenter;

import com.example.mvplib.BasePresenter;
import com.wzx.contractmvp.contract.ContractMain;
import com.wzx.contractmvp.model.api.RetrofitAPIManager;
import com.wzx.contractmvp.model.bean.ZhihuStory;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * 描述 TODO
 * Created by 王治湘 on 2017/9/4.
 * version 1.0
 */

public class PresenterMain extends BasePresenter<ContractMain.MView> implements ContractMain.MPresenter {

    @Override
    public void requestText() {
        if (mViewRef == null){
            return;
        }
        mViewRef.get().showProgress();
        RetrofitAPIManager.getInstence().provideClientApi().getZhihuDaily()
                .map(zhiHuDaily -> {
                    ArrayList<ZhihuStory> stories = zhiHuDaily.getStories();
                    return stories;
                })
                //设置事件触发在非主线程
                .subscribeOn(Schedulers.io())
                //设置事件接受在UI线程以达到UI显示的目的
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<ZhihuStory>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        //绑定观察对象，注意在界面的ondestory或者onpouse方法中调用presenter.unsubcription();
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<ZhihuStory> zhihuStories) {
                        mViewRef.get().showText(zhihuStories.get(0).getTitle());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mViewRef.get().showText(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mViewRef.get().hideProgress();
                    }
                });

    }
}
