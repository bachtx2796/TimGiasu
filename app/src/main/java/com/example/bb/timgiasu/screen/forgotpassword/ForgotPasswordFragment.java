package com.example.bb.timgiasu.screen.forgotpassword;

import com.gemvietnam.base.viper.ViewFragment;
import com.example.bb.timgiasu.R;

import butterknife.OnClick;

/**
 * The ForgotPassword Fragment
 */
public class ForgotPasswordFragment extends ViewFragment<ForgotPasswordContract.Presenter> implements ForgotPasswordContract.View {

    public static ForgotPasswordFragment getInstance() {
        return new ForgotPasswordFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_forgot_password;
    }

    @OnClick(R.id.backIv)
    public void back(){
        mPresenter.back();
    }
}
