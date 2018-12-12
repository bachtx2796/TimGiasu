package com.ptit.bb.timgiasu.screen.tutors;


import android.support.v4.widget.SwipeRefreshLayout;

import com.ptit.bb.timgiasu.R;
import com.ptit.bb.timgiasu.Utils.VerticalSpaceItemDecoration;
import com.gemvietnam.base.viper.ViewFragment;

import com.gemvietnam.utils.RecyclerUtils;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The Tutors Fragment
 */
public class TutorsFragment extends ViewFragment<TutorsContract.Presenter> implements TutorsContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.tutor_rv)
    SuperRecyclerView mTutorRv;

    public static TutorsFragment getInstance() {
        return new TutorsFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tutors;
    }

    @Override
    public void initLayout() {
        super.initLayout();
    }


    @Override
    public void bindView(TutorAdapter mTutorAdapter) {
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.margin_5dp);
        mTutorRv.getRecyclerView().addItemDecoration(new VerticalSpaceItemDecoration(spacingInPixels));
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), mTutorRv.getRecyclerView());
        mTutorRv.setAdapter(mTutorAdapter);
        mTutorRv.setRefreshListener(this);
    }

    @OnClick(R.id.filter_ll)
    public void filter(){
        mPresenter.filter();
    }

    @Override
    public void onRefresh() {
        mPresenter.getData();
    }
}
