package com.ptit.bb.timgiasu.screen.history;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gemvietnam.base.viper.ViewFragment;
import com.gemvietnam.utils.RecyclerUtils;
import com.ptit.bb.timgiasu.R;
import com.ptit.bb.timgiasu.Utils.SpacesItemDecoration;
import com.ptit.bb.timgiasu.data.dto.PostDTO;
import com.ptit.bb.timgiasu.prewrapper.PrefWrapper;
import com.ptit.bb.timgiasu.screen.home.HomeAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The History Fragment
 */
public class HistoryFragment extends ViewFragment<HistoryContract.Presenter> implements HistoryContract.View {

    @BindView(R.id.post_rv)
    RecyclerView mPostRv;
    @BindView(R.id.title_my_class_tv)
    TextView mTitleMyClassTv;
    @BindView(R.id.my_class_rv)
    RecyclerView mMyClassRv;

    private List<PostDTO> mPosts;


    private List<PostDTO> mMyClasses;
    private HomeAdapter mMyClassAdapter;

    public static HistoryFragment getInstance() {
        return new HistoryFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_history;
    }

    @Override
    public void initLayout() {
        super.initLayout();
    }

    @Override
    public void bindPost(HomeAdapter adapter, HomeAdapter mReceviePostAdapter) {
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.margin_5dp);
        mPostRv.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        RecyclerUtils.setupGridRecyclerView(getViewContext(), mPostRv, 2);
        mPostRv.setAdapter(adapter);
        if (!PrefWrapper.getUser(getViewContext()).isTutor()) {
            mTitleMyClassTv.setVisibility(View.GONE);
            mMyClassRv.setVisibility(View.GONE);
        } else {
            mTitleMyClassTv.setVisibility(View.VISIBLE);
            mMyClassRv.setVisibility(View.VISIBLE);
            mMyClassRv.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
            RecyclerUtils.setupGridRecyclerView(getViewContext(), mMyClassRv, 2);
            mMyClassRv.setAdapter(mReceviePostAdapter);
        }
    }

    @OnClick(R.id.back_iv)
    public void back() {
        mPresenter.back();
    }
}
