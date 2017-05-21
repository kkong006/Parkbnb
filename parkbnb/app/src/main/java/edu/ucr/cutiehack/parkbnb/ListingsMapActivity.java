package edu.ucr.cutiehack.parkbnb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListingsMapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private static final String TAG = "ListingsMapActivity";
    private GoogleMap mMap;
    private DatabaseReference ref;
    private ArrayList parkingList;
    private LatLng currLocation;
    ValueEventListener spotListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listings_map);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_listings_map);
        mapFragment.getMapAsync(this);

        ref = FirebaseDatabase.getInstance().getReference("spot");

        spotListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parkingList = new ArrayList();
                for(DataSnapshot child: dataSnapshot.getChildren()) {
                    ParkingSpot ps = child.getValue(ParkingSpot.class);
                    parkingList.add(ps);
                    Log.w(TAG, " images: " + ps.images + " lat: " + ps.lat
                            + " long: " + ps.lng + " price: $" + ps.price
                            + " rate: $" + ps.rate + " taken: " + ps.taken
                            + " user " + ps.user);
                }
                updateMap();
            }

            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        };
        ref.addValueEventListener(spotListener);
    }

    private void updateMap() {
        if(mMap != null) {
            Log.w(TAG, "\n\n\n");
            mMap.clear();
            for(int i = 0; i < parkingList.size(); i++) {
                ParkingSpot ps = (ParkingSpot)parkingList.get(i);
                if(!ps.taken) {
                    mMap.addMarker(new MarkerOptions().position(new LatLng(ps.lat, ps.lng)).title("$" + ps.price + "/day or $" + ps.rate + "/hr")).setTag(ps.id);
                    Log.w(TAG, " images: " + ps.images + " lat: " + ps.lat
                            + " long: " + ps.lng + " price: $" + ps.price
                            + " rate: $" + ps.rate + " taken: " + ps.taken
                            + " user " + ps.user);
                }
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currLocation));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        currLocation = new LatLng(33.9737, -117.3281); // UCR's location
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currLocation));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16));

        // Set listener for info window events
        mMap.setOnInfoWindowClickListener(this);
        Log.w(TAG, "onMapReady");
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        ParkingSpot ps = null;

        for(int i = 0; i < parkingList.size(); i++) {
            if(((ParkingSpot)parkingList.get(i)).id == (long)marker.getTag()) {
                ps = (ParkingSpot)parkingList.get(i);
                Log.w(TAG, "Found parking spot");
            }
        }

        Intent i = new Intent(this, ViewListingActivity.class);
        i.putExtra("lat", marker.getPosition().latitude);
        i.putExtra("lng", marker.getPosition().longitude);
        i.putExtra("price", ps.price);
        i.putExtra("image", ps.images);
        i.putExtra("id", ps.id);
        i.putExtra("rate", ps.rate);
        i.putExtra("user", ps.user);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        ref.removeEventListener(spotListener);
        Log.w(TAG, "removed event listener");
        finish();
    }

    @Override
    public void onPause() {
        ref.removeEventListener(spotListener);
        Log.w(TAG, "removed event listener");
        super.onPause();
    }
}
