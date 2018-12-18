package com.ptit.bb.timgiasu.screen.changepassword;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.gemvietnam.base.viper.Interactor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * The ChangePassword interactor
 */
class ChangePasswordInteractor extends Interactor<ChangePasswordContract.Presenter>
        implements ChangePasswordContract.Interactor {

    ChangePasswordInteractor(ChangePasswordContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void savePass(String pass, OnCompleteListener onCompleteListener) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider
                .getCredential(user.getEmail(), pass);
        user.reauthenticate(credential).addOnCompleteListener(onCompleteListener);
    }
}
