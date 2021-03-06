package com.ptit.bb.timgiasu.screen.home;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;
import com.google.firebase.database.ValueEventListener;

/**
 * The Home Contract
 */
interface HomeContract {

    interface Interactor extends IInteractor<Presenter> {
        void getPosts(String city, ValueEventListener valueEventListener);
    }

    interface View extends PresentView<Presenter> {
        void bindView(HomeAdapter mHomeAdapter);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void refreshData();
    }
}



