package com.ptit.bb.timgiasu.screen.map;

import com.gemvietnam.base.viper.ViewFragment;
import com.ptit.bb.timgiasu.R;

/**
 * The Map Fragment
 */
public class MapFragment extends ViewFragment<MapContract.Presenter> implements MapContract.View {

    public static MapFragment getInstance() {
        return new MapFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_map;
    }
}
