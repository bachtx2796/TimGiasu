package com.ptit.bb.timgiasu.screen.changepassword;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;
import com.google.android.gms.tasks.OnCompleteListener;

/**
 * The ChangePassword Contract
 */
interface ChangePasswordContract {

    interface Interactor extends IInteractor<Presenter> {
        void savePass(String pass, OnCompleteListener onCompleteListener);
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void savePass(String pass, String newpass);
    }
}



