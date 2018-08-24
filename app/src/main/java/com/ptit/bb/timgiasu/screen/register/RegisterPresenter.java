package com.ptit.bb.timgiasu.screen.register;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.ptit.bb.timgiasu.screen.map.MyMapPresenter;
import com.ptit.bb.timgiasu.screen.verifyphoneno.VerifyPhoneNoPresenter;

import java.util.concurrent.TimeUnit;

/**
 * The Register Presenter
 */
public class RegisterPresenter extends Presenter<RegisterContract.View, RegisterContract.Interactor>
        implements RegisterContract.Presenter {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public RegisterPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public RegisterContract.View onCreateView() {
        return RegisterFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
        getListUserFromDB();
    }

    @Override
    public RegisterContract.Interactor onCreateInteractor() {
        return new RegisterInteractor(this);
    }

    @Override
    public void signup(String fullname, final String email, final String phoneNo, String gender, final String pass, String confirmpass, String dob) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+84" + phoneNo, 60, TimeUnit.SECONDS, getViewContext(), new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(getViewContext(), "Số điện thoại không tồn tại", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                new VerifyPhoneNoPresenter(mContainerView)
                        .setPhoneNo("+84"+phoneNo)
                        .setVerificationID(verificationId)
                        .setOnVerifyPhoneNumberListener(new VerifyPhoneNoPresenter.OnVerifyPhoneNumberListener() {
                            @Override
                            public void onVerifyPhoneNumberSuccessful() {
                                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d("@@@@", "createUserWithEmail:success");
                                            // save user into db

                                            task.getResult().getUser();
                                            back();
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w("@@@@", "createUserWithEmail:failure", task.getException());
                                            Toast.makeText(getViewContext(), "Đăng kí không thành công",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onVerifyPhoneNumberFailed() {

                            }
                        })
                        .pushView();
            }
        });


    }

    @Override
    public void showMap() {
        MyMapPresenter presenter = new MyMapPresenter(mContainerView);
        presenter.setmOnLocationSelectedListener(new MyMapPresenter.OnLocationSelectedListener() {
            @Override
            public void onItemSelected(String location) {
                mView.setLocation(location);
            }
        });
        presenter.pushView();
    }

    public void getListUserFromDB() {

    }
}
