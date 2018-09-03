package com.ptit.bb.timgiasu.screen.tutorprofile;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.gemvietnam.base.viper.ViewFragment;
import com.gemvietnam.utils.PermissionUtils;

import android.Manifest;

import com.google.firebase.database.FirebaseDatabase;
import com.ptit.bb.timgiasu.R;
import com.ptit.bb.timgiasu.customview.RatingDialog;
import com.ptit.bb.timgiasu.data.dto.UserDTO;
import com.ptit.bb.timgiasu.prewrapper.PrefWrapper;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The TutorProfile Fragment
 */
public class TutorProfileFragment extends ViewFragment<TutorProfileContract.Presenter> implements TutorProfileContract.View {

    @BindView(R.id.avatar_iv)
    SimpleDraweeView mAvatarIv;
    @BindView(R.id.name_tv)
    TextView mNameTv;
    @BindView(R.id.email_tv)
    TextView mEmailTv;
    @BindView(R.id.mobile_tv)
    TextView mMobileTv;
    @BindView(R.id.gender_tv)
    TextView mGenderTv;
    @BindView(R.id.dob_tv)
    TextView mDobTv;
    @BindView(R.id.address_tv)
    TextView mAddressTv;
    @BindView(R.id.class_tv)
    TextView mClassTv;
    @BindView(R.id.subject_tv)
    TextView mSubjectTv;
    @BindView(R.id.image1)
    SimpleDraweeView mImage1;
    @BindView(R.id.image2)
    SimpleDraweeView mImage2;
    @BindView(R.id.image3)
    SimpleDraweeView mImage3;
    @BindView(R.id.time_tv)
    TextView mTimeTv;
    @BindView(R.id.servicefee_tv)
    TextView mSalaryTv;

    private static final int CALL_PHONE = 101;

    public static TutorProfileFragment getInstance() {
        return new TutorProfileFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tutor_profile;
    }

    @Override
    public void bindTutor(UserDTO mUser) {
        mAvatarIv.setImageURI(mUser.getAvatar());
        mNameTv.setText(mUser.getName());
        mEmailTv.setText(mUser.getEmail());
        if (mUser.getPhoneNo().charAt(0) == '0') {
            mMobileTv.setText(mUser.getPhoneNo());
        } else {
            mMobileTv.setText("0" + mUser.getPhoneNo());
        }
        mGenderTv.setText(mUser.getGender());
        mDobTv.setText(mUser.getDob());
        mAddressTv.setText(mUser.getAddress());
        mClassTv.setText(mUser.getClasses().toString());
        mSubjectTv.setText(mUser.getSubjects().toString());
        mTimeTv.setText(mUser.getTime());
        mSalaryTv.setText(mUser.getSalary() + "");
        if (!mUser.getUris().get(0).equals("")) {
            mImage1.setImageURI(mUser.getUris().get(0));
        } else {
            mImage1.setVisibility(View.GONE);
        }
        if (!mUser.getUris().get(1).equals("")) {
            mImage2.setImageURI(mUser.getUris().get(1));
        } else {
            mImage2.setVisibility(View.GONE);
        }
        if (!mUser.getUris().get(2).equals("")) {
            mImage3.setImageURI(mUser.getUris().get(2));
        } else {
            mImage3.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.back_iv)
    public void back() {
        mPresenter.back();
    }

    @OnClick(R.id.contact_bt)
    public void contact() {
        if (!PermissionUtils.needRequestPermissions(getViewContext(), this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE)) {
            mPresenter.contact();
        }
    }

    @OnClick(R.id.rating_bt)
    public void rating() {
        int rate = 0;
        if (mPresenter.getmUser().getRatings() != null){
            rate = mPresenter.getmUser().getRatings().get(PrefWrapper.getUser(getViewContext()).getId());
        }
        RatingDialog ratingDialog = new RatingDialog(getViewContext(), rate);
        ratingDialog.setmOnRatingListener(new RatingDialog.OnRatingListener() {
            @Override
            public void onRating(float rate) {
                mPresenter.saveRating(rate);
            }
        });
        ratingDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PHONE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                mPresenter.contact();
        }
    }
}
