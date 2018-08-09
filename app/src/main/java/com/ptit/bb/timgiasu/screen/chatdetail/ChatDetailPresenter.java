package com.ptit.bb.timgiasu.screen.chatdetail;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;

/**
 * The ChatDetail Presenter
 */
public class ChatDetailPresenter extends Presenter<ChatDetailContract.View, ChatDetailContract.Interactor>
        implements ChatDetailContract.Presenter {

    private String mId;

    public ChatDetailPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public ChatDetailContract.View onCreateView() {
        return ChatDetailFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public ChatDetailContract.Interactor onCreateInteractor() {
        return new ChatDetailInteractor(this);
    }

    public ChatDetailPresenter setId(String id) {
        this.mId = id;
        return this;
    }
}
