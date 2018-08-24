package com.ptit.bb.timgiasu.screen.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.gemvietnam.utils.ActivityUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.ptit.bb.timgiasu.screen.login.LoginActivity;
import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.ptit.bb.timgiasu.screen.main.MainActivity;

/**
 * The Splash Presenter
 */
public class SplashPresenter extends Presenter<SplashContract.View, SplashContract.Interactor>
        implements SplashContract.Presenter {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private static final int SPLASH_DISPLAY_LENGTH = 1000;

    public SplashPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public SplashContract.View onCreateView() {
        return SplashFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
        if (mAuth.getCurrentUser() != null){
            ActivityUtils.startActivity(getViewContext(), MainActivity.class);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(getViewContext(), LoginActivity.class);
                    getViewContext().startActivity(mainIntent);
                    getViewContext().finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }
    }

    @Override
    public SplashContract.Interactor onCreateInteractor() {
        return new SplashInteractor(this);
    }
}
