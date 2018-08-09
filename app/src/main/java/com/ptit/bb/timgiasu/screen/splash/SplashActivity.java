package com.ptit.bb.timgiasu.screen.splash;

import com.gemvietnam.base.ContainerActivity;
import com.gemvietnam.base.viper.ViewFragment;

/**
 * Created by BB on 8/5/2018.
 */

public class SplashActivity extends ContainerActivity {


    @Override
    public ViewFragment onCreateFirstFragment() {
        return (ViewFragment) new SplashPresenter(this).getFragment();
    }
}
