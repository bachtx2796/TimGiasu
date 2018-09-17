package com.ptit.bb.timgiasu.screen.postdetail;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.gemvietnam.base.viper.ViewFragment;
import com.gemvietnam.utils.StringUtils;
import com.google.gson.Gson;
import com.ptit.bb.timgiasu.R;
import com.ptit.bb.timgiasu.Utils.DateTimeUtil;
import com.ptit.bb.timgiasu.data.dto.PostDTO;
import com.ptit.bb.timgiasu.prewrapper.PrefWrapper;
import com.ptit.bb.timgiasu.screen.viewimage.ShowImageDialog;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.edit_bt)
    TextView mEditBt;
    @BindView(R.id.mark_as_sold_bt)
    TextView mActionBt;

    public static PostDetailFragment getInstance() {
        return new PostDetailFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_post_detail;
    }

    @Override
    public void bindView(PostDTO mPost) {
        if (mPost.getIdUser().equals(PrefWrapper.getUser(getViewContext()).getId())) {
            mEditBt.setVisibility(View.VISIBLE);
            mChatBt.setVisibility(View.GONE);
        } else {
            mEditBt.setVisibility(View.GONE);
            mChatBt.setVisibility(View.VISIBLE);
        }
        List<String> tmp = new ArrayList<>();
        for (String link : mPost.getUris()) {
            if (!StringUtils.isEmpty(link)) {
                tmp.add(link);
            }
        }
        ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter(getViewContext(), tmp);
        imagePagerAdapter.setmOnItemImageClickListener(new ImagePagerAdapter.OnItemImageClickListener() {
            @Override
            public void onItemClick(List<String> list, int position) {
                ShowImageDialog showImageDialog = new ShowImageDialog(getViewContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen, list, position);
                showImageDialog.show();
            }
        });
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
            if (mPost.getStatus().equals("Đăng tuyển")) {
                mActionBt.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void sendRequestSuccess() {
        mActionBt.setEnabled(false);
        mActionBt.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.button_red_border_bg));
        mActionBt.setTextColor(ContextCompat.getColor(getViewContext(), R.color.red));
        mActionBt.setText("Đã gửi yêu cầu");
    }

    @OnClick(R.id.back_iv)
    public void back() {
        mPresenter.back();
    }

    @OnClick(R.id.edit_bt)
    public void editPost() {
        mPresenter.editPost();
    }

    @OnClick({R.id.mark_as_sold_bt})
    public void doAction() {
        String type = mActionBt.getText().toString();
        switch (type) {
            case "Nhận":
                mPresenter.sentRequest();
                break;
        }

    }

    @OnClick(R.id.chat_bt)
    public void createGrChat() {
        mPresenter.createGrChat();
    }
}
