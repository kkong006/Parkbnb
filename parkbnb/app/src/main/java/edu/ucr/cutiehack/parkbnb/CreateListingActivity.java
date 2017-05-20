package edu.ucr.cutiehack.parkbnb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class CreateListingActivity extends AppCompatActivity implements OnMapReadyCallback{

    private static final String TAG = "CreateListingsMap";

    private GoogleMap mMap;
    private DatabaseReference ref;
    private Button btCreateListing;
    private ArrayList parkingList;
    private LatLng currLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
/*
        btCreateListing = (Button) findViewById(R.id.bt_create_listing);
        btCreateListing.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), CreateListing.class);
                startActivity(i);
            }
        });
*/
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        Log.w(TAG, "onMapReady");
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }
}
