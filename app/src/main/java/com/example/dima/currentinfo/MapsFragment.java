package com.example.dima.currentinfo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.example.dima.currentinfo.Tracker.REQUEST_PERMISSIONS_REQUEST_CODE;

public class MapsFragment extends Fragment implements OnMapReadyCallback {
    private View v;
    private MapView mMapView;
    private GoogleMap mMap;
    private Tracker mTracker;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // check if user gave permissions, otherwise ask via dialog
        if (!checkPermission()) {
            getLocationPermissions();
        } else {
            mTracker = Tracker.get(getContext());
            mTracker.requestLocationUpdate();
        }
    }

    private void getLocationPermissions() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private boolean checkPermission() {
        return isGranted(ActivityCompat.checkSelfPermission(getContext(), ACCESS_FINE_LOCATION)) &&
                isGranted(ActivityCompat.checkSelfPermission(getContext(), ACCESS_COARSE_LOCATION));
    }

    private boolean isGranted(int permission) {
        return permission == PackageManager.PERMISSION_GRANTED;
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
        mTracker.removeLocationUpdate();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        double latitude = mTracker.getLatitude();
        double longitude = mTracker.getLongitude();


        LatLng latLng = new LatLng(latitude, longitude);
        MapsInitializer.initialize(getContext());
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        CameraPosition cameraPosition = CameraPosition.builder().
                target(latLng).zoom(8).bearing(0).tilt(60).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }


    private void addMarker(String title, String snippet, double latitude, double longitude) {

        LatLng latLng = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions()
                .position(latLng).title(title).snippet(snippet));

        CameraPosition cameraPosition = CameraPosition.builder().
                target(latLng).zoom(16).bearing(0).tilt(45).build();

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

}
