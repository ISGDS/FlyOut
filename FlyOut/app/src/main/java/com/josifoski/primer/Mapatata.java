package com.josifoski.primer;

import android.annotation.TargetApi;
import android.location.Geocoder;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class Mapatata extends AppCompatActivity {
    private GoogleMap map;
    double lat,lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapatata);
        asd();


    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void asd(){
        map=((MapFragment)getFragmentManager().findFragmentById(R.id.fragment)).getMap();
        map.addMarker(new MarkerOptions().position(new LatLng(42.002131, 21.429351)).title("Skopje").snippet("You take off from here!"));
    }

}
