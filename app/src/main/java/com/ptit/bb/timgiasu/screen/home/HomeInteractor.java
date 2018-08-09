package com.ptit.bb.timgiasu.screen.home;

import com.gemvietnam.base.viper.Interactor;

/**
 * The Home interactor
 */
class HomeInteractor extends Interactor<HomeContract.Presenter>
        implements HomeContract.Interactor {

    HomeInteractor(HomeContract.Presenter presenter) {
        super(presenter);
    }
}
