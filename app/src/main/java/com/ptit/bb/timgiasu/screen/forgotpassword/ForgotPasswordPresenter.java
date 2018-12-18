package com.ptit.bb.timgiasu.screen.forgotpassword;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * The ForgotPassword Presenter
 */
public class ForgotPasswordPresenter extends Presenter<ForgotPasswordContract.View, ForgotPasswordContract.Interactor>
        implements ForgotPasswordContract.Presenter {

    public ForgotPasswordPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public ForgotPasswordContract.View onCreateView() {
        return ForgotPasswordFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public ForgotPasswordContract.Interactor onCreateInteractor() {
        return new ForgotPasswordInteractor(this);
    }

    @Override
    public void requestResetPass(String email) {
        mView.showProgress();
        mInteractor.sendLinkToEmail(email, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mView.hideProgress();
                    Toast.makeText(getViewContext(), "Chúng tôi đã gửi yêu cầu đặt lại mật khẩu đến email của bạn!", Toast.LENGTH_SHORT).show();
                } else {
                    mView.hideProgress();
                    Toast.makeText(getViewContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
