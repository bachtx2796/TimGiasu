package com.example.bb.timgiasu.screen.chat;

import com.example.bb.timgiasu.data.dto.GroupChat;
import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;

import java.util.ArrayList;
import java.util.List;

/**
 * The Chat Presenter
 */
public class ChatPresenter extends Presenter<ChatContract.View, ChatContract.Interactor>
        implements ChatContract.Presenter {

    private List<GroupChat> mGroupChats;
    private ChatAdapter mChatAdapter;

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
        getData();
    }

    private void getData() {
        mGroupChats = new ArrayList<>();
        mGroupChats.add(new GroupChat());
        mGroupChats.add(new GroupChat());
        mGroupChats.add(new GroupChat());
        mGroupChats.add(new GroupChat());
        mGroupChats.add(new GroupChat());
        mGroupChats.add(new GroupChat());
        mChatAdapter = new ChatAdapter(getViewContext(),mGroupChats);
        mView.bindView(mChatAdapter);
    }

    @Override
    public ChatContract.Interactor onCreateInteractor() {
        return new ChatInteractor(this);
    }
}
