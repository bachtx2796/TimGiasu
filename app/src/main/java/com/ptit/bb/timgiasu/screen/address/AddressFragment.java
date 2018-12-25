package com.ptit.bb.timgiasu.screen.address;

import android.app.Fragment;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.gemvietnam.base.viper.ViewFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;
import com.ptit.bb.timgiasu.R;
import com.ptit.bb.timgiasu.data.GoogleService;
import com.ptit.bb.timgiasu.data.dto.Coord;
import com.ptit.bb.timgiasu.prewrapper.PrefWrapper;
import com.ptit.bb.timgiasu.screen.map.MapManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static org.greenrobot.eventbus.EventBus.TAG;

/**
 * The Address Fragment
 */
public class AddressFragment extends ViewFragment<AddressContract.Presenter> implements AddressContract.View {

    private MapManager mMapManager;
    private MapFragment mMapFragment;

    private String TAG = "so47492459";

    private GoogleMap mMap;

    public static AddressFragment getInstance() {
        return new AddressFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_address;
    }

    @Override
    public void initLayout() {
        super.initLayout();

        initMap();
    }

    private void initMap() {
        mMapFragment = (MapFragment) getViewContext().getFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMapManager = new MapManager(getViewContext(), googleMap);

                showMarker(mPresenter.getCoord1());

                Toast.makeText(getViewContext(), "Bấm vào địa chỉ để thêm thông tin chi tiết", Toast.LENGTH_SHORT).show();
//                showMarker(PrefWrapper.getUser(getViewContext()).getCoord());
                //drawRouter(googleMap, PrefWrapper.getUser(getViewContext()).getCoord(), mPresenter.getCoord1());
            }
        });
    }

    public void onDestroyView() {
        super.onDestroyView();
        Fragment f = getViewContext().getFragmentManager()
                .findFragmentById(R.id.map);
        getViewContext().getFragmentManager().beginTransaction().remove(f).commit();
    }

    public void showMarker(Coord location) {
        mMapManager.addMarker(new LatLng(location.getLat(), location.getLng()), mPresenter.getTitle());
    }

    private void drawRouter(GoogleMap googleMap, Coord a1, Coord a2) {

        mMap = googleMap;
        mMap.clear();

        LatLng l1 = new LatLng(a1.getLat(), a1.getLng());
        mMap.addMarker(new MarkerOptions().position(l1).title("Marker in Barcelona"));

        LatLng l2 = new LatLng(a2.getLat(), a2.getLng());
        mMap.addMarker(new MarkerOptions().position(l2).title("Marker in Madrid"));

        //Define list to get all latlng for the route
        List<LatLng> path = new ArrayList();


        //Execute Directions API request
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyBIRn0GUrHct4Tb-4-SiZACcx1gZdiWC5k")
                .build();
        DirectionsApiRequest req = DirectionsApi.getDirections(context, a1.getLat() + "," + a1.getLng(), a2.getLat() + "," + a2.getLng());
        try {
            DirectionsResult res = req.await();

            //Loop through legs and steps to get encoded polylines of each step
            if (res.routes != null && res.routes.length > 0) {
                DirectionsRoute route = res.routes[0];

                if (route.legs != null) {
                    for (int i = 0; i < route.legs.length; i++) {
                        DirectionsLeg leg = route.legs[i];
                        if (leg.steps != null) {
                            for (int j = 0; j < leg.steps.length; j++) {
                                DirectionsStep step = leg.steps[j];
                                if (step.steps != null && step.steps.length > 0) {
                                    for (int k = 0; k < step.steps.length; k++) {
                                        DirectionsStep step1 = step.steps[k];
                                        EncodedPolyline points1 = step1.polyline;
                                        if (points1 != null) {
                                            //Decode polyline and add points to list of route coordinates
                                            List<com.google.maps.model.LatLng> coords1 = points1.decodePath();
                                            for (com.google.maps.model.LatLng coord1 : coords1) {
                                                path.add(new LatLng(coord1.lat, coord1.lng));
                                            }
                                        }
                                    }
                                } else {
                                    EncodedPolyline points = step.polyline;
                                    if (points != null) {
                                        //Decode polyline and add points to list of route coordinates
                                        List<com.google.maps.model.LatLng> coords = points.decodePath();
                                        for (com.google.maps.model.LatLng coord : coords) {
                                            path.add(new LatLng(coord.lat, coord.lng));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.getLocalizedMessage());
        }

        //Draw the polyline
        if (path.size() > 0) {
            PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.BLUE).width(5);
            mMap.addPolyline(opts);
        }

        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(l2, 6));
    }
}
