package com.ptit.bb.timgiasu.screen.filter;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;

import java.util.ArrayList;
import java.util.List;

/**
 * The Filter Presenter
 */
public class FilterPresenter extends Presenter<FilterContract.View, FilterContract.Interactor>
        implements FilterContract.Presenter {

    private ApplyListener mApplyListener;

    public void setmApplyListener(ApplyListener mApplyListener) {
        this.mApplyListener = mApplyListener;
    }

    public FilterPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public FilterContract.View onCreateView() {
        return FilterFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public FilterContract.Interactor onCreateInteractor() {
        return new FilterInteractor(this);
    }

    @Override
    public void apply(String gender, List<String> mClasses, List<String> mSubjects) {
        if (mClasses == null){
            mClasses = new ArrayList<>();
        }
        if (mSubjects == null){
            mSubjects = new ArrayList<>();
        }
        mApplyListener.apply(gender, mClasses, mSubjects);
        back();
    }

    public interface ApplyListener {
        void apply(String gender, List classes, List subject);
    }
}
