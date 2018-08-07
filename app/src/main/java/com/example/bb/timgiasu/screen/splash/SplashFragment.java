package com.example.bb.timgiasu.screen.splash;

import com.gemvietnam.base.viper.ViewFragment;
import com.example.bb.timgiasu.R;

/**
 * The Splash Fragment
 */
public class SplashFragment extends ViewFragment<SplashContract.Presenter> implements SplashContract.View {

    public static SplashFragment getInstance() {
        return new SplashFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_splash;
    }
}
