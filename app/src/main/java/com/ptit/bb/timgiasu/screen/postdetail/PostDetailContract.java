package com.ptit.bb.timgiasu.screen.postdetail;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;
import com.ptit.bb.timgiasu.data.dto.PostDTO;

/**
 * The PostDetail Contract
 */
interface PostDetailContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
        void bindView(PostDTO mPost);
    }

    interface Presenter extends IPresenter<View, Interactor> {
    }
}



