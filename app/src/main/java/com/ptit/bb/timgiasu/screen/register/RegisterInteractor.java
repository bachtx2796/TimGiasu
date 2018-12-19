package com.ptit.bb.timgiasu.screen.register;

import com.gemvietnam.base.viper.Interactor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * The Register interactor
 */
class RegisterInteractor extends Interactor<RegisterContract.Presenter>
        implements RegisterContract.Interactor {

    RegisterInteractor(RegisterContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void signup(String email, String pass, OnCompleteListener<AuthResult> onCompleteListener) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass).addOnCompleteListener(onCompleteListener);
    }
}
