package com.ptit.bb.timgiasu.screen.forgotpassword;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;

/**
 * The ForgotPassword Presenter
 */
public class ForgotPasswordPresenter extends Presenter<ForgotPasswordContract.View, ForgotPasswordContract.Interactor>
        implements ForgotPasswordContract.Presenter {

    public ForgotPasswordPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public ForgotPasswordContract.View onCreateView() {
        return ForgotPasswordFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public ForgotPasswordContract.Interactor onCreateInteractor() {
        return new ForgotPasswordInteractor(this);
    }
}
