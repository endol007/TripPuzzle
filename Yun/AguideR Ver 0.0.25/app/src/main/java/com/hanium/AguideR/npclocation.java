package com.hanium.AguideR;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class npclocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.npclocation);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera
        LatLng inu = new LatLng(37.3750548,126.63289980000002);
        mMap.addMarker(new MarkerOptions().position(inu).title("INU"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(inu));

        LatLng inulib = new LatLng(37.37501214672265, 126.63426687735887);
        mMap.addMarker(new MarkerOptions().position(inulib).title("INU학산도서관"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(inulib));

        LatLng inurestaurant = new LatLng(37.37436518196216, 126.63193298606629);
        mMap.addMarker(new MarkerOptions().position(inurestaurant).title("INU학생식당"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(inurestaurant));

        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f)); //위치 줌

        //LatLng airport = new LatLng(37.4601908,126.44069569999999);
        //mMap.addMarker(new MarkerOptions().position(airport).title("인천공항"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(airport));
        //인천공항 추가하면 지도가 풀지도로 나옴


    }
}
