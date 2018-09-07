package com.ptit.bb.timgiasu.screen.main;

import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ptit.bb.timgiasu.MainNavigator;
import com.gemvietnam.base.viper.ViewFragment;
import com.ptit.bb.timgiasu.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The Main Fragment
 */
public class MainFragment extends ViewFragment<MainContract.Presenter> implements MainContract.View {


    @BindView(R.id.bottom_bar_home_iv)
    ImageView mHomeIv;
    @BindView(R.id.bottom_bar_tutors_iv)
    ImageView mTutorsIv;
    @BindView(R.id.bottom_bar_messages_iv)
    ImageView mMessageIv;
    @BindView(R.id.bottom_bar_profile_iv)
    ImageView mProfileIv;

    @BindView(R.id.bottomBar)
    BottomBar bottomBar;


    private MainNavigator mNavigator;

    public static MainFragment getInstance() {
        return new MainFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void initLayout() {
        super.initLayout();

        mNavigator = new MainNavigator((MainActivity) getActivity());

        initBottomBar();

    }

    private void initBottomBar() {
        bottomBar.selectTabAtPosition(0);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switchTabs(tabId);
            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(int tabId) {
                switchTabs(tabId);
            }
        });
    }

    private void switchTabs(int tabId) {
        mNavigator.showFragment(tabId);
        switch (tabId) {
            case MainNavigator.TAB_HOME_ID:
                handleBottomBarState(true, false, false, false);
                break;
            case MainNavigator.TAB_TUTOR_ID:
                handleBottomBarState(false, true, false, false);
                break;
            case MainNavigator.TAB_MESSAGES_ID:
                handleBottomBarState(false, false, true, false);
                break;
            case MainNavigator.TAB_PROFILE_ID:
                handleBottomBarState(false, false, false, true);
                break;
        }
    }

    private void handleBottomBarState(boolean home, boolean favorite, boolean chat, boolean profile) {
        mHomeIv.setImageResource((home) ? R.drawable.ic_home_selected : R.drawable.ic_home);

        mTutorsIv.setImageResource((favorite) ? R.drawable.ic_tutor : R.drawable.ic_tutor_unselected);

        mMessageIv.setImageResource((chat) ? R.drawable.ic_chat : R.drawable.ic_unseleted_chat);

        mProfileIv.setImageResource((profile) ? R.drawable.ic_profile_selected : R.drawable.ic_profile);

    }

    @OnClick(R.id.bottom_bar_home_rl)
    public void displayHome(){
        switchTabs(MainNavigator.TAB_HOME_ID);
    }

    @OnClick(R.id.bottom_bar_tutors_rl)
    public void displayTutors(){
        switchTabs(MainNavigator.TAB_TUTOR_ID);
    }

    @OnClick(R.id.bottom_bar_messages_rl)
    public void displayMess(){
        switchTabs(MainNavigator.TAB_MESSAGES_ID);
    }

    @OnClick(R.id.bottom_bar_profile_rl)
    public void displayProfile(){
        switchTabs(MainNavigator.TAB_PROFILE_ID);
    }

    @OnClick(R.id.bottom_bar_post_rl)
    public void post(){
        mPresenter.newPost();
    }

}
