package com.example.bb.timgiasu.screen.register;

import com.gemvietnam.base.viper.ViewFragment;
import com.example.bb.timgiasu.R;

import butterknife.OnClick;

/**
 * The Register Fragment
 */
public class RegisterFragment extends ViewFragment<RegisterContract.Presenter> implements RegisterContract.View {

    public static RegisterFragment getInstance() {
        return new RegisterFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @OnClick(R.id.backIv)
    public void back(){
        mPresenter.back();
    }
}
