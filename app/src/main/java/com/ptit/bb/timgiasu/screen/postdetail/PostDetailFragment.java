package com.ptit.bb.timgiasu.screen.postdetail;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.gemvietnam.base.viper.ViewFragment;
import com.ptit.bb.timgiasu.R;
import com.ptit.bb.timgiasu.Utils.DateTimeUtil;
import com.ptit.bb.timgiasu.data.dto.PostDTO;
import com.ptit.bb.timgiasu.prewrapper.PrefWrapper;
import com.viewpagerindicator.CirclePageIndicator;

import butterknife.BindView;

/**
 * The PostDetail Fragment
 */
public class PostDetailFragment extends ViewFragment<PostDetailContract.Presenter> implements PostDetailContract.View {

    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.time_create_tv)
    TextView mtimeCreateTv;
    @BindView(R.id.time_tv)
    TextView mTimeTv;
    @BindView(R.id.salary_tv)
    TextView mSalaryTv;
    @BindView(R.id.address_tv)
    TextView mAddressTv;
    @BindView(R.id.viewPager)
    ViewPager mImageViewPager;
    @BindView(R.id.circle_page_indicator)
    CirclePageIndicator mIndicator;
    @BindView(R.id.chat_bt)
    TextView mChatBt;

    public static PostDetailFragment getInstance() {
        return new PostDetailFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_post_detail;
    }

    @Override
    public void bindView(PostDTO mPost) {
        ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter(getViewContext(), mPost.getUris());
        mImageViewPager.setAdapter(imagePagerAdapter);
        mIndicator.setViewPager(mImageViewPager);
        mTitleTv.setText("Lớp: " + mPost.getClasses().toString());
        mtimeCreateTv.setText(DateTimeUtil.longToString(mPost.getTimecreate()));
        mTimeTv.setText(mPost.getTime());
        mSalaryTv.setText(mPost.getSalary());
        mAddressTv.setText(mPost.getAddress());
        if (!mPost.getIdUser().equals(PrefWrapper.getUser(getViewContext()).getId())) {
            mChatBt.setVisibility(View.VISIBLE);
        } else {
            mChatBt.setVisibility(View.GONE);
        }
    }
}
