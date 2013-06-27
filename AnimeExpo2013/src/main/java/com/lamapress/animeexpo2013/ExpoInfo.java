package com.lamapress.animeexpo2013;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ExpoInfo extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expo_info,container,false);

        Button hoursButton = (Button)view.findViewById(R.id.hours_button);
        Button mapButton = (Button)view.findViewById(R.id.map_button);
        Button exhibitorButton = (Button)view.findViewById(R.id.exhibitor_button);

        hoursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ExpoMapFragment.class);
                getActivity().startActivity(intent);
            }
        });

        exhibitorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return view;
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.expo_info, menu);
        return true;
    }
    
}
