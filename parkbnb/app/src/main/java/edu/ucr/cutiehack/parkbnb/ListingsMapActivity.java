package edu.ucr.cutiehack.parkbnb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.database.DatabaseReference;

public class ListingsMapActivity extends AppCompatActivity {

    private GoogleMap mMap;
    private DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listings_map);
    }


}
