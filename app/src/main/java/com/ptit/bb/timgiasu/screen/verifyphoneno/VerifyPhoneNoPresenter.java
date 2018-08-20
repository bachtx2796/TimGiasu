package com.ptit.bb.timgiasu.screen.verifyphoneno;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.gemvietnam.utils.DialogUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.ptit.bb.timgiasu.customview.Confirmdialog;

import java.util.concurrent.TimeUnit;

/**
 * The VerifyPhoneNo Presenter
 */
public class VerifyPhoneNoPresenter extends Presenter<VerifyPhoneNoContract.View, VerifyPhoneNoContract.Interactor>
        implements VerifyPhoneNoContract.Presenter {

    private String mVerificationID;
    private String mUserPhoneNumber;

    private OnVerifyPhoneNumberListener mOnVerifyPhoneNumberListener;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public VerifyPhoneNoPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public VerifyPhoneNoContract.View onCreateView() {
        return VerifyPhoneNoFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public VerifyPhoneNoContract.Interactor onCreateInteractor() {
        return new VerifyPhoneNoInteractor(this);
    }

    public VerifyPhoneNoPresenter setOnVerifyPhoneNumberListener(OnVerifyPhoneNumberListener onVerifyPhoneNumberListener) {
        this.mOnVerifyPhoneNumberListener = onVerifyPhoneNumberListener;
        return this;
    }

    public VerifyPhoneNoPresenter setPhoneNo(String phoneNo) {
        this.mUserPhoneNumber = phoneNo;
        return this;
    }

    public VerifyPhoneNoPresenter setVerificationID(String verificationID) {
        this.mVerificationID = verificationID;
        return this;
    }

    @Override
    public void resendPin() {
        //TODO resend pin
        mView.showProgress();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mUserPhoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                getViewContext(),               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {

                    }

                    @Override
                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        mVerificationID = verificationId;
                        mView.hideProgress();
                        Log.d("SignUpPresenter", "onCodeSent:" + verificationId);
                    }
                });
    }

    @Override
    public void checkSMS(String code) {
        // TODO check sms
        if (!mVerificationID.isEmpty()) {
            DialogUtils.showProgressDialog(getViewContext());
            AuthCredential credential = PhoneAuthProvider.getCredential(mVerificationID, code);
            signInWithPhoneAuthCredential(credential);
        } else {
            Toast.makeText(getViewContext(), "Mã pin hết hạn, hãy gửi lại mã pin.", Toast.LENGTH_SHORT).show();
        }
    }

    private void signInWithPhoneAuthCredential(AuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getViewContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("@@@@@", "signInWithCredential:success");
                            DialogUtils.dismissProgressDialog();

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                            }
                                        }
                                    });

                            mOnVerifyPhoneNumberListener.onVerifyPhoneNumberSuccessful();

                            back();
                        } else {
                            // Sign in failed, display a message and update the UI
                            DialogUtils.dismissProgressDialog();
                            Log.e("@@@@@@", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    public interface OnVerifyPhoneNumberListener {
        void onVerifyPhoneNumberSuccessful();

        void onVerifyPhoneNumberFailed();
    }
}
