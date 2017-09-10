package com.wzx.contractmvp.contract;

import com.example.mvplib.BaseView;

/**
 * 描述 TODO
 * Created by 王治湘 on 2017/9/4.
 * version 1.0
 */

public interface ContractMain {

    interface MView extends BaseView {
        void showText(String string);
    }

    interface MPresenter {
        void requestText();
    }
}
