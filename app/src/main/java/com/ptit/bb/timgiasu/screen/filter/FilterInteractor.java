package com.ptit.bb.timgiasu.screen.filter;

import com.gemvietnam.base.viper.Interactor;

/**
 * The Filter interactor
 */
class FilterInteractor extends Interactor<FilterContract.Presenter>
        implements FilterContract.Interactor {

    FilterInteractor(FilterContract.Presenter presenter) {
        super(presenter);
    }
}
