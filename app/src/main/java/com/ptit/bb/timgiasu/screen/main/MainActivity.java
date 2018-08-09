package com.ptit.bb.timgiasu.screen.main;

import com.gemvietnam.base.ContainerActivity;
import com.gemvietnam.base.viper.ViewFragment;

public class MainActivity extends ContainerActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        return (ViewFragment) new MainPresenter(this).getFragment();
    }
}
