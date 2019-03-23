package com.jonathanvillafuerte.contactosuteq;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    String latitud, longitud;
    String usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Bundle bundle =  this.getIntent().getExtras();
        latitud = bundle.getString("latitud");
        longitud = bundle.getString("longitud");
        usuario = bundle.getString("usuario");

        FloatingActionButton my_fab = (FloatingActionButton) findViewById(R.id.back_map);
        my_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this,ListarContactosActivity.class);
                startActivity(intent);

            }
        });
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        try
        {
            mMap = googleMap;

            // Add a marker in Sydney and move the camera
            LatLng ubication = new LatLng(Double.parseDouble(longitud), Double.parseDouble(latitud));
            mMap.addMarker(new MarkerOptions().position(ubication).title(usuario));

            CameraUpdate miubicacion = CameraUpdateFactory.newLatLngZoom(ubication, 14);
            mMap.animateCamera(miubicacion);
        }
        catch (Exception ex)

        {
            Toast.makeText(getApplicationContext(),"El usuario no tiene ubicacion",Toast.LENGTH_SHORT).show();
        }
    }
}