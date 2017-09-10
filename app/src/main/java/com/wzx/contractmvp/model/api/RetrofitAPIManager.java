package com.wzx.contractmvp.model.api;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.GsonBuilder;
import com.wzx.contractmvp.Constants;
import com.wzx.contractmvp.MyApplication;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 描述 TODO
 * Created by 王治湘 on 2017/9/10.
 * version 1.0
 */

public class RetrofitAPIManager {

    private RetrofitService serviceApi;
    private static RetrofitAPIManager sApiManager;

    public static RetrofitAPIManager getInstence() {
        if (sApiManager == null) {
            synchronized (RetrofitAPIManager.class) {
                if (sApiManager == null) {
                    sApiManager = new RetrofitAPIManager();
                }
            }
        }
        return sApiManager;
    }

    public RetrofitService provideClientApi() {
        if (serviceApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.SERVER_URL)
                    .client(genericClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

                    .addConverterFactory(GsonConverterFactory.create(
                            new GsonBuilder()
                                    .setLenient()//TODO error (use jsonreader.setlenient true) if not GsonConverterFactory.create(new GsonBuilder().setLenient().create())
                                    .setDateFormat(Constants.DateForm)
                                    .create()))
                    .build();
            serviceApi = retrofit.create(RetrofitService.class);
        }
        return serviceApi;
    }


    private OkHttpClient genericClient() {
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(),
                        new SharedPrefsCookiePersistor(MyApplication.getContext()));

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpInterceptor())
                .cookieJar(cookieJar)
                .build();
        return httpClient;
    }
}
