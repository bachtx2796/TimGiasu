package com.ptit.bb.timgiasu.screen.history;

import com.gemvietnam.base.viper.Interactor;

/**
 * The History interactor
 */
class HistoryInteractor extends Interactor<HistoryContract.Presenter>
        implements HistoryContract.Interactor {

    HistoryInteractor(HistoryContract.Presenter presenter) {
        super(presenter);
    }
}
