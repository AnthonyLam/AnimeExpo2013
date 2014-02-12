package com.lamapress.animeexpo2013;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lamapress.animeexpo2013.MapFragments.ExpoMapFragment;

import java.util.ArrayList;
import java.util.List;

public class Shuttles extends Activity{

    public Shuttles(){
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shuttle_times);

        final List<String> holder = new ArrayList<String>();
        holder.add("Kawada Hotel -  On Hill Street - Corner of 2nd");
        holder.add("Millenium Biltmore Hotel - Cubside on Grand Avenue");
        holder.add("Sheraton - Curbisde on Hope Street");
        holder.add("The LA Hotel Downtown - Walk to Westin Bonaventure");
        holder.add("Westin Bonaventure - Curbside of Figueroa");

        ArrayAdapter<String> hotels = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,holder);
        ListView hotelList = (ListView)findViewById(R.id.shuttle_locations);
        hotelList.setAdapter(hotels);

        hotelList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), ExpoMapFragment.class);
                intent.putExtra("location",holder.get(position));
                startActivity(intent);
            }
        });
    }

}
