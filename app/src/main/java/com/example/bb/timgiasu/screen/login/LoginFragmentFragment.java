package com.example.bb.timgiasu.screen.login;

import com.gemvietnam.base.viper.ViewFragment;
import com.example.bb.timgiasu.R;

import butterknife.OnClick;

/**
 * The LoginFragment Fragment
 */
public class LoginFragmentFragment extends ViewFragment<LoginFragmentContract.Presenter> implements LoginFragmentContract.View {

    public static LoginFragmentFragment getInstance() {
        return new LoginFragmentFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @OnClick(R.id.forgotPasswordTv)
    public void displayForgotPassScreen(){
        mPresenter.displayForgotPassScreen();
    }

    @OnClick(R.id.signUpTv)
    public void displaySignupScreen(){
        mPresenter.displaySignupScreen();
    }

    @OnClick(R.id.loginTv)
    public void login(){
        mPresenter.login();
    }
}
