package edu.ucr.cutiehack.parkbnb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class ViewListingActivity extends AppCompatActivity {

    private static final String TAG = "ViewListingActivity";

    private ImageView ivImage;
    private TextView tvPrice;
    private TextView tvRate;
    private Button btBook;
    private double lat;
    private double lng;
    private long id;
    private double price;
    private double rate;
    private String image;
    private String user;

    private DatabaseReference ref;
    private ArrayList parkingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listing);
        Log.w(TAG, "OnCreate");
        ref = FirebaseDatabase.getInstance().getReference("spot");

        ValueEventListener spotListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parkingList = new ArrayList();
                for(DataSnapshot child: dataSnapshot.getChildren()) {
                    ParkingSpot ps = child.getValue(ParkingSpot.class);
                    parkingList.add(ps);
                    Log.w(TAG, "ID: " + ps.id + " Images: " + ps.images + " lat: " + ps.lat + " long: " + ps.lng + " price: $" + ps.price + " rate: $" + ps.rate + " taken: " + ps.taken);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        };

        ref.addValueEventListener(spotListener);

        ivImage = (ImageView) findViewById(R.id.iv_view_listing);
        tvPrice = (TextView) findViewById(R.id.tv_view_listing_price);
        tvRate = (TextView) findViewById(R.id.tv_view_listing_rate);

        Intent i = getIntent();
        Bundle bd = i.getExtras();
        if(bd != null) {
            lat = (double) bd.get("lat");
            lng = (double) bd.get("lng");
            id = (long) bd.get("id");
            price = (double) bd.get("price");
            rate = (double) bd.get("rate");
            image = (String) bd.get("image");
            user = (String) bd.get("user");
            tvPrice.setText("Price: $" + price);
            tvRate.setText("Rate: $" + rate);

            if(image.substring(0,4).equals("http")) {
                Log.w(TAG, "Valid url: " + image);
                new DownLoadImageTask(ivImage).execute(image);
            }
            Log.w(TAG, "\n\tlat " + lat + "\n\tlng " + lng + "\n\tid " + id + "\n\tprice " + price + "\n\trate " + rate + "\n\timage " + image + "\n\tuser " + user);
        }

        btBook = (Button) findViewById(R.id.bt_view_listing_book);
        btBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Booking your space", Toast.LENGTH_SHORT).show();

                ref.child(id + "").setValue(new ParkingSpot(id, image, lat, lng, price, rate, true, user));

                Intent i = new Intent(getBaseContext(), BookedListingMapActivity.class);
                i.putExtra("lat", lat);
                i.putExtra("lng", lng);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });
    }

    // Download image to imageview
    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        // Override this method to perform a computation on a background thread.
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                // Decode an input stream into a bitmap.
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        // Runs on the UI thread after doInBackground(Params...).
        protected void onPostExecute(Bitmap result){
            if(result != null) {
                imageView.setMinimumHeight(result.getHeight());
                imageView.setImageBitmap(result);
            } else {
                imageView.setVisibility(View.GONE);
                Toast.makeText(getBaseContext(), "No image available", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
