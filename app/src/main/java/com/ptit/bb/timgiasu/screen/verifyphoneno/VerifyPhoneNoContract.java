package com.ptit.bb.timgiasu.screen.verifyphoneno;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;

/**
 * The VerifyPhoneNo Contract
 */
interface VerifyPhoneNoContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void resendPin();

        void checkSMS(String code);
    }
}



