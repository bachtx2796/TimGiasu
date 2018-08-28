package com.ptit.bb.timgiasu.screen.login;

import android.util.Log;

import com.gemvietnam.base.ContainerActivity;
import com.gemvietnam.base.viper.ViewFragment;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends ContainerActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        return (ViewFragment) new LoginPresenter(this).getFragment();
    }

}
