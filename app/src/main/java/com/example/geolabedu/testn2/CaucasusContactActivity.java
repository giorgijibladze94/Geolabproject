package com.example.geolabedu.testn2;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class CaucasusContactActivity extends ActionBarActivity implements OnMapReadyCallback {

    SupportMapFragment mapFragment;
    ImageButton imageButton;
    Toolbar toolbar;
    TextView textView,textView1,textView2,textView3;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caucasus_contact);

        GoogleMapOptions options = new GoogleMapOptions().liteMode(true);
        textView= (TextView) findViewById(R.id.contact);
        textView1= (TextView) findViewById(R.id.contact1);
        textView2= (TextView) findViewById(R.id.contact2);
        textView3= (TextView) findViewById(R.id.contact3);
        toolbar= (Toolbar) findViewById(R.id.contacttoolbar);

//        imageButton= (ImageButton) findViewById(R.id.contactimage);
//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(CaucasusContactActivity.this,ContactActivity.class);
//                startActivity(intent);
//            }
//        });

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);


        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/Helvetica-Bold.otf");
        textView.setTypeface(typeface);
        textView1.setTypeface(typeface);
        textView2.setTypeface(typeface);
        textView3.setTypeface(typeface);

        Window window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.statusrose));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_caucasus_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id ==android.R.id.home) {
            Intent intent=new Intent(CaucasusContactActivity.this,FirstActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        final LatLng caucasus=new LatLng(41.7078902,44.7732324);
        Marker marker=googleMap.addMarker(new MarkerOptions().position(caucasus));


//        googleMap.setMyLocationEnabled(true);
//        LatLng markerLoc=new LatLng(caucasus.latitude, caucasus.longitude);
//        final CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(markerLoc)      // Sets the center of the map to Mountain View
//                .zoom(16)                   // Sets the zoom
//                .bearing(90)                // Sets the orientation of the camera to east
//                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
//                .build();                   //
//        googleMap.addMarker(new MarkerOptions().position(new LatLng(caucasus.latitude, caucasus.longitude)));
//        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//        googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
//            @Override
//            public boolean onMyLocationButtonClick() {
//                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//                return true;
//            }
//        });

    }
}
