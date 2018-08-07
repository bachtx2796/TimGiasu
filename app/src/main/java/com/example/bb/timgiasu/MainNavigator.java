package com.example.bb.timgiasu;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.bb.timgiasu.screen.chat.ChatPresenter;
import com.example.bb.timgiasu.screen.home.HomePresenter;
import com.example.bb.timgiasu.screen.main.MainActivity;
import com.example.bb.timgiasu.screen.profile.ProfilePresenter;
import com.example.bb.timgiasu.screen.tutors.TutorsPresenter;
import com.gemvietnam.base.viper.Presenter;

import java.util.HashMap;

public class MainNavigator {

    private MainActivity mMainActivity;

    public static final int FRAME_CONTAINER_ID = R.id.main_content;
    //    private static final int dialog: CustomStyleIOSDialog? = null
    // Tabs ids
    public static final int TAB_HOME_ID = R.id.tab_home;
    public static final int TAB_TUTOR_ID = R.id.tab_tutors;
    //private static final int TAB_FILE_ID = R.id.tab_file;
    public static final int TAB_MESSAGES_ID = R.id.tab_chat;
    public static final int TAB_PROFILE_ID = R.id.tab_profile;

    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, Presenter> mMap = new HashMap<>();

    private FragmentManager mFragmentManager;

    public MainNavigator(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity;
        mFragmentManager = mMainActivity.getSupportFragmentManager();
    }

    public void showFragment(int fragmentId) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        for (Presenter presenter : mMap.values()) {
            transaction.hide(presenter.getFragment());
        }

        if (mMap.containsKey(fragmentId)) {
            transaction.show(mMap.get(fragmentId).getFragment());
//            if (fragmentId == TAB_FAVORITES_ID) {
//                (mMap[fragmentId]?.fragment).refresh();
//            }
//
//            if (fragmentId == TAB_HOME_ID) {
//                (mMap.get(fragmentId).getFragment()).refresh();
//            }
//
//            if (fragmentId == TAB_MESSAGES_ID) {
//                (mMap[fragmentId]?.fragment).refresh();
//            }
//
//            if (fragmentId == TAB_PROFILE_ID){
//                (mMap[fragmentId]?.fragment).refresh();
//            }

        } else {
            transaction.add(FRAME_CONTAINER_ID, createFragment(fragmentId));
        }

        transaction.commit();
    }

    private Fragment createFragment(int fragmentId) {

        Presenter presenter = null;
        switch (fragmentId) {
            case TAB_HOME_ID:
                presenter = new HomePresenter(mMainActivity);
                break;
            case TAB_TUTOR_ID:
                presenter = new TutorsPresenter(mMainActivity);
                break;
            case TAB_MESSAGES_ID:
                presenter = new ChatPresenter(mMainActivity);
                break;
            case TAB_PROFILE_ID:
                presenter = new ProfilePresenter(mMainActivity);
                break;
        }

        mMap.put(fragmentId, presenter);
        return presenter.getFragment();
    }

}
