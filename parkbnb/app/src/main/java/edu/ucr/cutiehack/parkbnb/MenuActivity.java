package edu.ucr.cutiehack.parkbnb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button bt_r = (Button) findViewById(R.id.bt_rentor);
        Button bt_g = (Button) findViewById(R.id.bt_get);

        bt_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), CreateListingActivity.class);
                startActivity(i);
            }
        });

        bt_g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ListingsMapActivity.class);
                startActivity(i);
            }
        });
    }
}
