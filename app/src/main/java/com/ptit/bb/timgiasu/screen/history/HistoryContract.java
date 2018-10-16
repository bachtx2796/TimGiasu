package com.ptit.bb.timgiasu.screen.history;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;
import com.ptit.bb.timgiasu.screen.home.HomeAdapter;

/**
 * The History Contract
 */
interface HistoryContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
        void bindPost(HomeAdapter adapter, HomeAdapter mReceviePostAdapter);
    }

    interface Presenter extends IPresenter<View, Interactor> {
    }
}



