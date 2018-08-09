package com.ptit.bb.timgiasu.screen.chatdetail;

import com.gemvietnam.base.viper.Interactor;

/**
 * The ChatDetail interactor
 */
class ChatDetailInteractor extends Interactor<ChatDetailContract.Presenter>
        implements ChatDetailContract.Interactor {

    ChatDetailInteractor(ChatDetailContract.Presenter presenter) {
        super(presenter);
    }
}
