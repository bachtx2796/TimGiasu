package com.ptit.bb.timgiasu.screen.home;

import android.support.v4.widget.SwipeRefreshLayout;

import com.gemvietnam.base.viper.ViewFragment;
import com.gemvietnam.utils.RecyclerUtils;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.ptit.bb.timgiasu.R;
import com.ptit.bb.timgiasu.Utils.SpacesItemDecoration;

import butterknife.BindView;

/**
 * The Home Fragment
 */
public class HomeFragment extends ViewFragment<HomeContract.Presenter> implements HomeContract.View {

    @BindView(R.id.homeRv)
    SuperRecyclerView mHomeRv;

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void bindView(HomeAdapter mHomeAdapter) {
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.margin_5dp);
        mHomeRv.getRecyclerView().addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        RecyclerUtils.setupGridRecyclerView(getViewContext(), mHomeRv.getRecyclerView(), 2);
        mHomeRv.setAdapter(mHomeAdapter);
    }

    @Override
    public void initLayout() {
        super.initLayout();

        mHomeRv.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshData();
            }
        });
    }
}
