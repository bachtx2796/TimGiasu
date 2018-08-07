package com.example.bb.timgiasu.screen.profile;

import com.gemvietnam.base.viper.Interactor;

/**
 * The Profile interactor
 */
class ProfileInteractor extends Interactor<ProfileContract.Presenter>
        implements ProfileContract.Interactor {

    ProfileInteractor(ProfileContract.Presenter presenter) {
        super(presenter);
    }
}
