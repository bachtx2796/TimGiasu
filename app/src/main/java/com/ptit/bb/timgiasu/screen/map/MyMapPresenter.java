package com.ptit.bb.timgiasu.screen.map;

import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.ptit.bb.timgiasu.data.GoogleService;
import com.ptit.bb.timgiasu.data.GoogleServiceBuilder;
import com.ptit.bb.timgiasu.data.dto.GoogleMapSearchDTO;
import com.ptit.bb.timgiasu.data.dto.LocationSearchResult;
import com.ptit.bb.timgiasu.data.dto.PredictionPlaces;
import com.ptit.bb.timgiasu.data.dto.SugguestLocation;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The Map Presenter
 */
public class MyMapPresenter extends Presenter<MyMapContract.View, MyMapContract.Interactor>
        implements MyMapContract.Presenter {

    private List<String> mPlaces;
    private ArrayAdapter mArrayAdapter;

    private OnLocationSelectedListener mOnLocationSelectedListener;

    public void setmOnLocationSelectedListener(OnLocationSelectedListener mOnLocationSelectedListener) {
        this.mOnLocationSelectedListener = mOnLocationSelectedListener;
    }

    public MyMapPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public MyMapContract.View onCreateView() {
        return MyMapFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
        mPlaces = new ArrayList<>();
        mArrayAdapter = new ArrayAdapter(getViewContext(), android.R.layout.simple_list_item_1, mPlaces);
    }

    @Override
    public MyMapContract.Interactor onCreateInteractor() {
        return new MyMapInteractor(this);
    }

    @Override
    public void suggestPlace(String key) {
        GoogleServiceBuilder.getService().getLocationSuggestion(key, "vi", GoogleService.KEY).enqueue(new Callback<PredictionPlaces>() {
            @Override
            public void onResponse(Call<PredictionPlaces> call, Response<PredictionPlaces> response) {
                if (response.isSuccessful()) {
                    mPlaces.clear();
                    List<SugguestLocation> tmp = response.body().getmSugguestLocations();
                    for (SugguestLocation sugguestLocation : tmp) {
                        mPlaces.add(sugguestLocation.getDescription());
                    }
                    mArrayAdapter.clear();
                    mArrayAdapter.addAll(mPlaces);
                    mView.showSuggest();
                }
            }

            @Override
            public void onFailure(Call<PredictionPlaces> call, Throwable t) {

            }
        });
    }

    @Override
    public ArrayAdapter getAdapter() {
        return mArrayAdapter;
    }

    @Override
    public void showPlace(String selectedSearchItem) {
        mView.showProgress();
        GoogleServiceBuilder.getService().getLocationSearch(selectedSearchItem, GoogleService.KEY).enqueue(new Callback<GoogleMapSearchDTO>() {
            @Override
            public void onResponse(Call<GoogleMapSearchDTO> call, Response<GoogleMapSearchDTO> response) {
                mView.hideProgress();
                if (response.isSuccessful()) {
                    LocationSearchResult tmp = response.body().getListSearchResult().get(0);
                    mView.showMarker(tmp.getGeometry().getLocation());
                }
            }

            @Override
            public void onFailure(Call<GoogleMapSearchDTO> call, Throwable t) {

            }
        });
    }

    @Override
    public void selectLocation(String selectedSearchItem) {
        mOnLocationSelectedListener.onItemSelected(selectedSearchItem);
        back();
    }

    public interface OnLocationSelectedListener {
        void onItemSelected(String location);
    }
}
