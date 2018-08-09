package com.ptit.bb.timgiasu.screen.tutors;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;

/**
 * The Tutors Contract
 */
interface TutorsContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
        void bindView(TutorAdapter mTutorAdapter);
    }

    interface Presenter extends IPresenter<View, Interactor> {
    }
}


