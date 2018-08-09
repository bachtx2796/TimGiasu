package com.ptit.bb.timgiasu.screen.splash;

import com.gemvietnam.base.viper.Interactor;

/**
 * The Splash interactor
 */
class SplashInteractor extends Interactor<SplashContract.Presenter>
        implements SplashContract.Interactor {

    SplashInteractor(SplashContract.Presenter presenter) {
        super(presenter);
    }
}
