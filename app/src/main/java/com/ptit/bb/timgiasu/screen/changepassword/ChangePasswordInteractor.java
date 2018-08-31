package com.ptit.bb.timgiasu.screen.changepassword;

import com.gemvietnam.base.viper.Interactor;

/**
 * The ChangePassword interactor
 */
class ChangePasswordInteractor extends Interactor<ChangePasswordContract.Presenter>
        implements ChangePasswordContract.Interactor {

    ChangePasswordInteractor(ChangePasswordContract.Presenter presenter) {
        super(presenter);
    }
}
