package com.josifoski.primer;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setUpMapIfNeeded();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded()
    {
        if(mMap == null)
        {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment2)).getMap();
            if(mMap != null)
            {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(42.002131, 21.429351), 12));
                mMap.addMarker(new MarkerOptions().position(new LatLng(42.002131, 21.429351)).title("Skopje").snippet("You take off from here!"));
            }
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
    }

}
