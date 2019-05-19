package com.hanium.AguideR;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapMain extends FragmentActivity implements OnMapReadyCallback {


    Button worldbtn;
    Button npcloaction;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_main);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        npcloaction = (Button) findViewById(R.id.npcloaction);

        npcloaction.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent npcintent = new Intent(getApplicationContext(), npclocation.class);
                startActivity(npcintent);
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in INU and move the camera
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