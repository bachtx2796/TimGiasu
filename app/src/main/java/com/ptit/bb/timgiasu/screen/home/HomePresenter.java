package com.ptit.bb.timgiasu.screen.home;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.ptit.bb.timgiasu.data.dto.ItemRecruit;

import java.util.ArrayList;
import java.util.List;

/**
 * The Home Presenter
 */
public class HomePresenter extends Presenter<HomeContract.View, HomeContract.Interactor>
        implements HomeContract.Presenter {

    private List<ItemRecruit> mItemRecruits;
    private HomeAdapter mHomeAdapter;

    public HomePresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public HomeContract.View onCreateView() {
        return HomeFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
        getData();
    }

    private void getData() {
        mItemRecruits = new ArrayList<>();
        mItemRecruits.add(new ItemRecruit());
        mItemRecruits.add(new ItemRecruit());
        mItemRecruits.add(new ItemRecruit());
        mItemRecruits.add(new ItemRecruit());
        mItemRecruits.add(new ItemRecruit());
        mItemRecruits.add(new ItemRecruit());
        mItemRecruits.add(new ItemRecruit());
        mHomeAdapter = new HomeAdapter(getViewContext(),mItemRecruits);
        mView.bindView(mHomeAdapter);
    }

    @Override
    public HomeContract.Interactor onCreateInteractor() {
        return new HomeInteractor(this);
    }
}
