package com.ptit.bb.timgiasu.screen.login;


import android.support.annotation.NonNull;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.ptit.bb.timgiasu.data.dto.UserDTO;
import com.ptit.bb.timgiasu.screen.forgotpassword.ForgotPasswordPresenter;
import com.ptit.bb.timgiasu.screen.main.MainActivity;
import com.ptit.bb.timgiasu.screen.register.RegisterPresenter;
import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.gemvietnam.utils.ActivityUtils;

/**
 * The LoginFragment Presenter
 */
public class LoginFragmentPresenter extends Presenter<LoginFragmentContract.View, LoginFragmentContract.Interactor>
        implements LoginFragmentContract.Presenter {

    private FirebaseAuth mAuth;

    public LoginFragmentPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public LoginFragmentContract.View onCreateView() {
        return LoginFragmentFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public LoginFragmentContract.Interactor onCreateInteractor() {
        return new LoginFragmentInteractor(this);
    }

    @Override
    public void displayForgotPassScreen() {
        new ForgotPasswordPresenter(mContainerView).pushView();
    }

    @Override
    public void displaySignupScreen() {
        new RegisterPresenter(mContainerView).pushView();
    }

    @Override
    public void login(String username, String pass) {
        mView.showProgress();
        mAuth.signInWithEmailAndPassword(username, pass)
                .addOnCompleteListener(getViewContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        mView.hideProgress();
                        if (!task.isSuccessful()) {
                            Toast.makeText(getViewContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            UserDTO userDTO = getUser(task.getResult().getUser().getUid());
                            ActivityUtils.startActivity(getViewContext(), MainActivity.class);
                            getViewContext().finish();
                        }
                    }
                });
    }

    private UserDTO getUser(String uid) {
        return null;
    }
}
