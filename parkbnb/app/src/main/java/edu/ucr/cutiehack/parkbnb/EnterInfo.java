package edu.ucr.cutiehack.parkbnb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EnterInfo extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPrice;
    private EditText editTextRate;
    private double lat;
    private double lng;

    private Button buttonSubmit;

    int spotNum = 100;
    private DatabaseReference ref;
    //ref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_info);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if(b != null) {
            lat = (double) b.get("lat");
            lng = (double) b.get("lng");
        }
        editTextPrice = (EditText) findViewById(R.id.priceET);
        editTextRate = (EditText) findViewById(R.id.rateET);
        buttonSubmit = (Button) findViewById(R.id.submitButton);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
/*
        ref = FirebaseDatabase.getInstance().getReference("spot");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    String rate = editTextRate.getText().toString().trim();
                    String price = editTextPrice.getText().toString().trim();
                    String strSpotNum = String.valueOf(spotNum);
                    //ParkingSpot currSpot = new ParkingSpot(spotNum, price);
                    ref.child(strSpotNum).child("price").setValue(price);
                    ref.child(strSpotNum).child("rate").setValue(rate);
                    ref.child(strSpotNum).child("taken").setValue(false);
                    //databaseReference.child()

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
      */
    }


    public void onSubmit() {
        ref = FirebaseDatabase.getInstance().getReference("spot");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    String rate = editTextRate.getText().toString().trim();
                    String price = editTextPrice.getText().toString().trim();
                    String strSpotNum = String.valueOf(spotNum);
                    //ParkingSpot currSpot = new ParkingSpot(spotNum, price);
                    ref.child(strSpotNum).child("price").setValue(price);
                    ref.child(strSpotNum).child("rate").setValue(rate);
                    ref.child(strSpotNum).child("taken").setValue(false);

                    ref.child(strSpotNum).child("lat").setValue(lat);
                    ref.child(strSpotNum).child("lng").setValue(lng);
                    //databaseReference.child()
                    Toast.makeText(getApplicationContext(), "Your entry has been saved!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

}

