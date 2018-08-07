package com.example.bb.timgiasu.screen.login;

import com.gemvietnam.base.viper.Interactor;

/**
 * The LoginFragment interactor
 */
class LoginFragmentInteractor extends Interactor<LoginFragmentContract.Presenter>
        implements LoginFragmentContract.Interactor {

    LoginFragmentInteractor(LoginFragmentContract.Presenter presenter) {
        super(presenter);
    }
}
