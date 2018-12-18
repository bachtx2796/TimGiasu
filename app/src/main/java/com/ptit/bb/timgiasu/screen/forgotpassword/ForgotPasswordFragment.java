package com.ptit.bb.timgiasu.screen.forgotpassword;

import android.widget.EditText;
import android.widget.Toast;

import com.gemvietnam.base.viper.ViewFragment;
import com.gemvietnam.utils.StringUtils;
import com.ptit.bb.timgiasu.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The ForgotPassword Fragment
 */
public class ForgotPasswordFragment extends ViewFragment<ForgotPasswordContract.Presenter> implements ForgotPasswordContract.View {

    @BindView(R.id.email_address_et)
    EditText mEmailEt;

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

    @OnClick(R.id.send_reset_password_bt)
    public void requestResetPass(){
        String email = mEmailEt.getText().toString();
        if (StringUtils.isEmpty(email)){
            Toast.makeText(getViewContext(), "Chưa nhập địa chỉ email", Toast.LENGTH_SHORT).show();
            return;
        }
        mPresenter.requestResetPass(email);
    }
}
