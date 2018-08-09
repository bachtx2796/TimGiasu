package com.ptit.bb.timgiasu.screen.profile;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;

/**
 * The Profile Presenter
 */
public class ProfilePresenter extends Presenter<ProfileContract.View, ProfileContract.Interactor>
        implements ProfileContract.Presenter {

    public ProfilePresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public ProfileContract.View onCreateView() {
        return ProfileFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public ProfileContract.Interactor onCreateInteractor() {
        return new ProfileInteractor(this);
    }
}
