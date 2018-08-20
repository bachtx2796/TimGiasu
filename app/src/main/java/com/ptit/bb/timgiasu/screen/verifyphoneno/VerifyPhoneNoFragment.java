package com.ptit.bb.timgiasu.screen.verifyphoneno;

import android.widget.EditText;
import android.widget.Toast;

import com.gemvietnam.base.viper.ViewFragment;
import com.ptit.bb.timgiasu.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The VerifyPhoneNo Fragment
 */
public class VerifyPhoneNoFragment extends ViewFragment<VerifyPhoneNoContract.Presenter> implements VerifyPhoneNoContract.View {

    @BindView(R.id.mUserNumber1Et)
    EditText mUserNumber1Et;
    @BindView(R.id.mUserNumber2Et)
    EditText mUserNumber2Et;
    @BindView(R.id.mUserNumber3Et)
    EditText mUserNumber3Et;
    @BindView(R.id.mUserNumber4Et)
    EditText mUserNumber4Et;
    @BindView(R.id.mUserNumber5Et)
    EditText mUserNumber5Et;
    @BindView(R.id.mUserNumber6Et)
    EditText mUserNumber6Et;

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

    @OnClick(R.id.resendPinTv)
    public void resendPin(){
        mPresenter.resendPin();
    }

    @OnClick(R.id.okBt)
    public void ok(){
        if (mUserNumber1Et.getText().toString().equals("")
                || mUserNumber2Et.getText().toString().equals("")
                || mUserNumber3Et.getText().toString().equals("")
                || mUserNumber4Et.getText().toString().equals("")
                || mUserNumber5Et.getText().toString().equals("")
                || mUserNumber6Et.getText().toString().equals("")) {
            Toast.makeText(getViewContext(), "Điền đầy đủ thông tin các trường", Toast.LENGTH_SHORT).show();
            return;
        }
        String code = (mUserNumber1Et.getText().toString()
                + mUserNumber2Et.getText().toString()
                + mUserNumber3Et.getText().toString()
                + mUserNumber4Et.getText().toString()
                + mUserNumber5Et.getText().toString()
                + mUserNumber6Et.getText().toString());
        mPresenter.checkSMS(code);
    }
}
