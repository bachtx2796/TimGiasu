package com.ptit.bb.timgiasu.screen.chat;

import com.gemvietnam.base.viper.Interactor;

/**
 * The Chat interactor
 */
class ChatInteractor extends Interactor<ChatContract.Presenter>
        implements ChatContract.Interactor {

    ChatInteractor(ChatContract.Presenter presenter) {
        super(presenter);
    }
}
