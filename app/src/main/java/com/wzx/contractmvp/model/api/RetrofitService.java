package com.wzx.contractmvp.model.api;


import com.wzx.contractmvp.model.bean.ZhiHuDaily;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * 描述 TODO
 * Created by 王治湘 on 2017/9/10.
 * version 1.0
 */

public interface RetrofitService {
    //使用retrofit+RxAndroid的接口定义
    @GET("news/latest")
    Observable<ZhiHuDaily> getZhihuDaily();
}
