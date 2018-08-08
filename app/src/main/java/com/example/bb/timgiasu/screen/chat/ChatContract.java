package com.example.bb.timgiasu.screen.chat;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;

/**
 * The Chat Contract
 */
interface ChatContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
        void bindView(ChatAdapter mChatAdapter);
    }

    interface Presenter extends IPresenter<View, Interactor> {
    }
}



