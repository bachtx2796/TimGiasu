package com.ptit.bb.timgiasu.screen.register;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;

/**
 * The Register Contract
 */
interface RegisterContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
        void setLocation(String location);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void signup(String fullname, String email, String phoneNo, String gender, String pass, String confirmpass, String dob, String cities, String address);

        void showMap();

        void getSizeUserFromDB(String city);
    }
}



