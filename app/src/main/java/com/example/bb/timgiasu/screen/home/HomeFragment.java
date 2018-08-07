package com.example.bb.timgiasu.screen.home;

import com.gemvietnam.base.viper.ViewFragment;
import com.example.bb.timgiasu.R;

/**
 * The Home Fragment
 */
public class HomeFragment extends ViewFragment<HomeContract.Presenter> implements HomeContract.View {

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }
}
