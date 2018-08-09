package com.ptit.bb.timgiasu.screen.main;

import com.gemvietnam.base.viper.Interactor;

/**
 * The Main interactor
 */
class MainInteractor extends Interactor<MainContract.Presenter>
        implements MainContract.Interactor {

    MainInteractor(MainContract.Presenter presenter) {
        super(presenter);
    }
}
