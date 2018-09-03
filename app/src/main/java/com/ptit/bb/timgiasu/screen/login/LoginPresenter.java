package com.ptit.bb.timgiasu.screen.login;


import android.support.annotation.NonNull;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ptit.bb.timgiasu.Utils.DBConstan;
import com.ptit.bb.timgiasu.data.dto.UserDTO;
import com.ptit.bb.timgiasu.prewrapper.PrefWrapper;
import com.ptit.bb.timgiasu.screen.forgotpassword.ForgotPasswordPresenter;
import com.ptit.bb.timgiasu.screen.main.MainActivity;
import com.ptit.bb.timgiasu.screen.register.RegisterPresenter;
import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.gemvietnam.utils.ActivityUtils;

/**
 * The LoginFragment Presenter
 */
public class LoginPresenter extends Presenter<LoginContract.View, LoginContract.Interactor>
        implements LoginContract.Presenter {

    private FirebaseAuth mAuth;

    public LoginPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public LoginContract.View onCreateView() {
        return LoginFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public LoginContract.Interactor onCreateInteractor() {
        return new LoginInteractor(this);
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
                        if (!task.isSuccessful()) {
                            Toast.makeText(getViewContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            saveUser(task.getResult().getUser().getUid());
                        }
                    }
                });
    }

    private void saveUser(String id) {
        FirebaseDatabase.getInstance().getReference(DBConstan.USERS).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDTO userDTO = dataSnapshot.getValue(UserDTO.class);
                PrefWrapper.saveUser(getViewContext(), userDTO);
                mView.hideProgress();
                ActivityUtils.startActivity(getViewContext(), MainActivity.class);
                getViewContext().finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
