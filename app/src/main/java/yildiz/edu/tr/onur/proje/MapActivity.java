package yildiz.edu.tr.onur.proje;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Locale;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap googleMap;
    double latitude, longtitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_map);
        Intent intent = getIntent();
        latitude = intent.getDoubleExtra("latitude", 41.02412565669365 );
        longtitude = intent.getDoubleExtra("longitude", 28.8941428810358);
        // Get the SupportMapFragment and request notification when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        LatLng sydney = new LatLng(latitude, longtitude);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(sydney)
                .title("Location of Event").draggable(true);
        Marker mVisitingMarker = googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mVisitingMarker.setPosition(latLng);
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                Intent intent = new Intent();
                intent.putExtra("latitude" , latLng.latitude);
                intent.putExtra("longitude" , latLng.longitude);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });

    }

}