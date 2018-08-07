package com.example.bb.timgiasu.screen.register;

import com.gemvietnam.base.viper.Interactor;

/**
 * The Register interactor
 */
class RegisterInteractor extends Interactor<RegisterContract.Presenter>
        implements RegisterContract.Interactor {

    RegisterInteractor(RegisterContract.Presenter presenter) {
        super(presenter);
    }
}
