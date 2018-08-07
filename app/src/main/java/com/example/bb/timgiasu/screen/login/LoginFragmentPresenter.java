package com.example.bb.timgiasu.screen.login;

import com.example.bb.timgiasu.screen.forgotpassword.ForgotPasswordPresenter;
import com.example.bb.timgiasu.screen.main.MainActivity;
import com.example.bb.timgiasu.screen.register.RegisterPresenter;
import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.gemvietnam.utils.ActivityUtils;

/**
 * The LoginFragment Presenter
 */
public class LoginFragmentPresenter extends Presenter<LoginFragmentContract.View, LoginFragmentContract.Interactor>
        implements LoginFragmentContract.Presenter {

    public LoginFragmentPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public LoginFragmentContract.View onCreateView() {
        return LoginFragmentFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public LoginFragmentContract.Interactor onCreateInteractor() {
        return new LoginFragmentInteractor(this);
    }

    @Override
    public void displayForgotPassScreen() {
        new ForgotPasswordPresenter(mContainerView).pushView();
    }

    @Override
    public void displaySignupScreen() {
        new RegisterPresenter(mContainerView).pushView();
    }

    @Override
    public void login() {
        ActivityUtils.startActivity(getViewContext(), MainActivity.class);
    }
}
