package com.ptit.bb.timgiasu.screen.verifyphoneno;

import com.gemvietnam.base.viper.ViewFragment;
import com.ptit.bb.timgiasu.R;

import butterknife.OnClick;

/**
 * The VerifyPhoneNo Fragment
 */
public class VerifyPhoneNoFragment extends ViewFragment<VerifyPhoneNoContract.Presenter> implements VerifyPhoneNoContract.View {

    public static VerifyPhoneNoFragment getInstance() {
        return new VerifyPhoneNoFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_verify_phone_no;
    }

    @OnClick(R.id.backIv)
    public void back(){
        mPresenter.back();
    }


}
