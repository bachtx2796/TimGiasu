package com.ptit.bb.timgiasu.screen.chat;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;
import com.google.firebase.database.ValueEventListener;

/**
 * The Chat Contract
 */
interface ChatContract {

    interface Interactor extends IInteractor<Presenter> {
        void getListGroup(String id, ValueEventListener valueEventListener);
    }

    interface View extends PresentView<Presenter> {
        void bindView(ChatAdapter mChatAdapter);
    }

    interface Presenter extends IPresenter<View, Interactor> {
    }
}



