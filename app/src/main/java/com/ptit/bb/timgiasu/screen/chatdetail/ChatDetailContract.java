package com.ptit.bb.timgiasu.screen.chatdetail;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;
import com.ptit.bb.timgiasu.data.dto.PostDTO;

/**
 * The ChatDetail Contract
 */
interface ChatDetailContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
        void bindPost(PostDTO mPost);

        void bindUsername(String name);

        void bindListMsg(ChatAdapter mAdapter);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void sendMsg(String s);
    }
}



