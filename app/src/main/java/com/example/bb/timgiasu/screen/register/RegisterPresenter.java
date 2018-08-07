package com.example.bb.timgiasu.screen.register;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;

/**
 * The Register Presenter
 */
public class RegisterPresenter extends Presenter<RegisterContract.View, RegisterContract.Interactor>
        implements RegisterContract.Presenter {

    public RegisterPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public RegisterContract.View onCreateView() {
        return RegisterFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public RegisterContract.Interactor onCreateInteractor() {
        return new RegisterInteractor(this);
    }
}
