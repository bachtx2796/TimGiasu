package com.ptit.bb.timgiasu.screen.post;

import com.gemvietnam.base.viper.Interactor;

/**
 * The Post interactor
 */
class PostInteractor extends Interactor<PostContract.Presenter>
        implements PostContract.Interactor {

    PostInteractor(PostContract.Presenter presenter) {
        super(presenter);
    }
}
