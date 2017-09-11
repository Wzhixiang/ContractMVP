package com.wzx.contractmvp.contract;

import com.wzx.contractmvp.BaseView;
import com.wzx.contractmvp.model.bean.ZhihuStory;

import java.util.ArrayList;

/**
 * 描述 TODO
 * Created by 王治湘 on 2017/9/4.
 * version 1.0
 */

public interface ContractMain {

    interface MView extends BaseView {
        void getStorysSuccess(ArrayList<ZhihuStory> stories);
        void getStorysFail(String msg);
    }

    interface MPresenter {
        void requestStorys();
    }
}
