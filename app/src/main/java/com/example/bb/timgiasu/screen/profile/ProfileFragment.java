package com.example.bb.timgiasu.screen.profile;

import com.gemvietnam.base.viper.ViewFragment;
import com.example.bb.timgiasu.R;

/**
 * The Profile Fragment
 */
public class ProfileFragment extends ViewFragment<ProfileContract.Presenter> implements ProfileContract.View {

    public static ProfileFragment getInstance() {
        return new ProfileFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile;
    }
}
