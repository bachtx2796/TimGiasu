package com.ptit.bb.timgiasu.screen.editpost;

import com.gemvietnam.base.viper.Interactor;

/**
 * The EditPost interactor
 */
class EditPostInteractor extends Interactor<EditPostContract.Presenter>
        implements EditPostContract.Interactor {

    EditPostInteractor(EditPostContract.Presenter presenter) {
        super(presenter);
    }
}
