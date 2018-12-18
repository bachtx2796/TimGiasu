package com.ptit.bb.timgiasu.screen.login;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;
import com.google.firebase.database.ValueEventListener;

/**
 * The Login Contract
 */
interface LoginContract {

    interface Interactor extends IInteractor<Presenter> {
        void saveUser(String id, ValueEventListener valueEventListener);
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void displayForgotPassScreen();

        void displaySignupScreen();

        void login(String username, String pass);
    }
}



