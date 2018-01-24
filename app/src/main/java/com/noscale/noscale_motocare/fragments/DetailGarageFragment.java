package com.noscale.noscale_motocare.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.noscale.noscale_motocare.R;
import com.noscale.noscale_motocare.activities.MainActivity;

/**
 * Created by kurniawanrizzki on 20/01/18.
 */

public class DetailGarageFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap map;
    private View view;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail_garage, container, false);
        ((MainActivity) getActivity()).getFragmentController().getMenuFragment().getMenuController()
                .getGarageFragment().getController()
                .initSubLayout(view);
        initMap(savedInstanceState);
        return view;
    }

    private void initMap (Bundle savedInstanceState) {
        MapsInitializer.initialize(getActivity());
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);
    }


    public void showOnTheMap (double lat, double lng, String garageName) {
        map.clear();
        map.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(garageName));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng), 12.0f));
    }

    public View getView () {
        return view;
    }

}
