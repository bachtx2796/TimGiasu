package com.ptit.bb.timgiasu.screen.login;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;

/**
 * The LoginFragment Contract
 */
interface LoginFragmentContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void displayForgotPassScreen();

        void displaySignupScreen();

        void login(String username, String pass);
    }
}



