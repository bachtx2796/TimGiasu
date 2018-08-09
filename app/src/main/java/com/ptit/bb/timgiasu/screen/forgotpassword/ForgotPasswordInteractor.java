package com.ptit.bb.timgiasu.screen.forgotpassword;

import com.gemvietnam.base.viper.Interactor;

/**
 * The ForgotPassword interactor
 */
class ForgotPasswordInteractor extends Interactor<ForgotPasswordContract.Presenter>
        implements ForgotPasswordContract.Interactor {

    ForgotPasswordInteractor(ForgotPasswordContract.Presenter presenter) {
        super(presenter);
    }
}
