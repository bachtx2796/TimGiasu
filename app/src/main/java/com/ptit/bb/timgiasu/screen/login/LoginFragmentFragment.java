package com.ptit.bb.timgiasu.screen.login;

import android.widget.EditText;
import android.widget.Toast;

import com.gemvietnam.base.viper.ViewFragment;
import com.ptit.bb.timgiasu.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The LoginFragment Fragment
 */
public class LoginFragmentFragment extends ViewFragment<LoginFragmentContract.Presenter> implements LoginFragmentContract.View {

    @BindView(R.id.userNameEt)
    EditText mUsernameEt;
    @BindView(R.id.passwordEt)
    EditText mPassEt;

    public static LoginFragmentFragment getInstance() {
        return new LoginFragmentFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @OnClick(R.id.forgotPasswordTv)
    public void displayForgotPassScreen(){
        mPresenter.displayForgotPassScreen();
    }

    @OnClick(R.id.signUpTv)
    public void displaySignupScreen(){
        mPresenter.displaySignupScreen();
    }

    @OnClick(R.id.loginTv)
    public void login(){
        String username = mUsernameEt.getText().toString();
        String pass = mPassEt.getText().toString();

        if("".equals(username) || "".equals(pass)){
            Toast.makeText(getViewContext(), "Chưa điền đủ thông tin đăng nhập.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isEmailValid(username)){
            Toast.makeText(getViewContext(), "Sai đinh dạng email", Toast.LENGTH_SHORT).show();
            return;
        }

        mPresenter.login(username,pass);
    }

    private boolean isEmailValid(String email){
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
