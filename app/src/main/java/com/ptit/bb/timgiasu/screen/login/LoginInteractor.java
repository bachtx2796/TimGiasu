package com.ptit.bb.timgiasu.screen.login;

import com.gemvietnam.base.viper.Interactor;

/**
 * The LoginFragment interactor
 */
class LoginInteractor extends Interactor<LoginContract.Presenter>
        implements LoginContract.Interactor {

    LoginInteractor(LoginContract.Presenter presenter) {
        super(presenter);
    }
}
