package com.lamapress.animeexpo2013;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ExpoMapFragment extends Fragment {

    public MapView mView;
    GoogleMap mMap;
    //GroundOverlay goo;
    MapOverlay overlay;
    TileOverlay tileOverlay;
    public LatLng ne;
    public LatLng sw;

    public ExpoMapFragment(){

    }


    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){


        View v = inflater.inflate(R.layout.fragment_map_view,null);

        mView = (MapView)v.findViewById(R.id.map);
        mView.onCreate(savedInstanceState);
        mMap = mView.getMap();
        mMap.setMyLocationEnabled(true);

        // Boundary definitions
        ne = new LatLng(34.041689,-118.269481);
        sw = new LatLng( 34.039205,-118.271738);
        //LatLngBounds camera = new LatLngBounds(new LatLng(  34.037505,-118.273154), new LatLng( 34.043808,-118.266756));
        //overlay.setBitMapRange(ne,sw,0);

        // Sets the Exhibit Hall overlay for LACC
        try{
            MapsInitializer.initialize(this.getActivity());
        }
        catch(GooglePlayServicesNotAvailableException e)
        {
            Toast.makeText(this.getActivity(), "Not Available", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        // Generate TileOverlays
        overlay = new MapOverlay(getActivity().getAssets());
        tileOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(overlay));


        //Moves the camera to center on the convention center
        CameraPosition cameraPos = new CameraPosition.Builder()
                .target(ne)
                .zoom(17)
                .bearing(270)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPos));

        return v;
    }

    @Override
    public void onResume(){
        mView.onResume();
        tileOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(overlay));
        super.onResume();
        //goo = overlay.setGround(mMap);
    }

    @Override
    public void onStop(){
        tileOverlay.remove();
        tileOverlay = null;
        mMap.setMyLocationEnabled(false);
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
        tileOverlay.clearTileCache();
        mMap.setMyLocationEnabled(false);
        super.onPause();
    }



}