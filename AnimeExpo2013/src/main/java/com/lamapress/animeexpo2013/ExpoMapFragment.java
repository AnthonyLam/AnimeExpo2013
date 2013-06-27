package com.lamapress.animeexpo2013;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;

public class ExpoMapFragment extends Activity {

    public static int NUM_OVERLAY = 3;
    public MapView mView;
    GoogleMap mMap;
    //GroundOverlay goo;
    MapOverlay[] overlay = new MapOverlay[NUM_OVERLAY];
    TileOverlay[] tileOverlay = new TileOverlay[NUM_OVERLAY];
    public LatLng ne;
    public LatLng sw;



    public ExpoMapFragment(){
        // Generate TileOverlays

    }


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map_view);

        //View v = inflater.inflate(R.layout.fragment_map_view,null);

        try{

            mView = (MapView)findViewById(R.id.map);
        }
        catch(NullPointerException e){
            e.getCause();
        }

        try{
            MapsInitializer.initialize(this);
        }
        catch(GooglePlayServicesNotAvailableException e)
        {
            Toast.makeText(this, "Not Available", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        mView.onCreate(savedInstanceState);
        mMap = mView.getMap();
        mMap.setMyLocationEnabled(true);

        Button hallButton = (Button)findViewById(R.id.show_hall);
        Button oneButton = (Button)findViewById(R.id.show_level_one);
        Button twoButton = (Button)findViewById(R.id.show_level_two);

        // Boundary definitions
        ne = new LatLng(34.041689,-118.269481);
        sw = new LatLng( 34.039205,-118.271738);

        // Sets the Exhibit Hall overlay for LACC


        for(int numOv = 0;numOv < NUM_OVERLAY;numOv++){
            overlay[numOv] = new MapOverlay(this.getAssets(),numOv);
            tileOverlay[numOv] = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(overlay[numOv]));
        }

        //Moves the camera to center on the convention center
        CameraPosition cameraPos = new CameraPosition.Builder()
                .target(ne)
                .zoom(18)
                .bearing(270)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPos));

        hallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tileOverlay[0].setVisible(true);
                tileOverlay[1].setVisible(false);
                tileOverlay[2].setVisible(false);
            }
        });

        oneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tileOverlay[0].setVisible(false);
                tileOverlay[1].setVisible(true);
                tileOverlay[2].setVisible(false);
            }
        });

        twoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                tileOverlay[0].setVisible(false);
                tileOverlay[1].setVisible(false);
                tileOverlay[2].setVisible(true);
            }
        });

        //return v;
    }

    @Override
    public void onResume(){
        mView.onResume();
        super.onResume();
        //goo = overlay.setGround(mMap);
    }

    @Override
    public void onStop(){
        if(mMap.isMyLocationEnabled() && mMap != null)
        {
            mMap.setMyLocationEnabled(false);
        }
        super.onStop();
        //goo.remove();
    }

    // Releases Activity to free up memory after quitting the tab
    @Override
    public void onDestroy(){
        mMap.setMyLocationEnabled(false);
        mView.onDestroy();
        super.onDestroy();
        //goo.remove();
    }

    @Override
    public void onPause(){
        //goo.remove();
        mMap.setMyLocationEnabled(false);
        super.onPause();
    }


}