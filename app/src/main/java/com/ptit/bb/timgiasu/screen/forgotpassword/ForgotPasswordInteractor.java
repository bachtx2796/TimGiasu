package com.ptit.bb.timgiasu.screen.forgotpassword;

import com.gemvietnam.base.viper.Interactor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;

/**
 * The ForgotPassword interactor
 */
class ForgotPasswordInteractor extends Interactor<ForgotPasswordContract.Presenter>
        implements ForgotPasswordContract.Interactor {

    ForgotPasswordInteractor(ForgotPasswordContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void sendLinkToEmail(String email, OnCompleteListener<Void> onCompleteListener) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(onCompleteListener);
    }
}
