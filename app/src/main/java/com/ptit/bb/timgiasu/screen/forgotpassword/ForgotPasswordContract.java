package com.ptit.bb.timgiasu.screen.forgotpassword;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;
import com.google.android.gms.tasks.OnCompleteListener;

/**
 * The ForgotPassword Contract
 */
interface ForgotPasswordContract {

    interface Interactor extends IInteractor<Presenter> {
        void sendLinkToEmail(String email, OnCompleteListener<Void> onCompleteListener);
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void requestResetPass(String email);
    }
}



