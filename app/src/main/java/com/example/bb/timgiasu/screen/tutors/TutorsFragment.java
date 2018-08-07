package com.example.bb.timgiasu.screen.tutors;

import com.gemvietnam.base.viper.ViewFragment;
import com.example.bb.timgiasu.R;

/**
 * The Tutors Fragment
 */
public class TutorsFragment extends ViewFragment<TutorsContract.Presenter> implements TutorsContract.View {

    public static TutorsFragment getInstance() {
        return new TutorsFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tutors;
    }
}
