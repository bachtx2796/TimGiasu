package com.ptit.bb.timgiasu.screen.changepassword;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * The ChangePassword Presenter
 */
public class ChangePasswordPresenter extends Presenter<ChangePasswordContract.View, ChangePasswordContract.Interactor>
        implements ChangePasswordContract.Presenter {

    private static final String TAG = ChangePasswordPresenter.class.getSimpleName();

    private FirebaseAuth mAuth;

    public ChangePasswordPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public ChangePasswordContract.View onCreateView() {
        return ChangePasswordFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public ChangePasswordContract.Interactor onCreateInteractor() {
        return new ChangePasswordInteractor(this);
    }

    @Override
    public void savePass(String pass, final String newPass) {
        final FirebaseUser user = mAuth.getCurrentUser();
        AuthCredential credential = EmailAuthProvider
                .getCredential(user.getEmail(), pass);
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getViewContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Password updated");
                            } else {
                                Toast.makeText(getViewContext(), task.getException() + "", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Error password not updated" + task.getException());
                            }
                        }
                    });
                } else {
                    Toast.makeText(getViewContext(), task.getException() + "", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Error " + task.getException());
                }
            }
        });
    }
}
