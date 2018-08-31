package com.ptit.bb.timgiasu.screen.changepassword;

import android.widget.EditText;
import android.widget.Toast;

import com.gemvietnam.base.viper.ViewFragment;
import com.gemvietnam.utils.StringUtils;
import com.ptit.bb.timgiasu.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The ChangePassword Fragment
 */
public class ChangePasswordFragment extends ViewFragment<ChangePasswordContract.Presenter> implements ChangePasswordContract.View {

    @BindView(R.id.currentPasswordEt)
    EditText mCurrentPassEt;
    @BindView(R.id.newPasswordEt)
    EditText mNewPassEt;
    @BindView(R.id.confirmNewPasswordEt)
    EditText mConfirmNewPassEt;


    public static ChangePasswordFragment getInstance() {
        return new ChangePasswordFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_change_password;
    }

    @OnClick(R.id.backIv)
    public void back() {
        mPresenter.back();
    }

    @OnClick(R.id.saveChangePasswordTv)
    public void savePass() {
        String pass = mCurrentPassEt.getText().toString();
        String newpass = mNewPassEt.getText().toString();
        String confirmpass = mConfirmNewPassEt.getText().toString();

        if (StringUtils.isEmpty(pass) || StringUtils.isEmpty(newpass) || StringUtils.isEmpty(confirmpass)) {
            Toast.makeText(getViewContext(), "Nhập đủ thông tin các trường", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newpass.equals(confirmpass)) {
            Toast.makeText(getViewContext(), "Xác nhận mật khẩu mới không thành công", Toast.LENGTH_SHORT).show();
            return;
        }

        mPresenter.savePass(pass, newpass);
    }

}
