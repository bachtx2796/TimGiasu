package com.ptit.bb.timgiasu.screen.register;

import android.widget.Toast;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.google.firebase.FirebaseException;
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

    private FirebaseAuth mAuth;

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
    }

    @Override
    public RegisterContract.Interactor onCreateInteractor() {
        return new RegisterInteractor(this);
    }

    @Override
    public void signup(String fullname, String email, String phoneNo, String gender, String pass, String confirmpass, String dob) {
        PhoneAuthProvider.getInstance(mAuth).verifyPhoneNumber("+84" + phoneNo, 60, TimeUnit.SECONDS, getViewContext(), new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                new VerifyPhoneNoPresenter(mContainerView).pushView();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(getViewContext(), "Số điện thoại không tồn tại", Toast.LENGTH_SHORT).show();
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
}
