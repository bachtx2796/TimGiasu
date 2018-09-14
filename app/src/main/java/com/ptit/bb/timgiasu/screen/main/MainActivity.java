package com.ptit.bb.timgiasu.screen.main;

import com.gemvietnam.base.ContainerActivity;
import com.gemvietnam.base.viper.ViewFragment;
import com.ptit.bb.timgiasu.pushnotification.MyFirebaseMessagingService;

public class MainActivity extends ContainerActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        String grChatJson = getIntent().getStringExtra(MyFirebaseMessagingService.REQUEST);
        return (ViewFragment) new MainPresenter(this)
                .setGrChatFromNoti(grChatJson)
                .getFragment();
    }


}
