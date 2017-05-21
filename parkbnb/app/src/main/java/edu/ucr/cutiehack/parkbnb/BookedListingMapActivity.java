package edu.ucr.cutiehack.parkbnb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class BookedListingMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "ListingsMapActivity";
    private GoogleMap mMap;
    private LatLng currLocation;
    private double lat;
    private double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_listing_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_booked_listing_map);
        mapFragment.getMapAsync(this);

        Intent i = getIntent();
        Bundle bd = i.getExtras();
        lat = (double)bd.get("lat");
        lng = (double)bd.get("lng");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        currLocation = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(currLocation));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(currLocation));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        Log.w(TAG, "onMapReady");
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getBaseContext(), ListingsMapActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}
