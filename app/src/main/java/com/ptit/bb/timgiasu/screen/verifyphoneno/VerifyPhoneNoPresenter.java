package com.ptit.bb.timgiasu.screen.verifyphoneno;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;

/**
 * The VerifyPhoneNo Presenter
 */
public class VerifyPhoneNoPresenter extends Presenter<VerifyPhoneNoContract.View, VerifyPhoneNoContract.Interactor>
        implements VerifyPhoneNoContract.Presenter {

    public VerifyPhoneNoPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public VerifyPhoneNoContract.View onCreateView() {
        return VerifyPhoneNoFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public VerifyPhoneNoContract.Interactor onCreateInteractor() {
        return new VerifyPhoneNoInteractor(this);
    }
}
