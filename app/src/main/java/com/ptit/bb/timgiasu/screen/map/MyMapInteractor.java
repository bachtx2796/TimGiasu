package com.ptit.bb.timgiasu.screen.map;

import com.gemvietnam.base.viper.Interactor;

/**
 * The Map interactor
 */
class MyMapInteractor extends Interactor<MyMapContract.Presenter>
        implements MyMapContract.Interactor {

    MyMapInteractor(MyMapContract.Presenter presenter) {
        super(presenter);
    }
}
