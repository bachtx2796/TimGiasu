package com.ptit.bb.timgiasu.screen.favourite;

import com.gemvietnam.base.viper.ViewFragment;
import com.ptit.bb.timgiasu.R;

/**
 * The Favourite Fragment
 */
public class FavouriteFragment extends ViewFragment<FavouriteContract.Presenter> implements FavouriteContract.View {

    public static FavouriteFragment getInstance() {
        return new FavouriteFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_favourite;
    }
}
