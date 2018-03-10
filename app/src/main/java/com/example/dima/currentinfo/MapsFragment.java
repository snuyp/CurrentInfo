package com.example.dima.currentinfo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dima.currentinfo.common.Common;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsFragment extends Fragment implements OnMapReadyCallback {
    private View v;
    private MapView mMapView;
    private GoogleMap mMap;
    private List<Info> mInfoList;
    private double longitude;
    private double latitude;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInfoList = InfoLab.get(getContext()).getInfoList();
        longitude = Common.lastLocation.getLongitude();
        latitude = Common.lastLocation.getLatitude();
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_google_maps, container, false);
        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = v.findViewById(R.id.map);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();

            mMapView.getMapAsync(this);

            mMapView.onStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(latitude, longitude);
        MapsInitializer.initialize(getContext());
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().isMapToolbarEnabled();

        CameraPosition cameraPosition = CameraPosition.builder().
                target(latLng).zoom(10).bearing(0).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        showAllMarker();
    }
    private void showAllMarker()
    {
        for(Info info : mInfoList)
        {
            String title = info.getSimpleDate()+" "+info.getTemp();
            addMarker(title,info.getTitle(),info.getLatitude(),info.getLongitude());
        }
    }

    private void addMarker(String title, String snippet, double latitude, double longitude) {

        LatLng latLng = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions()
                .position(latLng).title(title).snippet(snippet));

        CameraPosition cameraPosition = CameraPosition.builder().
                target(latLng).zoom(16).bearing(0).build();

        //mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
    private void addMarker(String photoUrl, String title, String snippet, double latitude, double longitude)
    {
        LatLng latLng = new LatLng(latitude,longitude);
        //mMap.addMarker(new MarkerOptions().position(latLng).title(title).snippet(snippet)).setIcon();
    }
//    private void getLocationPermissions() {
//        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                REQUEST_PERMISSIONS_REQUEST_CODE);
//    }
//
//    private boolean checkPermission() {
//        return isGranted(ActivityCompat.checkSelfPermission(getContext(), ACCESS_FINE_LOCATION)) &&
//                isGranted(ActivityCompat.checkSelfPermission(getContext(), ACCESS_COARSE_LOCATION));
//    }

//    private boolean isGranted(int permission) {
//        return permission == PackageManager.PERMISSION_GRANTED;
//    }
}
