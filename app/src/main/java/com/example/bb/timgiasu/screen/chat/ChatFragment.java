package com.example.bb.timgiasu.screen.chat;

import com.gemvietnam.base.viper.ViewFragment;
import com.example.bb.timgiasu.R;

/**
 * The Chat Fragment
 */
public class ChatFragment extends ViewFragment<ChatContract.Presenter> implements ChatContract.View {

    public static ChatFragment getInstance() {
        return new ChatFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat;
    }
}
