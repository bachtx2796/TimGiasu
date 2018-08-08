package com.example.bb.timgiasu.screen.tutors;

import com.example.bb.timgiasu.data.dto.Tutor;
import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;

import java.util.ArrayList;
import java.util.List;

/**
 * The Tutors Presenter
 */
public class TutorsPresenter extends Presenter<TutorsContract.View, TutorsContract.Interactor>
        implements TutorsContract.Presenter {

    private List<Tutor> mTutors;
    private TutorAdapter mTutorAdapter;

    public TutorsPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public TutorsContract.View onCreateView() {
        return TutorsFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
        getData();
    }

    private void getData() {
        mTutors = new ArrayList<>();
        mTutors.add(new Tutor());
        mTutors.add(new Tutor());
        mTutors.add(new Tutor());
        mTutors.add(new Tutor());
        mTutors.add(new Tutor());
        mTutors.add(new Tutor());
        mTutors.add(new Tutor());
        mTutorAdapter = new TutorAdapter(getViewContext(),mTutors);
        mView.bindView(mTutorAdapter);
    }

    @Override
    public TutorsContract.Interactor onCreateInteractor() {
        return new TutorsInteractor(this);
    }
}
