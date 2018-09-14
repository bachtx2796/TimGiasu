package com.ptit.bb.timgiasu.screen.splash;

import android.content.Intent;

import com.gemvietnam.base.ContainerActivity;
import com.gemvietnam.base.viper.ViewFragment;
import com.ptit.bb.timgiasu.pushnotification.MyFirebaseMessagingService;

/**
 * Created by BB on 8/5/2018.
 */

public class SplashActivity extends ContainerActivity {

    @Override
    public ViewFragment onCreateFirstFragment() {
        String grChatJson = getIntent().getStringExtra(MyFirebaseMessagingService.REQUEST);
        return (ViewFragment) new SplashPresenter(this).setLinkGrChatGromNoti(grChatJson).getFragment();
    }
}
