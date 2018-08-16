package com.ptit.bb.timgiasu.screen.register;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.ptit.bb.timgiasu.screen.map.MyMapPresenter;
import com.ptit.bb.timgiasu.screen.verifyphoneno.VerifyPhoneNoPresenter;

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

    @Override
    public void signup() {
        new VerifyPhoneNoPresenter(mContainerView).pushView();
    }

    @Override
    public void showMap() {
        new MyMapPresenter(mContainerView).pushView();
    }
}
