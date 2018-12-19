package com.ptit.bb.timgiasu.screen.register;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;

/**
 * The Register Contract
 */
interface RegisterContract {

    interface Interactor extends IInteractor<Presenter> {
        void signup(String email, String pass, OnCompleteListener<AuthResult> đăng_kí_không_thành_công);
    }

    interface View extends PresentView<Presenter> {
        void setLocation(String location);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void signup(String fullname, String email, String phoneNo, String gender, String pass, String confirmpass, String dob, String cities, String address);

        void showMap();
    }
}



