package com.ptit.bb.timgiasu.screen.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.gemvietnam.utils.ActivityUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.ptit.bb.timgiasu.prewrapper.PrefWrapper;
import com.ptit.bb.timgiasu.pushnotification.MyFirebaseMessagingService;
import com.ptit.bb.timgiasu.screen.login.LoginActivity;
import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.ptit.bb.timgiasu.screen.main.MainActivity;

/**
 * The Splash Presenter
 */
public class SplashPresenter extends Presenter<SplashContract.View, SplashContract.Interactor>
        implements SplashContract.Presenter {

    private String mGrChatJson = "";

    private static final int SPLASH_DISPLAY_LENGTH = 1000;

    public SplashPresenter(ContainerView containerView) {
        super(containerView);
    }

    public SplashPresenter setLinkGrChatGromNoti(String grChatJson) {
        this.mGrChatJson = grChatJson;
        return this;
    }

    @Override
    public SplashContract.View onCreateView() {
        return SplashFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
        if (PrefWrapper.getUser(getViewContext()) != null) {
            Intent mainIntent = new Intent(getViewContext(), MainActivity.class);
            mainIntent.putExtra(MyFirebaseMessagingService.REQUEST,mGrChatJson);
            getViewContext().startActivity(mainIntent);
            getViewContext().finish();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    /* Create an Intent that will start the Menu-Activity. */
                    Intent loginIntent = new Intent(getViewContext(), LoginActivity.class);
                    getViewContext().startActivity(loginIntent);
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
