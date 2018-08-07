package com.example.bb.timgiasu.screen.chat;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;

/**
 * The Chat Presenter
 */
public class ChatPresenter extends Presenter<ChatContract.View, ChatContract.Interactor>
        implements ChatContract.Presenter {

    public ChatPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public ChatContract.View onCreateView() {
        return ChatFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public ChatContract.Interactor onCreateInteractor() {
        return new ChatInteractor(this);
    }
}
