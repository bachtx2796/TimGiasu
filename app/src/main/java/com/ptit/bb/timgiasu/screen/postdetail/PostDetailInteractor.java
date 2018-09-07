package com.ptit.bb.timgiasu.screen.postdetail;

import com.gemvietnam.base.viper.Interactor;

/**
 * The PostDetail interactor
 */
class PostDetailInteractor extends Interactor<PostDetailContract.Presenter>
        implements PostDetailContract.Interactor {

    PostDetailInteractor(PostDetailContract.Presenter presenter) {
        super(presenter);
    }
}
