package com.ptit.bb.timgiasu.screen.map;

import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.gemvietnam.base.viper.ViewFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.ptit.bb.timgiasu.R;
import com.ptit.bb.timgiasu.data.GoogleService;
import com.ptit.bb.timgiasu.data.GoogleServiceBuilder;
import com.ptit.bb.timgiasu.data.dto.Coord;
import com.ptit.bb.timgiasu.data.dto.PredictionPlaces;
import com.ptit.bb.timgiasu.data.dto.SugguestLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The Map Fragment
 */
public class MyMapFragment extends ViewFragment<MyMapContract.Presenter> implements MyMapContract.View {

    @BindView(R.id.search_et)
    AutoCompleteTextView mSearchEt;

    private MapManager mMapManager;
    private MapFragment mMapFragment;

    private String selectedSearchItem = "";


    public static MyMapFragment getInstance() {
        return new MyMapFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_map;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        initMap();

        searchPlace();
    }

    private void searchPlace() {
        mSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                if (!selectedSearchItem.equals(mSearchEt.getText().toString()))
                    mPresenter.suggestPlace(editable.toString());
            }

        });

        mSearchEt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedSearchItem = (String) mSearchEt.getAdapter().getItem(i);
                mSearchEt.dismissDropDown();
                mPresenter.showPlace(selectedSearchItem);
            }
        });
    }


    private void initMap() {
        mMapFragment = (MapFragment) getViewContext().getFragmentManager().findFragmentById(R.id.detail_map);
        mMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMapManager = new MapManager(getViewContext(), googleMap);
//            var latlng = LatLng(mItemDTO?.latitude!!.toDouble(), mItemDTO?.longitude!!.toDouble())
//            mapManager?.addMarker(latlng, "")
            }
        });
    }

    public void onDestroyView() {
        super.onDestroyView();
        Fragment f = getViewContext().getFragmentManager()
                .findFragmentById(R.id.detail_map);
        getViewContext().getFragmentManager().beginTransaction().remove(f).commit();
    }


    @Override
    public void showSuggest() {
        mSearchEt.setAdapter(mPresenter.getAdapter());
        mSearchEt.showDropDown();
    }

    @Override
    public void showMarker(Coord location) {
        mMapManager.addMarker(new LatLng(location.getLat(), location.getLng()), "vị trí của tôi");
    }

    @OnClick(R.id.select_bt)
    public void selectLocation() {
        mPresenter.selectLocation(selectedSearchItem);
    }
}
