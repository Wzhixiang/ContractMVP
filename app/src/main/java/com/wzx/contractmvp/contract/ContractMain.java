package com.wzx.contractmvp.contract;

import com.wzx.contractmvp.interfaces.IBaseView;
import com.wzx.contractmvp.model.bean.ZhiHuDaily;

/**
 * 描述 TODO
 * Created by 王治湘 on 2017/9/4.
 * version 1.0
 */

public interface ContractMain {

    interface MView extends IBaseView {
        void getStorysSuccess(ZhiHuDaily zhiHuDaily);
        void getStorysFail(String msg);
    }

    interface MPresenter {
        void requestStorys();
    }
}
