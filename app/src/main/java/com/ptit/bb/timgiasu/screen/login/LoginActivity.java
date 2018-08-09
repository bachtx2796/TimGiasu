package com.ptit.bb.timgiasu.screen.login;

import com.gemvietnam.base.ContainerActivity;
import com.gemvietnam.base.viper.ViewFragment;

public class LoginActivity extends ContainerActivity{
    @Override
    public ViewFragment onCreateFirstFragment() {
        return (ViewFragment) new LoginFragmentPresenter(this).getFragment();
    }
}
