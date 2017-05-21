package edu.ucr.cutiehack.parkbnb;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateListingActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    private static final String TAG = "CreateListingsMap";

    private GoogleMap mMap;
    private DatabaseReference ref;
    private Button SearchButton;
    private Button CLButton;
    private ArrayList parkingList;
    private LatLng currLocation;
    int parkNum = 0;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        SearchButton = (Button) findViewById(R.id.Search);
        CLButton = (Button) findViewById(R.id.CLbutton);
        CLButton.setOnClickListener(this);

        ref = FirebaseDatabase.getInstance().getReference("spot");

/*
        ValueEventListener spotListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parkingList = new ArrayList();
                for(DataSnapshot child: dataSnapshot.getChildren()) {
                    ParkingSpot ps = child.getValue(ParkingSpot.class);
                    parkingList.add(ps);
                    Log.w(TAG, "ID: " + ps.id + " Images: " + ps.images + " lat: " + ps.lat + " long: " + ps.lng + " price: $" + ps.price + " rate: $" + ps.rate + " taken: " + ps.taken);
                }
                updateMap();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        };

        ref.addValueEventListener(spotListener);
*/
    }
/*
    private void updateMap() {
        if(mMap != null) {
            Log.w(TAG, "\n\n\n");
            mMap.clear();
            for(int i = 0; i < parkingList.size(); i++) {
                ParkingSpot ps = (ParkingSpot)parkingList.get(i);
                if(!ps.taken) {
                    mMap.addMarker(new MarkerOptions().position(new LatLng(ps.lat, ps.lng)).title("$" + ps.price + "/day or $" + ps.rate + "/hr"));
                }
                Log.w(TAG, "ID: " + ps.id + " Images: " + ps.images + " lat: " + ps.lat + " long: " + ps.lng + " price: $" + ps.price + " rate: $" + ps.rate + " taken: " + ps.taken);
            }
            currLocation = new LatLng(34, -117);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currLocation));
        }
   */

    public void onMapSearch(View view) throws IOException {
        EditText locationText = (EditText) findViewById(R.id.addressEditText);
        String location = locationText.getText().toString();
        List<Address> addressList = null;

        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }

            mMap.clear();
            if(!addressList.isEmpty()) {
                Address address = addressList.get(0);
                currLocation = new LatLng(address.getLatitude(), address.getLongitude());
                mMap.addMarker(new MarkerOptions().position(currLocation).title("Add New Parking"));
                float zoom = 10;
                //mMap.animateCamera(CameraUpdateFactory.newLatLng(currLocation));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currLocation, zoom));
            }
            /*
            currLocation = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(currLocation).title("Add New Parking"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(currLocation));
            */
        }
    }

    public void onCreateListing(View view) {


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker").draggable(true));
        Log.w(TAG, "onMapReady");

        /*
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }*/

        //mMap.setMyLocationEnabled(true);



    }

    public void onClick(View v) {
        if(v == CLButton){
            Intent i = new Intent(this, EnterInfo.class);

            if(currLocation != null) {
                i.putExtra("lat", currLocation.latitude);
                i.putExtra("lng", currLocation.longitude);
            }
            startActivity(i);

        }
    }

//    @Override
//    public void onBackPressed() {
//        this.moveTaskToBack(true);
//    }


}
